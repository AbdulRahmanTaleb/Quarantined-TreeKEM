package com.github.traderjoe95.mls.protocol.group

import arrow.core.Either
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
import com.github.traderjoe95.mls.protocol.error.MessageRecipientError
import com.github.traderjoe95.mls.protocol.error.ProcessMessageError
import com.github.traderjoe95.mls.protocol.error.ProposalValidationError
import com.github.traderjoe95.mls.protocol.error.PskError
import com.github.traderjoe95.mls.protocol.error.ShareRecoveryMessageError
import com.github.traderjoe95.mls.protocol.error.ShareResendError
import com.github.traderjoe95.mls.protocol.error.WelcomeBackGhostMessageError
import com.github.traderjoe95.mls.protocol.group.GhostShareHolderList.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.message.AuthHandshakeContent
import com.github.traderjoe95.mls.protocol.message.GroupInfo
import com.github.traderjoe95.mls.protocol.message.GroupMessageFactory
import com.github.traderjoe95.mls.protocol.message.HandshakeMessage
import com.github.traderjoe95.mls.protocol.message.MlsHandshakeMessage
import com.github.traderjoe95.mls.protocol.message.MlsShareRecoveryMessage
import com.github.traderjoe95.mls.protocol.message.QuarantineEnd
import com.github.traderjoe95.mls.protocol.message.RequestWelcomeBackGhost
import com.github.traderjoe95.mls.protocol.message.ShareRecoveryMessage
import com.github.traderjoe95.mls.protocol.message.ShareResend
import com.github.traderjoe95.mls.protocol.message.WelcomeBackGhost
import com.github.traderjoe95.mls.protocol.psk.PreSharedKeyId
import com.github.traderjoe95.mls.protocol.psk.PskLookup
import com.github.traderjoe95.mls.protocol.psk.ResolvedPsk
import com.github.traderjoe95.mls.protocol.psk.ResumptionPskId
import com.github.traderjoe95.mls.protocol.service.AuthenticationService
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.tree.PrivateRatchetTree
import com.github.traderjoe95.mls.protocol.tree.RatchetTree
import com.github.traderjoe95.mls.protocol.tree.SecretTree
import com.github.traderjoe95.mls.protocol.tree.check
import com.github.traderjoe95.mls.protocol.types.Credential
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
import kotlin.math.ceil
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
    internal val cachedRequestWelcomeBackGhost: MutableList<CachedRequestWelcomeBackGhost> = mutableListOf(),
    val groupGhostInfo: GroupGhostInfo = GroupGhostInfo(),
    val recoveredShares: List<GhostShareHolder> = listOf(),
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
         cachedRequestWelcomeBackGhost,
         groupGhostInfo,
        recoveredShares,
      )

    context(Raise<ProposalValidationError>)
    private suspend fun storeRecoveredShare(secretShares: List<GhostShareHolder>): Active {

      val filteredShares = secretShares.filter{ ghostShareHolder ->
        recoveredShares.find { storedShareHolder ->
          (ghostShareHolder.ghostEncryptionKey.eq(storedShareHolder.ghostEncryptionKey)) &&
            (ghostShareHolder.ghostShare == storedShareHolder.ghostShare)
        } == null
      }

      return Active(
        groupContext,
        tree,
        keySchedule,
        signaturePrivateKey,
        cachedProposals,
        cachedUpdate,
        cachedQuarantineEnd,
        cachedRequestWelcomeBackGhost,
        groupGhostInfo,
        recoveredShares + filteredShares,
      )
    }

    context(Raise<GhostRecoveryProcessError>)
    suspend fun recoverKeyPairs(): Either<GhostRecoveryProcessError, List<Pair<ULong,HpkeKeyPair>>> = either {
      val shares = mutableListOf<Pair<HpkePublicKey, MutableList<GhostShareHolder>>>()
      recoveredShares.forEach{ghostShareHolder ->
        shares.firstOrNull {
          it.first.eq(ghostShareHolder.ghostEncryptionKey)
        }.also {
          if(it == null){
            shares.add(Pair(ghostShareHolder.ghostEncryptionKey, mutableListOf(ghostShareHolder)))
          } else{
            it.second.add(ghostShareHolder)
          }
        }
      }
      if(shares.isEmpty()){
        raise(GhostRecoveryProcessError.NotEnoughSharesForKeyRecoveryError)
      }

      shares.forEach {
        val indices = mutableSetOf<UInt>()
        val t = it.second[0].ghostShare.t

        it.second.forEach {share ->
          indices.add(share.ghostShare.rank)
        }
        if(indices.size.toUInt() < t){
          raise(GhostRecoveryProcessError.NotEnoughSharesForKeyRecoveryError)
        }
      }

      val secretsWithEpochs = shares.map{
        Pair(
          it.second[0].epoch,
          ShamirSecretSharing.retrieveSecret(it.second.map{ it.ghostShare })
        )
      }

      val res = secretsWithEpochs.map { Pair(it.first, derive(it.second)) }
      res.forEachIndexed{ idx, (_, key) ->
        if(!key.public.eq(shares[idx].first)){
          raise(GhostRecoveryProcessError.IncorrectRecoveredKey(shares[idx].first, key.public))
        }
      }
      res
    }

    suspend fun canRecoverKeyPairs(): Boolean  {
      val shares = mutableListOf<Pair<HpkePublicKey, MutableList<GhostShareHolder>>>()
      recoveredShares.forEach{ghostShareHolder ->
        shares.firstOrNull {
          it.first.eq(ghostShareHolder.ghostEncryptionKey)
        }.also {
          if(it == null){
            shares.add(Pair(ghostShareHolder.ghostEncryptionKey, mutableListOf(ghostShareHolder)))
          } else{
            it.second.add(ghostShareHolder)
          }
        }
      }
      if(shares.isEmpty()){
        return false
      }
      shares.forEach {
        val t = it.second[0].ghostShare.t
        val indices = mutableSetOf<UInt>()
        for (shareHolder in it.second) {
          indices.add(shareHolder.ghostShare.rank)
        }
        if(indices.size.toUInt() < t){
          return false
        }
//        println(indices)
      }
      return true
    }

    fun nextShareHolderRankForShareResend(): UInt  = recoveredShares.fold(0u) { max, share -> if(share.ghostShareHolderRank > max) share.ghostShareHolderRank else max } + 1u

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
        cachedRequestWelcomeBackGhost,
        groupGhostInfo,
        recoveredShares,
      )
    }

    private suspend fun storeRequestWelcomeBackGhost(requestWelcomeBackGhost: RequestWelcomeBackGhost): Active {
      if(requestWelcomeBackGhost.leafIndex != leafIndex) {
        cachedRequestWelcomeBackGhost.add(
          CachedRequestWelcomeBackGhost(
            requestWelcomeBackGhost.leafIndex
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
        cachedRequestWelcomeBackGhost,
        groupGhostInfo,
        recoveredShares,
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
        if (groupGhostInfo.containsGhost(quarantineEnd.leafIndex)) {
          if (groupGhostInfo.hasKeyShares(quarantineEnd.leafIndex, 1u)) {
            val ghostShareHolderList = groupGhostInfo.getKeyShares(quarantineEnd.leafIndex, 1u)
            val ct = encryptWithLabel(
              quarantineEnd.leafNode.encryptionKey,
              "ShareRecoveryMessage",
              ByteArray(0),
              GhostShareHolderList(ghostShareHolderList).encodeUnsafe()
            ).bind()
            Pair(
              newState,
              messages.shareRecoveryMessage(quarantineEnd.leafIndex, epoch ,quarantineEnd.leafNode.encryptionKey, ct).bind()
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
      requestWelcomeBackGhost: RequestWelcomeBackGhost,
    ): Either<ProcessMessageError, GroupState> =
      either {
        ensure(requestWelcomeBackGhost.groupId eq groupId) { MessageRecipientError.WrongGroup(requestWelcomeBackGhost.groupId, groupId) }

        val newState = storeRequestWelcomeBackGhost(requestWelcomeBackGhost)
        validations.validated(requestWelcomeBackGhost).bind()

        newState
      }

    context(Raise<ProcessMessageError>)
    suspend fun process(
      shareResend: ShareResend,
    ): Either<ProcessMessageError, MlsShareRecoveryMessage?> =
      either {
        ensure(shareResend.groupId eq groupId) { MessageRecipientError.WrongGroup(shareResend.groupId, groupId) }

        val encryptionKey = tree.leafNode(shareResend.leafIndex).encryptionKey
        validations.validated(shareResend).bind()
        if (groupGhostInfo.hasKeyShares(shareResend.leafIndex, shareResend.requiredShareHolderRank)) {
          val ghostShareHolderList = groupGhostInfo.getKeyShares(shareResend.leafIndex, shareResend.requiredShareHolderRank)
          val ct = encryptWithLabel(
            encryptionKey,
            "ShareRecoveryMessage",
            ByteArray(0),
            GhostShareHolderList(ghostShareHolderList).encodeUnsafe()
          ).bind()
            messages.shareRecoveryMessage(shareResend.leafIndex, epoch, encryptionKey, ct).bind()
        } else {
          null
        }
      }

    context(Raise<ProcessMessageError>)
    suspend fun process(
      shareRecoveryMessage: ShareRecoveryMessage,
      encryptionPrivateKey: HpkePrivateKey,
    ): Either<ProcessMessageError, Pair<ULong, GroupState>> = either {

      ensure(shareRecoveryMessage.groupId eq groupId) { MessageRecipientError.WrongGroup(shareRecoveryMessage.groupId, groupId) }

//      ensure(cachedUpdate != null) { ShareRecoveryMessageError.MissingCachedUpdateForGhost }

      val res = decryptWithLabel(
        encryptionPrivateKey,
        "ShareRecoveryMessage",
        ByteArray(0),
        shareRecoveryMessage.encryptedShare
      ).bind()

      val shares = GhostShareHolderList.decodeUnsafe(res)

      var ep: ULong = 0u
      shares.ghostShareHolders.forEach {
        if(it.ghostShareHolderRank == 1u){
          ep = shareRecoveryMessage.currentEpoch
        }
      }

//      println("ep = " + ep)

      Pair(ep, storeRecoveredShare(shares.ghostShareHolders))
    }

    context(Raise<ProcessMessageError>)
    suspend fun process(
      welcomeBackGhost: WelcomeBackGhost,
      leafNode: LeafNode<*>,
      keyPair: HpkeKeyPair,
      psks: PskLookup = PskLookup.EMPTY
    ): Either<ProcessMessageError, GroupState> = either {

      ensure(welcomeBackGhost.groupId eq groupId) { MessageRecipientError.WrongGroup(welcomeBackGhost.groupId, groupId) }

//      ensure(cachedUpdate != null) { WelcomeBackGhostMessageError.MissingCachedUpdateForGhost }

//      val keyPair = reconstructPublicKey(cachedUpdate!!.encryptionPrivateKey).bind()

      val groupSecrets = welcomeBackGhost.decryptWelcomeBackGroupSecrets(keyPair).bind()

      val resolvedPsks = PskLookup.resolvePsks(psks, groupSecrets.preSharedKeyIds).bind()
      val pskSecret = ResolvedPsk.calculatePskSecret(cipherSuite, resolvedPsks)

      val groupInfo = welcomeBackGhost.decryptWelcomeBackGroupInfo(groupSecrets.joinerSecret, pskSecret).bind()

      val publicTree =
        groupInfo.extension<RatchetTreeExt>()?.tree ?: raise(WelcomeBackGhostMessageError.MissingRatchetTree)

      groupInfo.verifySignature(publicTree).bind()

      publicTree.check(groupInfo.groupContext).bind()

      val secretTree = PrivateRatchetTree(cipherSuite, leafIndex, mapOf(Pair(publicTree.root,groupSecrets.pathSecret)))

      leafNode.epk = groupInfo.groupContext.epoch

      val newTree = RatchetTree(cipherSuite, publicTree, secretTree).update(leafIndex, leafNode, keyPair.private)

      var groupContext = groupInfo.groupContext

//      for(i in 0..<tree.leaves.size){
//        if(tree.leaves[i] != null){
//          tree.leaves[i]!!.epk = groupContext.epoch
//        }
//      }

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
//      if (cachedUpdate != null) raise(CreateQuarantineEndError.AlreadyUpdatedThisEpoch)

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

//      cachedUpdate = CachedUpdate(newLeaf, newEncryptionKeyPair.private, null, true)

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
        mutableListOf(),
        groupGhostInfo,
        recoveredShares,
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

  internal data class CachedRequestWelcomeBackGhost(
    val leafIndex: LeafIndex,
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

  companion object {
    val MINIMUM_SECRET_SHARING_NB: Int = 3
    val INACTIVITY_DELAY: ULong = 5U
    val UPDATE_QUARANTINE_KEYS_DELAY: ULong = 5u
    val DELETE_FROM_QUARANTINE_DELAY: ULong = 20U

    fun computeSecretSharingTValue(m: Int): Int {
      return ceil(m.toDouble()/2).toInt()
    }

  }
}
