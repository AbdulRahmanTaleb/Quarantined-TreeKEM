package com.github.traderjoe95.mls.protocol.client

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.raise.either
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.error.CreateSignatureError
import com.github.traderjoe95.mls.protocol.error.DecoderError
import com.github.traderjoe95.mls.protocol.error.ExternalJoinError
import com.github.traderjoe95.mls.protocol.error.ExternalPskError
import com.github.traderjoe95.mls.protocol.error.GroupCreationError
import com.github.traderjoe95.mls.protocol.error.GroupSuspended
import com.github.traderjoe95.mls.protocol.error.ProcessMessageError
import com.github.traderjoe95.mls.protocol.error.PskError
import com.github.traderjoe95.mls.protocol.error.PublicMessageError
import com.github.traderjoe95.mls.protocol.error.UnknownGroup
import com.github.traderjoe95.mls.protocol.error.WelcomeJoinError
import com.github.traderjoe95.mls.protocol.group.PrepareCommitResult
import com.github.traderjoe95.mls.protocol.group.resumption.isProtocolResumption
import com.github.traderjoe95.mls.protocol.message.ApplicationMessage
import com.github.traderjoe95.mls.protocol.message.GroupInfo
import com.github.traderjoe95.mls.protocol.message.GroupMessage
import com.github.traderjoe95.mls.protocol.message.HandshakeMessage
import com.github.traderjoe95.mls.protocol.message.KeyPackage
import com.github.traderjoe95.mls.protocol.message.MlsMessage
import com.github.traderjoe95.mls.protocol.message.MlsMessage.Companion.ensureFormat
import com.github.traderjoe95.mls.protocol.message.PublicMessage
import com.github.traderjoe95.mls.protocol.message.QuarantineEnd
import com.github.traderjoe95.mls.protocol.message.RequestWelcomeBackGhost
import com.github.traderjoe95.mls.protocol.message.ShareRecoveryMessage
import com.github.traderjoe95.mls.protocol.message.ShareResend
import com.github.traderjoe95.mls.protocol.message.Welcome
import com.github.traderjoe95.mls.protocol.message.WelcomeBackGhost
import com.github.traderjoe95.mls.protocol.psk.ExternalPskHolder
import com.github.traderjoe95.mls.protocol.psk.ExternalPskId
import com.github.traderjoe95.mls.protocol.psk.PreSharedKeyId
import com.github.traderjoe95.mls.protocol.psk.ResumptionPskId
import com.github.traderjoe95.mls.protocol.service.AuthenticationService
import com.github.traderjoe95.mls.protocol.tree.PublicRatchetTree
import com.github.traderjoe95.mls.protocol.types.Credential
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.KeyPackageExtensions
import com.github.traderjoe95.mls.protocol.types.LeafNodeExtensions
import com.github.traderjoe95.mls.protocol.types.crypto.Secret
import com.github.traderjoe95.mls.protocol.types.crypto.SignatureKeyPair
import com.github.traderjoe95.mls.protocol.types.framing.enums.ContentType
import com.github.traderjoe95.mls.protocol.types.tree.leaf.Capabilities
import com.github.traderjoe95.mls.protocol.types.tree.leaf.Lifetime
import com.github.traderjoe95.mls.protocol.util.hex
import org.bouncycastle.asn1.ocsp.Request

class MlsClient<Identity : Any>(
  val authenticationService: AuthenticationService<Identity>,
) : ExternalPskHolder<MlsClient<Identity>> {
  private val groups: MutableMap<String, GroupClient<Identity, *>> = mutableMapOf()
  private val externalPsks: MutableMap<String, Secret> = mutableMapOf()

  fun createGroup(
    cipherSuite: CipherSuite,
    signatureKeyPair: SignatureKeyPair,
    credential: Credential,
    groupId: GroupId? = null,
    capabilities: Capabilities = Capabilities.default(),
    keyPackageExtensions: KeyPackageExtensions = listOf(),
    leafNodeExtensions: LeafNodeExtensions = listOf(),
  ): Either<GroupCreationError, ActiveGroupClient<Identity>> =
    either {
      GroupClient.newGroup(
        this@MlsClient,
        KeyPackage
          .generate(
            cipherSuite,
            signatureKeyPair.move(),
            credential,
            capabilities = capabilities,
            lifetime = Lifetime.always(),
            keyPackageExtensions = keyPackageExtensions,
            leafNodeExtensions = leafNodeExtensions,
          )
          .bind(),
        groupId = groupId,
      ).bind()
    }

  suspend fun joinFromWelcome(
    welcome: Welcome,
    ownKeyPackage: KeyPackage.Private,
    optionalTree: PublicRatchetTree? = null,
  ): Either<WelcomeJoinError, ActiveGroupClient<Identity>> =
    either {
      val resumingFrom =
        welcome.decryptGroupSecrets(ownKeyPackage)
          .bind()
          .preSharedKeyIds
          .filterIsInstance<ResumptionPskId>()
          .find { it.isProtocolResumption }
          ?.let {
            groups[it.pskGroupId.hex]
              ?.getStateForEpoch(it.pskGroupId, it.pskEpoch)
              ?: raise(WelcomeJoinError.MissingResumptionGroup(it))
          }

      GroupClient.joinFromWelcome(
        this@MlsClient,
        welcome,
        ownKeyPackage,
        resumingFrom,
        optionalTree,
      ).bind().also { register(it) }
    }

  suspend fun joinFromGroupInfo(
    groupInfo: GroupInfo,
    signatureKeyPair: SignatureKeyPair,
    credential: Credential,
    lifetime: Lifetime = Lifetime.always(),
    capabilities: Capabilities = Capabilities.default(),
    keyPackageExtensions: KeyPackageExtensions = listOf(),
    leafNodeExtensions: LeafNodeExtensions = listOf(),
    commitAuthenticatedData: ByteArray = byteArrayOf(),
    optionalTree: PublicRatchetTree? = null,
  ): Either<ExternalJoinError, Pair<ActiveGroupClient<Identity>, ByteArray>> =
    either {
      joinFromGroupInfo(
        groupInfo,
        newKeyPackage(
          groupInfo.cipherSuite,
          signatureKeyPair,
          credential,
          lifetime,
          capabilities,
          keyPackageExtensions,
          leafNodeExtensions,
        ).bind(),
        commitAuthenticatedData,
        optionalTree,
      ).bind()
    }

  suspend fun joinFromGroupInfo(
    groupInfo: GroupInfo,
    ownKeyPackage: KeyPackage.Private,
    commitAuthenticatedData: ByteArray = byteArrayOf(),
    optionalTree: PublicRatchetTree? = null,
  ): Either<ExternalJoinError, Pair<ActiveGroupClient<Identity>, ByteArray>> =
    GroupClient.joinFromGroupInfo(
      this@MlsClient,
      groupInfo,
      ownKeyPackage,
      commitAuthenticatedData,
      optionalTree,
    ).onRight { (client, _) -> register(client) }

  @JvmOverloads
  fun newKeyPackage(
    cipherSuite: CipherSuite,
    signatureKeyPair: SignatureKeyPair,
    credential: Credential,
    lifetime: Lifetime = Lifetime.always(),
    capabilities: Capabilities = Capabilities.default(),
    keyPackageExtensions: KeyPackageExtensions = listOf(),
    leafNodeExtensions: LeafNodeExtensions = listOf(),
  ): Either<CreateSignatureError, KeyPackage.Private> =
    KeyPackage
      .generate(
        cipherSuite,
        signatureKeyPair.move(),
        credential,
        capabilities,
        lifetime,
        keyPackageExtensions,
        leafNodeExtensions,
      )

  fun decodeMessage(messageBytes: ByteArray): Either<DecoderError, MlsMessage<*>> = GroupClient.decodeMessage(messageBytes)

  suspend fun processMessage(messageBytes: ByteArray, isGhost: Boolean = false, cached: Boolean = false): Either<ProcessMessageError, ProcessMessageResult<Identity>> =
    decodeMessage(messageBytes)
      .flatMap { processMessage(it, isGhost, cached) }

  private suspend fun processMessage(message: MlsMessage<*>, isGhost: Boolean = false, cached: Boolean = false): Either<ProcessMessageError, ProcessMessageResult<Identity>> =
    either {
      if(isGhost && (!cached) && (message.message !is ShareRecoveryMessage) && (message.message !is WelcomeBackGhost)){
        ProcessMessageResult.MessageToCachForLater
      }
      else {
        when (message.message) {
          is KeyPackage -> {
            println("key package received")
            ProcessMessageResult.KeyPackageMessageReceived(message.message)
          }

          is Welcome -> {
            println("welcome received")
            ProcessMessageResult.WelcomeMessageReceived(message.message)
          }

          is GroupInfo -> {
            println("groupInfo received")
            ProcessMessageResult.GroupInfoMessageReceived(message.message)
          }

          is QuarantineEnd -> {
            println("QuarantineEnd received")
            processQuarantineEnd(message.message).bind()
          }

          is RequestWelcomeBackGhost -> {
            println("RequestWelcomeBackGhost received")
            processRequestWelcomeBackGhost(message.message).bind()
          }

          is GroupMessage<*> -> {
            processGroupMessage(message.message).bind()
          }

          is ShareRecoveryMessage -> {
            println("ShareRecoveryMessage Received")
            processShareRecoveryMessage(message.message).bind()
          }

          is WelcomeBackGhost -> {
            println("WelcomeBackGhost")
            processWelcomeBackGhostMessage(message.message).bind()
          }

          is ShareResend -> {
            println("ShareResend received")
            processShareResend(message.message).bind()
          }
        }
      }
    }

  @Suppress("UNCHECKED_CAST")
  private suspend fun processGroupMessage(groupMessage: GroupMessage<*>): Either<ProcessMessageError, ProcessMessageResult<Identity>> =
    either {
      val groupId = groupMessage.groupId
      val group = groups[groupId.hex] ?: raise(UnknownGroup(groupId))

      when (groupMessage.contentType) {
        is ContentType.Handshake -> {

          if (group is ActiveGroupClient<Identity>) {
//               println("handshake message received")
            ProcessMessageResult.HandshakeMessageReceived(
              groupId,
              group.processHandshake(groupMessage as HandshakeMessage).bind(),
            )
          } else {
            raise(GroupSuspended(groupMessage.groupId))
          }
        }

        is ContentType.Application -> {
          if (groupMessage is PublicMessage) {
//              println("public app message received")
            raise(PublicMessageError.ApplicationMessageMustNotBePublic)
          } else {
//              println("private app message received")
            ProcessMessageResult.ApplicationMessageReceived(
              groupId,
              group.open(groupMessage as ApplicationMessage).bind(),
            )
          }
        }

        else ->
          error("unreachable")
      }
    }

  private suspend fun processQuarantineEnd(quarantineEnd: QuarantineEnd): Either<ProcessMessageError, ProcessMessageResult<Identity>> =
    either{
      val groupId = quarantineEnd.groupId
      val group = groups[groupId.hex] ?: raise(UnknownGroup(groupId))

      if(quarantineEnd.leafIndex == group.state.leafIndex){
        println("Ignoring own QuarantineEnd Received")
        ProcessMessageResult.QuarantineEndReceived(
          quarantineEnd.groupId,
          null
        )
      }
      else{
        if (group is ActiveGroupClient<Identity>) {
          ProcessMessageResult.QuarantineEndReceived(
            quarantineEnd.groupId,
            group.processQuarantineEnd(quarantineEnd).bind()?.encoded,
          )
        } else {
          raise(GroupSuspended(quarantineEnd.groupId))
        }
      }
    }

  private suspend fun processRequestWelcomeBackGhost(requestWelcomeBackGhost: RequestWelcomeBackGhost): Either<ProcessMessageError, ProcessMessageResult<Identity>> =
    either{
      val groupId = requestWelcomeBackGhost.groupId
      val group = groups[groupId.hex] ?: raise(UnknownGroup(groupId))

      if(requestWelcomeBackGhost.leafIndex == group.state.leafIndex){
        println("Ignoring own QuarantineEnd Received")
        ProcessMessageResult.RequestWelcomeBackGhostMessageReceived(
          requestWelcomeBackGhost
        )
      }
      else{
        if (group is ActiveGroupClient<Identity>) {
          group.processRequestWelcomeBackGhost(requestWelcomeBackGhost).bind()

          ProcessMessageResult.RequestWelcomeBackGhostMessageReceived(
            requestWelcomeBackGhost
          )
        } else {
          raise(GroupSuspended(requestWelcomeBackGhost.groupId))
        }
      }
    }

  private suspend fun processShareResend(shareResend: ShareResend): Either<ProcessMessageError, ProcessMessageResult<Identity>> =
    either{
      val groupId = shareResend.groupId
      val group = groups[groupId.hex] ?: raise(UnknownGroup(groupId))

      if(shareResend.leafIndex == group.state.leafIndex){
        println("Ignoring own ShareResend Received")
        ProcessMessageResult.ShareResendReceived(
          shareResend.groupId,
          null
        )
      }
      else{
        if (group is ActiveGroupClient<Identity>) {
          ProcessMessageResult.ShareResendReceived(
            shareResend.groupId,
            group.processShareResend(shareResend).bind()?.encoded,
          )
        } else {
          raise(GroupSuspended(shareResend.groupId))
        }
      }
    }

  private suspend fun processShareRecoveryMessage(shareRecoveryMessage: ShareRecoveryMessage): Either<ProcessMessageError, ProcessMessageResult<Identity>> =
    either{
      val groupId = shareRecoveryMessage.groupId
      val group = groups[groupId.hex] ?: raise(UnknownGroup(groupId))

      if(shareRecoveryMessage.leafIndex != group.state.leafIndex){
        println("Ignoring share recovery message")
        ProcessMessageResult.ShareRecoveryMessageReceived(shareRecoveryMessage)
      }

      else{
        if (group is ActiveGroupClient<Identity>) {
          group.processShareRecoveryMessage(shareRecoveryMessage).bind()
          println("Processed share recovery message")
          ProcessMessageResult.ShareRecoveryMessageReceived(shareRecoveryMessage)
        } else {
          raise(GroupSuspended(shareRecoveryMessage.groupId))
        }
      }
    }

  private suspend fun processWelcomeBackGhostMessage(welcomeBackGhost: WelcomeBackGhost): Either<ProcessMessageError, ProcessMessageResult<Identity>> =
    either{
      val groupId = welcomeBackGhost.groupId
      val group = groups[groupId.hex] ?: raise(UnknownGroup(groupId))

      if(!group.isGhost){
        ProcessMessageResult.WelcomeBackGhostMessageIgnored
      }
      else{
        if (group is ActiveGroupClient<Identity>) {
          group.processWelcomeBackGhostMessage(welcomeBackGhost).bind()
          ProcessMessageResult.WelcomeBackGhostMessageProcessed
        } else {
          raise(GroupSuspended(welcomeBackGhost.groupId))
        }
      }
    }

  operator fun get(groupId: GroupId): GroupClient<Identity, *>? = groups[groupId.hex]

  override fun registerExternalPsk(
    pskId: ByteArray,
    psk: Secret,
  ): MlsClient<Identity> =
    apply {
      externalPsks[pskId.hex] = psk
    }

  override fun deleteExternalPsk(pskId: ByteArray): MlsClient<Identity> = apply { externalPsks.remove(pskId.hex) }

  override fun clearExternalPsks(): MlsClient<Identity> = apply { externalPsks.clear() }

  override suspend fun getPreSharedKey(id: PreSharedKeyId): Either<PskError, Secret> =
    either {
      when (id) {
        is ExternalPskId -> externalPsks[id.pskId.hex] ?: raise(ExternalPskError.UnknownExternalPsk(id.pskId))
        is ResumptionPskId ->
          groups[id.pskGroupId.hex]?.getPreSharedKey(id)?.bind()
            ?: raise(UnknownGroup(id.pskGroupId))
      }
    }

  internal fun register(groupClient: GroupClient<Identity, *>) {
    groups[groupClient.groupId.hex] = groupClient
  }
}
