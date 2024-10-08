package com.github.traderjoe95.mls.protocol.message

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.error.CreateAddError
import com.github.traderjoe95.mls.protocol.error.CreateGroupContextExtensionsError
import com.github.traderjoe95.mls.protocol.error.CreateMessageError
import com.github.traderjoe95.mls.protocol.error.CreatePreSharedKeyError
import com.github.traderjoe95.mls.protocol.error.CreateQuarantineEndError
import com.github.traderjoe95.mls.protocol.error.CreateReInitError
import com.github.traderjoe95.mls.protocol.error.CreateRemoveError
import com.github.traderjoe95.mls.protocol.error.CreateRequestWelcomeBackGhostError
import com.github.traderjoe95.mls.protocol.error.CreateShareRecoveryMessageError
import com.github.traderjoe95.mls.protocol.error.CreateShareResendMessageError
import com.github.traderjoe95.mls.protocol.error.CreateSignatureError
import com.github.traderjoe95.mls.protocol.error.CreateUpdateError
import com.github.traderjoe95.mls.protocol.error.PrivateMessageSenderError
import com.github.traderjoe95.mls.protocol.error.PublicMessageSenderError
import com.github.traderjoe95.mls.protocol.group.GroupContext
import com.github.traderjoe95.mls.protocol.group.GroupState
import com.github.traderjoe95.mls.protocol.group.Validations
import com.github.traderjoe95.mls.protocol.message.padding.PaddingStrategy
import com.github.traderjoe95.mls.protocol.message.padding.deterministic.NoPadding
import com.github.traderjoe95.mls.protocol.psk.ExternalPskId
import com.github.traderjoe95.mls.protocol.psk.PskLookup
import com.github.traderjoe95.mls.protocol.psk.ResumptionPskId
import com.github.traderjoe95.mls.protocol.psk.ResumptionPskUsage
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.tree.RatchetTreeOps
import com.github.traderjoe95.mls.protocol.tree.SecretTree
import com.github.traderjoe95.mls.protocol.types.GroupContextExtension
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.crypto.HpkeCiphertext
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import com.github.traderjoe95.mls.protocol.types.crypto.Mac
import com.github.traderjoe95.mls.protocol.types.crypto.Secret
import com.github.traderjoe95.mls.protocol.types.crypto.SignaturePrivateKey
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import com.github.traderjoe95.mls.protocol.types.framing.content.ApplicationData
import com.github.traderjoe95.mls.protocol.types.framing.content.AuthenticatedContent
import com.github.traderjoe95.mls.protocol.types.framing.content.Commit
import com.github.traderjoe95.mls.protocol.types.framing.content.Content
import com.github.traderjoe95.mls.protocol.types.framing.content.FramedContent
import com.github.traderjoe95.mls.protocol.types.framing.content.GroupContextExtensions
import com.github.traderjoe95.mls.protocol.types.framing.content.PreSharedKey
import com.github.traderjoe95.mls.protocol.types.framing.content.ReInit
import com.github.traderjoe95.mls.protocol.types.framing.content.Remove
import com.github.traderjoe95.mls.protocol.types.framing.content.Update
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion
import com.github.traderjoe95.mls.protocol.types.framing.enums.WireFormat
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode
import com.github.traderjoe95.mls.protocol.types.tree.UpdateLeafNode

class GroupMessageFactory internal constructor(
  private val currentTree: RatchetTreeOps,
  private val membershipKey: Secret,
  private val senderDataSecret: Secret,
  private val secretTree: SecretTree,
  private val groupContext: GroupContext,
  private val leafIndex: LeafIndex,
  private val signaturePrivateKey: SignaturePrivateKey,
  private val validations: Validations = Validations(groupContext, currentTree),
) {
  constructor(group: GroupState.Active) : this(
    group.tree,
    group.keySchedule.membershipKey,
    group.keySchedule.senderDataSecret,
    group.secretTree,
    group.groupContext,
    group.leafIndex,
    group.signaturePrivateKey,
    group.validations,
  )

  private val cipherSuite: CipherSuite
    get() = groupContext.cipherSuite

  suspend fun quarantineEndMessage(
    leafIndex: LeafIndex,
    leafNode: UpdateLeafNode,
    signaturePrivateKey: SignaturePrivateKey,
  ): Either<CreateQuarantineEndError, MlsQuarantineEndMessage> = either {
    MlsMessage(QuarantineEnd.create(groupContext.groupId, groupContext.cipherSuite, leafIndex, leafNode, signaturePrivateKey).bind())
  }

  suspend fun shareRecoveryMessage(
    leafIndex: LeafIndex,
    epoch: ULong,
    encryptionKey: HpkePublicKey,
    ciphertext: HpkeCiphertext
  ): Either<CreateShareRecoveryMessageError, MlsShareRecoveryMessage> = either {
    MlsMessage(ShareRecoveryMessage.create(groupContext.groupId, leafIndex, epoch, encryptionKey, ciphertext))
  }

  suspend fun shareResend(
    shareHolderRank: UInt,
    leafIndex: LeafIndex,
    signaturePrivateKey: SignaturePrivateKey,
  ): Either<CreateShareResendMessageError, MlsShareResendMessage> = either {
    MlsMessage(ShareResend.create(groupContext.groupId, groupContext.cipherSuite, leafIndex, shareHolderRank, signaturePrivateKey).bind())
  }

  suspend fun requestWelcomeBackGhost(
    leafIndex: LeafIndex,
    signaturePrivateKey: SignaturePrivateKey,
  ): Either<CreateRequestWelcomeBackGhostError, MlsRequestWelcomeBackGhostMessage> = either {
    MlsMessage(RequestWelcomeBackGhost.create(groupContext.groupId, groupContext.cipherSuite, leafIndex, signaturePrivateKey).bind())
  }

  suspend fun applicationMessage(
    applicationData: ApplicationData,
    options: UsePrivateMessage = UsePrivateMessage(),
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<PrivateMessageSenderError, MlsApplicationMessage> =
    either {
      protectPrivate(applicationData, options, authenticatedData)
    }

  context(Raise<CreateAddError>)
  suspend fun add(
    keyPackage: KeyPackage,
    options: MessageOptions = UsePublicMessage,
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<CreateAddError, MlsProposalMessage> =
    either {
      protect(
        validations.validated(Add(keyPackage)).bind(),
        options,
        authenticatedData,
      )
    }

  suspend fun update(
    leafNode: UpdateLeafNode,
    options: MessageOptions = UsePublicMessage,
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<CreateUpdateError, MlsProposalMessage> =
    either {
      protect(
        validations.validated(Update(leafNode), leafIndex).bind(),
        options,
        authenticatedData,
      )
    }

  suspend fun remove(
    leafIndex: LeafIndex,
    options: MessageOptions = UsePublicMessage,
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<CreateRemoveError, MlsProposalMessage> =
    either {
      protect(validations.validated(Remove(leafIndex)).bind(), options, authenticatedData)
    }

  suspend fun preSharedKey(
    externalPskId: ByteArray,
    psks: PskLookup? = null,
    options: MessageOptions = UsePublicMessage,
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<CreatePreSharedKeyError, MlsProposalMessage> =
    either {
      protect(
        PreSharedKey(ExternalPskId(externalPskId, cipherSuite.generateNonce(cipherSuite.hashLen))).also {
          validations.validated(it, psks = psks).bind()
        },
        options,
        authenticatedData,
      )
    }

  suspend fun preSharedKey(
    pskGroupId: GroupId,
    pskEpoch: ULong,
    psks: PskLookup? = null,
    options: MessageOptions = UsePublicMessage,
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<CreatePreSharedKeyError, MlsProposalMessage> =
    either {
      protect(
        PreSharedKey(
          ResumptionPskId(
            ResumptionPskUsage.Application,
            pskGroupId,
            pskEpoch,
            cipherSuite.generateNonce(cipherSuite.hashLen),
          ),
        ).also { validations.validated(it, psks = psks).bind() },
        options,
        authenticatedData,
      )
    }

  suspend fun groupContextExtensions(
    extensions: List<GroupContextExtension<*>>,
    options: MessageOptions = UsePublicMessage,
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<CreateGroupContextExtensionsError, MlsProposalMessage> =
    either {
      protect(
        validations.validated(GroupContextExtensions(extensions)).bind(),
        options,
        authenticatedData,
      )
    }

  suspend fun groupContextExtensions(
    vararg extensions: GroupContextExtension<*>,
    options: MessageOptions = UsePublicMessage,
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<CreateGroupContextExtensionsError, MlsProposalMessage> = groupContextExtensions(extensions.asList(), options, authenticatedData)

  suspend fun reInit(
    cipherSuite: CipherSuite,
    version: ProtocolVersion = ProtocolVersion.MLS_1_0,
    groupId: GroupId? = null,
    extensions: List<GroupContextExtension<*>> = listOf(),
    options: MessageOptions = UsePublicMessage,
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<CreateReInitError, MlsProposalMessage> =
    either {
      protect(
        validations.validated(ReInit(groupId ?: GroupId.new(), version, cipherSuite, extensions)).bind(),
        options,
        authenticatedData,
      )
    }

  suspend fun reInit(
    cipherSuite: CipherSuite,
    vararg extensions: GroupContextExtension<*>,
    version: ProtocolVersion = ProtocolVersion.MLS_1_0,
    groupId: GroupId = GroupId.new(),
    options: MessageOptions = UsePublicMessage,
    authenticatedData: ByteArray = byteArrayOf(),
  ): Either<CreateReInitError, MlsProposalMessage> = reInit(cipherSuite, version, groupId, extensions.asList(), options, authenticatedData)

  context(Raise<CreateMessageError>)
  internal suspend fun <C : Content<C>> protect(
    content: C,
    options: MessageOptions,
    authenticatedData: ByteArray = byteArrayOf(),
  ): MlsMessage<GroupMessage<C>> =
    when (options) {
      is UsePublicMessage -> protectPublic(content, options, authenticatedData)
      is UsePrivateMessage -> protectPrivate(content, options, authenticatedData)
    }

  context(Raise<CreateMessageError>)
  internal suspend fun protectCommit(
    partialCommit: AuthenticatedContent<Commit>,
    confirmationTag: Mac,
    options: MessageOptions,
  ): MlsCommitMessage =
    when (options) {
      is UsePublicMessage -> protectPublic(partialCommit.copy(confirmationTag = confirmationTag))
      is UsePrivateMessage -> protectPrivate(partialCommit.copy(confirmationTag = confirmationTag), options)
    }

  context(Raise<PublicMessageSenderError>)
  internal fun <C : Content<C>> protectPublic(
    content: C,
    options: UsePublicMessage,
    authenticatedData: ByteArray,
  ): MlsMessage<PublicMessage<C>> = protectPublic(createAuthenticatedContent(content, options, authenticatedData))

  context(Raise<PrivateMessageSenderError>)
  internal suspend fun <C : Content<C>> protectPrivate(
    content: C,
    options: UsePrivateMessage,
    authenticatedData: ByteArray,
  ): MlsMessage<PrivateMessage<C>> = protectPrivate(createAuthenticatedContent(content, options, authenticatedData), options)

  context(Raise<PublicMessageSenderError>)
  internal fun <C : Content<C>> protectPublic(content: AuthenticatedContent<C>): MlsMessage<PublicMessage<C>> =
    MlsMessage.public(
      PublicMessage.create(content, groupContext, membershipKey),
    )

  context(Raise<PrivateMessageSenderError>)
  internal suspend fun <C : Content<C>> protectPrivate(
    content: AuthenticatedContent<C>,
    options: UsePrivateMessage,
  ): MlsMessage<PrivateMessage<C>> =
    MlsMessage.private(
      PrivateMessage.create(
        cipherSuite,
        content,
        secretTree,
        senderDataSecret,
        options.paddingStrategy,
      ),
    )

  context(Raise<CreateSignatureError>)
  internal fun <C : Content<C>> createAuthenticatedContent(
    content: C,
    options: MessageOptions,
    authenticatedData: ByteArray,
  ): AuthenticatedContent<C> {
    val framedContent = FramedContent.createMember(content, groupContext, leafIndex, authenticatedData)
    val signature = framedContent.sign(options.wireFormat, groupContext, signaturePrivateKey).bind()

    return AuthenticatedContent(
      options.wireFormat,
      framedContent,
      signature,
      null,
    )
  }
}

sealed interface MessageOptions {
  val wireFormat: WireFormat
}

data object UsePublicMessage : MessageOptions {
  override val wireFormat: WireFormat = WireFormat.MlsPublicMessage
}

data class UsePrivateMessage(val paddingStrategy: PaddingStrategy = NoPadding) : MessageOptions {
  override val wireFormat: WireFormat = WireFormat.MlsPrivateMessage
}
