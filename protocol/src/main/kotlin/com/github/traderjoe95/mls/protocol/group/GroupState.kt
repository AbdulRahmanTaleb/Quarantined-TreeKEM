package com.github.traderjoe95.mls.protocol.group

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.github.traderjoe95.mls.codec.util.uSize
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.crypto.ICipherSuite
import com.github.traderjoe95.mls.protocol.crypto.KeySchedule
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.error.CreateQuarantineEndError
import com.github.traderjoe95.mls.protocol.error.CreateUpdateError
import com.github.traderjoe95.mls.protocol.error.GhostRecoveryProcessError
import com.github.traderjoe95.mls.protocol.error.GroupActive
import com.github.traderjoe95.mls.protocol.error.GroupInfoError
import com.github.traderjoe95.mls.protocol.error.GroupSuspended
import com.github.traderjoe95.mls.protocol.error.InvalidCommit
import com.github.traderjoe95.mls.protocol.error.JoinError
import com.github.traderjoe95.mls.protocol.error.MessageRecipientError
import com.github.traderjoe95.mls.protocol.error.ProcessMessageError
import com.github.traderjoe95.mls.protocol.error.ProposalValidationError
import com.github.traderjoe95.mls.protocol.error.PskError
import com.github.traderjoe95.mls.protocol.error.ShareRecoveryMessageError
import com.github.traderjoe95.mls.protocol.error.WelcomeBackGhostMessageError
import com.github.traderjoe95.mls.protocol.error.WelcomeJoinError
import com.github.traderjoe95.mls.protocol.group.resumption.isProtocolResumption
import com.github.traderjoe95.mls.protocol.group.resumption.validateResumption
import com.github.traderjoe95.mls.protocol.message.AuthHandshakeContent
import com.github.traderjoe95.mls.protocol.message.GroupInfo
import com.github.traderjoe95.mls.protocol.message.GroupMessageFactory
import com.github.traderjoe95.mls.protocol.message.HandshakeMessage
import com.github.traderjoe95.mls.protocol.message.KeyPackage
import com.github.traderjoe95.mls.protocol.message.MlsHandshakeMessage
import com.github.traderjoe95.mls.protocol.message.MlsShareRecoveryMessage
import com.github.traderjoe95.mls.protocol.message.QuarantineEnd
import com.github.traderjoe95.mls.protocol.message.ShareRecoveryMessage
import com.github.traderjoe95.mls.protocol.message.WelcomeBackGhost
import com.github.traderjoe95.mls.protocol.psk.PreSharedKeyId
import com.github.traderjoe95.mls.protocol.psk.PskLookup
import com.github.traderjoe95.mls.protocol.psk.ResolvedPsk
import com.github.traderjoe95.mls.protocol.psk.ResumptionPskId
import com.github.traderjoe95.mls.protocol.service.AuthenticationService
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.tree.PrivateRatchetTree
import com.github.traderjoe95.mls.protocol.tree.RatchetTree
import com.github.traderjoe95.mls.protocol.tree.RatchetTree.Companion.join
import com.github.traderjoe95.mls.protocol.tree.SecretTree
import com.github.traderjoe95.mls.protocol.tree.check
import com.github.traderjoe95.mls.protocol.tree.findEquivalentLeaf
import com.github.traderjoe95.mls.protocol.tree.insertPathSecrets
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.Credential
import com.github.traderjoe95.mls.protocol.types.CredentialType
import com.github.traderjoe95.mls.protocol.types.Extension
import com.github.traderjoe95.mls.protocol.types.ExternalPub
import com.github.traderjoe95.mls.protocol.types.GroupContextExtensions
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.LeafNodeExtensions
import com.github.traderjoe95.mls.protocol.types.crypto.HpkeKeyPair
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePrivateKey
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import com.github.traderjoe95.mls.protocol.types.crypto.Mac
import com.github.traderjoe95.mls.protocol.types.crypto.Secret
import com.github.traderjoe95.mls.protocol.types.crypto.Secret.Companion.asSecret
import com.github.traderjoe95.mls.protocol.types.crypto.SignatureKeyPair
import com.github.traderjoe95.mls.protocol.types.crypto.SignaturePrivateKey
import com.github.traderjoe95.mls.protocol.types.framing.content.AuthenticatedContent
import com.github.traderjoe95.mls.protocol.types.framing.content.Commit
import com.github.traderjoe95.mls.protocol.types.framing.content.Proposal
import com.github.traderjoe95.mls.protocol.types.framing.content.ReInit
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion
import com.github.traderjoe95.mls.protocol.types.framing.enums.SenderType.Member
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode
import com.github.traderjoe95.mls.protocol.types.tree.UpdateLeafNode
import com.github.traderjoe95.mls.protocol.types.tree.leaf.Capabilities
import com.github.traderjoe95.mls.protocol.util.hex
import java.time.Instant
import com.github.traderjoe95.mls.protocol.types.RatchetTree as RatchetTreeExt

sealed class GroupState(
  val groupContext: GroupContext,
  val tree: RatchetTree,
  val keySchedule: KeySchedule,
) : ICipherSuite by groupContext.cipherSuite {
  val protocolVersion: ProtocolVersion by lazy { groupContext.protocolVersion }
  val cipherSuite: CipherSuite by lazy { groupContext.cipherSuite }

  val groupId: GroupId by lazy { groupContext.groupId }
  val epoch: ULong by lazy { groupContext.epoch }

  val extensions: GroupContextExtensions by lazy { groupContext.extensions }

  val confirmationTag: Mac by lazy { mac(keySchedule.confirmationKey, groupContext.confirmedTranscriptHash) }

  val leafIndex: LeafIndex by lazy { tree.leafIndex }

  val members: List<LeafNode<*>> by lazy { tree.leaves.filterNotNull() }

  val INACTIVITY_DELAY: ULong = 2U
  val QUARANTEEN_DELAY: ULong = 100U


  fun isActive(): Boolean = this is Active

  context(Raise<GroupSuspended>)
  inline fun <T> ensureActive(body: Active.() -> T): T = ensureActive().body()

  context(Raise<GroupSuspended>)
  fun ensureActive(): Active = (this as? Active) ?: raise(GroupSuspended(groupId))

  context(Raise<GroupActive>)
  inline fun <T> ensureSuspended(body: Suspended.() -> T): T = ensureSuspended().body()

  context(Raise<GroupActive>)
  fun ensureSuspended(): Suspended = (this as? Suspended) ?: raise(GroupActive(groupId))

  fun coerceActive(): Active = this as Active

  fun coerceSuspended(): Suspended = this as Suspended


  class Active internal constructor(
    groupContext: GroupContext,
    tree: RatchetTree,
    keySchedule: KeySchedule,
    val signaturePrivateKey: SignaturePrivateKey,
    private val cachedProposals: Map<String, CachedProposal> = mapOf(),
    internal var cachedUpdate: CachedUpdate? = null,
    internal val cachedQuarantineEnd: MutableList<CachedQuarantineEnd> = mutableListOf(),
    val ghostMembers: MutableList<LeafIndex> = mutableListOf(),
    val ghostMembersKeys: MutableList<HpkePublicKey> = mutableListOf(),
    val ghostMembersShares: MutableList<ShamirSecretSharing.SecretShare> = mutableListOf(),
    val ghostMembersShareHolderRank: MutableList<UInt> = mutableListOf(),
    val recoveredShares: List<ShamirSecretSharing.SecretShare> = listOf(),
    val recoveredSharesHolderRank: UInt = 0u,
  ) : GroupState(groupContext, tree, keySchedule), SecretTree.Lookup, PskLookup {
    @get:JvmName("secretTree")
    val secretTree: SecretTree by lazy {
      SecretTree.create(
        cipherSuite,
        keySchedule.encryptionSecret,
        tree.leaves.uSize,
      )
    }

    @get:JvmName("messages")
    val messages: GroupMessageFactory by lazy { GroupMessageFactory(this) }

    @get:JvmName("validations")
    val validations: Validations by lazy { Validations(this) }

    fun derive(secret: ByteArray): HpkeKeyPair{
      return deriveKeyPair(secret.asSecret)
    }


    context(Raise<ProposalValidationError>)
    private suspend fun storeProposal(proposal: AuthenticatedContent<Proposal>): Active =
       Active(
        groupContext,
        tree,
        keySchedule,
        signaturePrivateKey,
        cachedProposals +
          CachedProposal(
            validations.validated(proposal).bind(),
            cipherSuite,
          ).let { it.ref.hex to it },
        cachedUpdate,
        cachedQuarantineEnd,
        ghostMembers,
        ghostMembersKeys,
        ghostMembersShares,
        ghostMembersShareHolderRank,
        recoveredShares,
         recoveredSharesHolderRank,
      )

    context(Raise<ProposalValidationError>)
    private suspend fun storeRecoveredShare(secretShare: ShamirSecretSharing.SecretShare, shareHolderRank: UInt): Active =
      Active(
        groupContext,
        tree,
        keySchedule,
        signaturePrivateKey,
        cachedProposals,
        cachedUpdate,
        cachedQuarantineEnd,
        ghostMembers,
        ghostMembersKeys,
        ghostMembersShares,
        ghostMembersShareHolderRank,
        recoveredShares + secretShare,
        if (shareHolderRank > recoveredSharesHolderRank) shareHolderRank else recoveredSharesHolderRank ,
      )

    context(Raise<GhostRecoveryProcessError>)
    suspend fun recoverKeyPair(): Either<GhostRecoveryProcessError, HpkeKeyPair> = either {
      if(recoveredShares.isEmpty()){
        raise(GhostRecoveryProcessError.NotEnoughSharesForKeyRecoveryError)
      }
      val t = recoveredShares[0].t
      val indices = mutableSetOf<Int>()
      recoveredShares.forEach {
        indices.add(it.rank)
      }
      if(indices.size != t){
        raise(GhostRecoveryProcessError.NotEnoughSharesForKeyRecoveryError)
      }
      val secret = ShamirSecretSharing.retrieveSecret(recoveredShares)
      derive(secret)
    }

    private suspend fun storeQuarantineEndProposal(quarantineEnd: QuarantineEnd): Active {
      if(quarantineEnd.leafIndex != leafIndex) {
        cachedQuarantineEnd.add(
          CachedQuarantineEnd(
            quarantineEnd.leafIndex,
            quarantineEnd.leafNode
          )
        )
      }

      return Active(
        groupContext,
        tree,
        keySchedule,
        signaturePrivateKey,
        cachedProposals,
        cachedUpdate,
        cachedQuarantineEnd,
        ghostMembers,
        ghostMembersKeys,
        ghostMembersShares,
        ghostMembersShareHolderRank,
        recoveredShares,
        recoveredSharesHolderRank,
      )
    }

    fun getStoredProposals(): List<CachedProposal> = cachedProposals.values.toList()

    context(Raise<InvalidCommit.UnknownProposal>)
    fun getStoredProposal(proposalRef: Proposal.Ref): CachedProposal =
      cachedProposals[proposalRef.hex]
        ?: raise(InvalidCommit.UnknownProposal(groupId, epoch, proposalRef))

    fun groupInfo(
      inlineTree: Boolean = true,
      public: Boolean = false,
    ): Either<GroupInfoError, GroupInfo> =
      GroupInfo.create(
        groupContext,
        mac(keySchedule.confirmationKey, groupContext.confirmedTranscriptHash),
        listOfNotNull(
          if (inlineTree) RatchetTreeExt(tree) else null,
          if (public) ExternalPub(deriveKeyPair(keySchedule.externalSecret).public) else null,
          *Extension.grease(),
        ),
        leafIndex,
        signaturePrivateKey,
      )

    override suspend fun getPreSharedKey(id: PreSharedKeyId): Either<PskError, Secret> =
      either {
        if (id is ResumptionPskId && id.pskGroupId eq groupId && id.pskEpoch == epoch) {
          keySchedule.resumptionPsk
        } else {
          raise(PskError.PskNotFound(id))
        }
      }

    suspend fun <Identity : Any> process(
      mlsMessage: MlsHandshakeMessage,
      authenticationService: AuthenticationService<Identity>,
      psks: PskLookup = PskLookup.EMPTY,
      cachedState: GroupState? = null,
    ): Either<ProcessMessageError, GroupState> = process(mlsMessage.message, authenticationService, psks, cachedState)

    suspend fun <Identity : Any> process(
      message: HandshakeMessage,
      authenticationService: AuthenticationService<Identity>,
      psks: PskLookup = PskLookup.EMPTY,
      cachedState: GroupState? = null,
      ghostKeyPair: HpkeKeyPair? = null,
      newGhostLeafNode: LeafNode<*>? = null,
      newGhostPrivateEncryptionKey: HpkePrivateKey? = null,
    ): Either<ProcessMessageError, GroupState> =
      either {
        process(message.unprotect(this@Active).bind(), authenticationService, psks, cachedState, ghostKeyPair, newGhostLeafNode, newGhostPrivateEncryptionKey)
      }

    context(Raise<ProcessMessageError>)
    @Suppress("UNCHECKED_CAST")
    internal suspend fun <Identity : Any> process(
      message: AuthHandshakeContent,
      authenticationService: AuthenticationService<Identity>,
      psks: PskLookup = PskLookup.EMPTY,
      cachedState: GroupState? = null,
      ghostKeyPair: HpkeKeyPair? = null,
      newGhostLeafNode: LeafNode<*>? = null,
      newGhostPrivateEncryptionKey: HpkePrivateKey? = null,
    ): GroupState {
      ensure(message.groupId eq groupId) { MessageRecipientError.WrongGroup(message.groupId, groupId) }
      ensure(message.epoch == epoch) {
        ProcessMessageError.HandshakeMessageForWrongEpoch(groupId, message.epoch, epoch)
      }


      return when (message.framedContent.content) {
        is Proposal ->
          storeProposal(message as AuthenticatedContent<Proposal>)

        is Commit ->
          if (message.sender.type == Member && message.sender.index == leafIndex) {
//             println("processing own commit !!")
            cachedState ?: raise(ProcessMessageError.MustUseCachedStateForOwnCommit)
          } else {
            processCommit(message as AuthenticatedContent<Commit>, authenticationService, psks, ghostKeyPair, newGhostLeafNode, newGhostPrivateEncryptionKey).bind()
          }
      }
    }

    context(Raise<ProcessMessageError>)
    suspend fun process(
      quarantineEnd: QuarantineEnd,
    ): Either<ProcessMessageError, Pair<GroupState, MlsShareRecoveryMessage?>> =
      either {
        ensure(quarantineEnd.groupId eq groupId) { MessageRecipientError.WrongGroup(quarantineEnd.groupId, groupId) }

        val newState = storeQuarantineEndProposal(quarantineEnd)
        validations.validated(quarantineEnd).bind()
        if (ghostMembers.contains(quarantineEnd.leafIndex)) {
          val idx = ghostMembers.indexOf(quarantineEnd.leafIndex)
          if (ghostMembersShareHolderRank[idx] == 1u) {
            val ct = encryptWithLabel(
              quarantineEnd.leafNode.encryptionKey,
              "ShareRecoveryMessage",
              ByteArray(0),
              ghostMembersShares[idx].encode()
            ).bind()
            Pair(
              newState,
              messages.shareRecoveryMessage(ghostMembersShareHolderRank[idx] ,quarantineEnd.leafIndex ,quarantineEnd.leafNode.encryptionKey, ct).bind()
            )
          } else {
            Pair(newState, null)
          }
        } else {
          Pair(newState, null)
        }
      }

    context(Raise<ProcessMessageError>)
    suspend fun process(
      shareRecoveryMessage: ShareRecoveryMessage
    ): Either<ProcessMessageError, GroupState> = either {

      ensure(shareRecoveryMessage.groupId eq groupId) { MessageRecipientError.WrongGroup(shareRecoveryMessage.groupId, groupId) }

      ensure(cachedUpdate != null) { ShareRecoveryMessageError.MissingCachedUpdateForGhost }

      val res = decryptWithLabel(
        cachedUpdate!!.encryptionPrivateKey,
        "ShareRecoveryMessage",
        ByteArray(0),
        shareRecoveryMessage.encryptedShare
      ).bind()

      val secretShare = ShamirSecretSharing.SecretShare.decode(res).second

      storeRecoveredShare(secretShare, shareRecoveryMessage.shareHolderRank)
    }

    context(Raise<ProcessMessageError>)
    suspend fun process(
      welcomeBackGhost: WelcomeBackGhost,
      psks: PskLookup = PskLookup.EMPTY
    ): Either<ProcessMessageError, GroupState> = either {

      ensure(welcomeBackGhost.groupId eq groupId) { MessageRecipientError.WrongGroup(welcomeBackGhost.groupId, groupId) }

      ensure(cachedUpdate != null) { WelcomeBackGhostMessageError.MissingCachedUpdateForGhost }


      val keyPair = reconstructPublicKey(cachedUpdate!!.encryptionPrivateKey).bind()

      val groupSecrets = welcomeBackGhost.decryptWelcomeBackGroupSecrets(keyPair).bind()

      val resolvedPsks = PskLookup.resolvePsks(psks, groupSecrets.preSharedKeyIds).bind()
      val pskSecret = ResolvedPsk.calculatePskSecret(cipherSuite, resolvedPsks)

      val groupInfo = welcomeBackGhost.decryptWelcomeBackGroupInfo(groupSecrets.joinerSecret, pskSecret).bind()

      val publicTree =
        groupInfo.extension<RatchetTreeExt>()?.tree ?: raise(WelcomeBackGhostMessageError.MissingRatchetTree)

      groupInfo.verifySignature(publicTree).bind()

      publicTree.check(groupInfo.groupContext).bind()

      val secretTree = PrivateRatchetTree(cipherSuite, leafIndex, mapOf(Pair(publicTree.root,groupSecrets.pathSecret)))

      val newTree = RatchetTree(cipherSuite, publicTree, secretTree).update(leafIndex, cachedUpdate!!.leafNode, cachedUpdate!!.encryptionPrivateKey)

      var groupContext = groupInfo.groupContext

      for(i in 0..<tree.leaves.size){
        if(tree.leaves[i] != null){
          tree.leaves[i]!!.epk = groupContext.epoch
        }
      }

      val keySchedule =
        KeySchedule.join(
          cipherSuite,
          groupSecrets.joinerSecret,
          pskSecret,
          groupContext,
        )

      cipherSuite.verifyMac(
        keySchedule.confirmationKey,
        groupContext.confirmedTranscriptHash,
        groupInfo.confirmationTag,
      )
      groupContext =
        groupContext.withInterimTranscriptHash(
          newInterimTranscriptHash(
            cipherSuite,
            groupContext.confirmedTranscriptHash,
            groupInfo.confirmationTag,
          ),
        )

      GroupState.Active(groupContext, newTree, keySchedule, signaturePrivateKey, recoveredShares = recoveredShares)
    }

    context(Raise<CreateUpdateError>)
    fun updateLeafNode(
      newEncryptionKeyPair: HpkeKeyPair,
      newSignatureKeyPair: SignatureKeyPair? = null,
      newCredential: Credential? = null,
      newCapabilities: Capabilities? = null,
      newExtensions: LeafNodeExtensions? = null,
    ): UpdateLeafNode {
      if (cachedUpdate != null) raise(CreateUpdateError.AlreadyUpdatedThisEpoch)

      val oldLeaf = tree.leafNode(leafIndex)
      val newLeaf =
        LeafNode.update(
          cipherSuite,
          newSignatureKeyPair ?: SignatureKeyPair(signaturePrivateKey, oldLeaf.signaturePublicKey),
          newEncryptionKeyPair.public,
          newCredential ?: oldLeaf.credential,
          newCapabilities ?: oldLeaf.capabilities,
          newExtensions ?: oldLeaf.extensions,
          leafIndex,
          groupId,
          epoch+1u,
        ).bind()

      cachedUpdate = CachedUpdate(newLeaf, newEncryptionKeyPair.private, newSignatureKeyPair?.private)

      return newLeaf
    }

    context(Raise<CreateQuarantineEndError>)
    fun updateGhostLeafNode(
      newEncryptionKeyPair: HpkeKeyPair,
    ): UpdateLeafNode {
      if (cachedUpdate != null) raise(CreateQuarantineEndError.AlreadyUpdatedThisEpoch)

      val oldLeaf = tree.leafNode(leafIndex)
      val newLeaf =
        LeafNode.update(
          cipherSuite,
          SignatureKeyPair(signaturePrivateKey, oldLeaf.signaturePublicKey),
          newEncryptionKeyPair.public,
          oldLeaf.credential,
          oldLeaf.capabilities,
          oldLeaf.extensions,
          leafIndex,
          groupId,
          epoch+1u,
        ).bind()

      cachedUpdate = CachedUpdate(newLeaf, newEncryptionKeyPair.private, null, true)

      return newLeaf
    }

    fun nextEpoch(
      groupContext: GroupContext,
      tree: RatchetTree,
      keySchedule: KeySchedule,
      newSignaturePrivateKey: SignaturePrivateKey = signaturePrivateKey,
    ): Active {
      return Active(
        groupContext,
        tree,
        keySchedule,
        newSignaturePrivateKey,
        mapOf(),
        null,
        mutableListOf(),
        ghostMembers,
        ghostMembersKeys,
        ghostMembersShares,
        ghostMembersShareHolderRank,
        recoveredShares,
        recoveredSharesHolderRank,
        )
    }

    fun suspend(
      groupContext: GroupContext,
      tree: RatchetTree,
      keySchedule: KeySchedule,
      reInit: ReInit,
    ): Suspended = Suspended(groupContext, tree, keySchedule, reInit)
  }

  class Suspended internal constructor(
    groupContext: GroupContext,
    tree: RatchetTree,
    keySchedule: KeySchedule,
    val reInit: ReInit,
  ) : GroupState(groupContext, tree, keySchedule), PskLookup {
    override suspend fun getPreSharedKey(id: PreSharedKeyId): Either<PskError, Secret> =
      either {
        if (id is ResumptionPskId && id.pskGroupId eq groupId && id.pskEpoch == epoch) {
          keySchedule.resumptionPsk
        } else {
          raise(PskError.PskNotFound(id))
        }
      }
  }

  internal data class CachedUpdate(
    val leafNode: UpdateLeafNode,
    val encryptionPrivateKey: HpkePrivateKey,
    val signaturePrivateKey: SignaturePrivateKey?,
    val ghost: Boolean = false,
  )

  internal data class CachedQuarantineEnd(
    val leafIndex: LeafIndex,
    val leafNode: UpdateLeafNode,
  )

  data class CachedProposal(
    val ref: Proposal.Ref,
    val sender: LeafIndex?,
    val proposal: Proposal,
    val received: Instant = Instant.now(),
  ) {
    internal constructor(
      proposal: AuthenticatedContent<Proposal>,
      cipherSuite: ICipherSuite,
    ) : this(
      cipherSuite.makeProposalRef(proposal),
      proposal.sender.takeIf { it.type == Member }?.index,
      proposal.framedContent.content,
    )
  }

  companion object
}
