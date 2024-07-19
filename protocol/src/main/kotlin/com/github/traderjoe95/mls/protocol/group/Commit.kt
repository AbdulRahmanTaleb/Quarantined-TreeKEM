package com.github.traderjoe95.mls.protocol.group

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.Tuple4
import arrow.core.getOrElse
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.nullable
import arrow.core.toOption
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite.Companion.zeroesNh
import com.github.traderjoe95.mls.protocol.crypto.KeySchedule
import com.github.traderjoe95.mls.protocol.error.CommitError
import com.github.traderjoe95.mls.protocol.error.GroupGhostInfoError
import com.github.traderjoe95.mls.protocol.error.InvalidCommit
import com.github.traderjoe95.mls.protocol.error.RecipientCommitError
import com.github.traderjoe95.mls.protocol.error.RecipientTreeUpdateError
import com.github.traderjoe95.mls.protocol.error.RemoveValidationError
import com.github.traderjoe95.mls.protocol.error.RemovedFromGroup
import com.github.traderjoe95.mls.protocol.error.SenderCommitError
import com.github.traderjoe95.mls.protocol.error.UnexpectedError
import com.github.traderjoe95.mls.protocol.message.GroupInfo
import com.github.traderjoe95.mls.protocol.message.GroupInfo.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.message.GroupSecrets
import com.github.traderjoe95.mls.protocol.message.KeyPackage
import com.github.traderjoe95.mls.protocol.message.MessageOptions
import com.github.traderjoe95.mls.protocol.message.MlsMessage
import com.github.traderjoe95.mls.protocol.message.UsePublicMessage
import com.github.traderjoe95.mls.protocol.message.Welcome
import com.github.traderjoe95.mls.protocol.message.WelcomeBackGhost
import com.github.traderjoe95.mls.protocol.message.WelcomeBackGroupSecrets
import com.github.traderjoe95.mls.protocol.psk.PreSharedKeyId
import com.github.traderjoe95.mls.protocol.psk.PskLookup
import com.github.traderjoe95.mls.protocol.psk.ResolvedPsk.Companion.updatePskSecret
import com.github.traderjoe95.mls.protocol.service.AuthenticationService
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.tree.RatchetTree
import com.github.traderjoe95.mls.protocol.tree.applyUpdatePath
import com.github.traderjoe95.mls.protocol.tree.applyUpdatePathExternalJoin
import com.github.traderjoe95.mls.protocol.tree.createUpdatePath
import com.github.traderjoe95.mls.protocol.tree.findEquivalentLeaf
import com.github.traderjoe95.mls.protocol.tree.validate
import com.github.traderjoe95.mls.protocol.types.Extension
import com.github.traderjoe95.mls.protocol.types.GroupContextExtension
import com.github.traderjoe95.mls.protocol.types.ProposalType
import com.github.traderjoe95.mls.protocol.types.crypto.Aad
import com.github.traderjoe95.mls.protocol.types.crypto.HpkeKeyPair
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePrivateKey
import com.github.traderjoe95.mls.protocol.types.crypto.Secret
import com.github.traderjoe95.mls.protocol.types.crypto.Signature
import com.github.traderjoe95.mls.protocol.types.crypto.SignaturePrivateKey
import com.github.traderjoe95.mls.protocol.types.framing.Sender
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import com.github.traderjoe95.mls.protocol.types.framing.content.AuthenticatedContent
import com.github.traderjoe95.mls.protocol.types.framing.content.Commit
import com.github.traderjoe95.mls.protocol.types.framing.content.ExternalInit
import com.github.traderjoe95.mls.protocol.types.framing.content.GroupContextExtensions
import com.github.traderjoe95.mls.protocol.types.framing.content.PreSharedKey
import com.github.traderjoe95.mls.protocol.types.framing.content.Proposal
import com.github.traderjoe95.mls.protocol.types.framing.content.ProposalOrRef
import com.github.traderjoe95.mls.protocol.types.framing.content.ReInit
import com.github.traderjoe95.mls.protocol.types.framing.content.Remove
import com.github.traderjoe95.mls.protocol.types.framing.content.Update
import com.github.traderjoe95.mls.protocol.types.framing.enums.SenderType
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode
import com.github.traderjoe95.mls.protocol.types.tree.UpdatePath
import com.github.traderjoe95.mls.protocol.types.tree.leaf.LeafNodeSource
import com.github.traderjoe95.mls.protocol.util.hex
import com.github.traderjoe95.mls.protocol.types.RatchetTree as RatchetTreeExt


private fun GroupState.Active.printGhostUsers(newGhostMembers: List<GhostMemberCommit>){
  if(newGhostMembers.isNotEmpty()){
    print("\nNew ghosts or ghosts key update at epoch " + (groupContext.epoch+1u)  +": ")
    newGhostMembers.forEach {
      print(it.leafIndex.nodeIndex.toString() + ", ")
    }
    println("\n")
  }
}

suspend fun <Identity : Any> GroupState.Active.prepareCommit(
  proposals: List<ProposalOrRef>,
  authenticationService: AuthenticationService<Identity>,
  messageOptions: MessageOptions = UsePublicMessage,
  authenticatedData: ByteArray = byteArrayOf(),
  inReInit: Boolean = false,
  inBranch: Boolean = false,
  psks: PskLookup = this,
  inlineTree: Boolean = true,
  forcePath: Boolean = false,
): Either<SenderCommitError, PrepareCommitResult> =
  either {
    val proposalResult = processProposals(proposals, None, authenticationService, leafIndex, inReInit, inBranch, psks)

    // Determining new ghost members if any
    val (updatedTreeGhost, newGhostMembers, newGhostSecrets, deleteGhostMembers) = updateGhostMembers(proposalResult.updatedTree ?: tree).bind()
//    println("Preparing Commit.")
    printGhostUsers(newGhostMembers)

    val forcePathGhost = newGhostMembers.isNotEmpty() || deleteGhostMembers.isNotEmpty()

    val (updatedTree, updatePath, pathSecrets, ownGhostShares) =
      if (proposalResult.updatePathRequired || forcePath || forcePathGhost) {
        createUpdatePath(
          updatedTreeGhost,
          proposalResult.newMemberLeafIndices(),
          groupContext.withExtensions((proposalResult as? ProcessProposalsResult.CommitByMember)?.extensions),
          signaturePrivateKey,
          newGhostMembers,
          newGhostSecrets
        ).bind()
      } else {
        Tuple4(updatedTreeGhost, null, listOf(), listOf())
      }

    groupGhostInfo.addNewGhostKeyShares(ownGhostShares)

    val commitSecret = nullable { deriveSecret(pathSecrets.lastOrNull().bind(), "path") } ?: zeroesNh


    val partialCommit =
      messages.createAuthenticatedContent(
        Commit(proposals, updatePath.toOption(), newGhostMembers, deleteGhostMembers),
        messageOptions,
        authenticatedData,
      )

    val updatedGroupContext =
      groupContext.evolve(
        partialCommit.wireFormat,
        partialCommit.framedContent,
        partialCommit.signature,
        updatedTree,
        newExtensions = (proposalResult as? ProcessProposalsResult.CommitByMember)?.extensions,
      )

    val (newKeySchedule, joinerSecret, welcomeSecret, welcomeBackSecret) =
      keySchedule.nextEpoch(
        commitSecret,
        updatedGroupContext,
        proposalResult.pskSecret,
        (proposalResult as? ProcessProposalsResult.ExternalJoin)?.externalInitSecret,
      )

    val confirmationTag = mac(newKeySchedule.confirmationKey, updatedGroupContext.confirmedTranscriptHash)

    val updatedGroupState =
      proposalResult.createNextEpochState(
        updatedGroupContext.withInterimTranscriptHash(
          newInterimTranscriptHash(
            cipherSuite,
            updatedGroupContext.confirmedTranscriptHash,
            confirmationTag,
          ),
        ),
        updatedTree,
        newKeySchedule,
      )
    val groupInfo =
      GroupInfo.create(
        updatedGroupContext,
        confirmationTag,
        listOfNotNull(
          RatchetTreeExt(updatedTree).takeIf { inlineTree },
          *Extension.grease(),
        ),
        leafIndex,
        signaturePrivateKey,
      ).bind()


    val ghostReconnectNodes = cachedRequestWelcomeBackGhost.map{
      updatedTree.leafNode(it.leafIndex)
    }

    PrepareCommitResult(
      updatedGroupState,
      messages.protectCommit(partialCommit, confirmationTag, messageOptions),
      proposalResult.welcomeTo
        ?.takeIf { it.isNotEmpty() }
        ?.let { newMembers ->
          listOf(
            PrepareCommitResult.WelcomeMessage(
              newMembers.createWelcome(
                groupInfo,
                updatedTree,
                pathSecrets,
                welcomeSecret,
                joinerSecret,
                proposalResult.pskIds,
              ),
              newMembers.map { it.second },
            ),
          )
        } ?: listOf(),

      ghostReconnectNodes.takeIf { it.isNotEmpty() }
        ?.let{ newMembers ->
          listOf(
            PrepareCommitResult.WelcomeBackGhostMessage(
              newMembers.createWelcomeBack(
                groupInfo,
                pathSecrets.last(),
                welcomeBackSecret,
                joinerSecret,
                proposalResult.pskIds,
              ),
              newMembers.map { it },
            ),
          )
        } ?: listOf(),
    )
  }

suspend fun <Identity : Any> GroupState.Active.processCommit(
  authenticatedCommit: AuthenticatedContent<Commit>,
  authenticationService: AuthenticationService<Identity>,
  psks: PskLookup = this,
  ghostKeyPair: HpkeKeyPair? = null,
  newGhostLeafNode: LeafNode<*>? = null,
  newGhostPrivateEncryptionKey: HpkePrivateKey? = null,
): Either<RecipientCommitError, GroupState> =
  either {
    val commit = authenticatedCommit.framedContent
    val proposalResult = commit.content.validateAndApply(commit.sender, psks, authenticationService, newGhostLeafNode, newGhostPrivateEncryptionKey)
    val updatePath = commit.content.updatePath

//    println("Processing Commit.")
//    printGhostUsers()

    val preTree = proposalResult.updatedTree ?: tree

    with(preTree) {
      if (leafIndex.isBlank) raise(RemovedFromGroup)
    }

    val updatedTreeGhost =
      processGhostMembers(preTree, commit.content.ghostUsers, commit.content.deadGhostsToDelete ,ghostKeyPair).bind()

    val (updatedTree, commitSecret, ghostSecretShares) =
      updatePath.map { path ->
        path.leafNode.epk = commit.epoch + 1u
        updatedTreeGhost.applyCommitUpdatePath(
          groupContext.withExtensions((proposalResult as? ProcessProposalsResult.CommitByMember)?.extensions),
          path,
          commit.sender,
          proposalResult.newMemberLeafIndices(),
          commit.content.ghostUsers,
        )
      }.getOrElse { Triple(updatedTreeGhost, zeroesNh, null) }

    if(ghostSecretShares != null){
      groupGhostInfo.addNewGhostKeyShares(ghostSecretShares)
    }

    val updatedGroupContext =
      groupContext.evolve(
        authenticatedCommit.wireFormat,
        commit,
        authenticatedCommit.signature,
        updatedTree,
        newExtensions = (proposalResult as? ProcessProposalsResult.CommitByMember)?.extensions,
      )

    val (newKeySchedule, _, _, _) =
      keySchedule.nextEpoch(
        commitSecret,
        updatedGroupContext,
        proposalResult.pskSecret,
        (proposalResult as? ProcessProposalsResult.ExternalJoin)?.externalInitSecret,
      )

    verifyMac(
      newKeySchedule.confirmationKey,
      updatedGroupContext.confirmedTranscriptHash,
      authenticatedCommit.confirmationTag!!,
    )

    proposalResult.createNextEpochState(
      updatedGroupContext.withInterimTranscriptHash(
        newInterimTranscriptHash(
          cipherSuite,
          updatedGroupContext.confirmedTranscriptHash,
          authenticatedCommit.confirmationTag,
        ),
      ),
      updatedTree,
      newKeySchedule,
    )
  }


private fun GroupState.Active.updateGhostMembers(tree: RatchetTree): Either<GroupGhostInfoError,Tuple4<RatchetTree, List<GhostMemberCommit>, List<Secret>, List<LeafIndex>>> =
  either {


    val deleteMembers = groupGhostInfo.removeDeadGhosts(tree, groupContext.epoch, GroupState.DELETE_FROM_QUARANTINE_DELAY)

    val newGhostMembers = mutableListOf<GhostMemberCommit>()
    val newGhostSecrets = mutableListOf<Secret>()

    val interTree = if(deleteMembers.isNotEmpty()){
      tree.remove(deleteMembers)
    } else {
      tree
    }

    var newTree = interTree

    interTree.leafNodeIndices.forEach {
      if(it.leafIndex != leafIndex){
        val leaf = interTree.leaves[it.leafIndex.value.toInt()]
        // New Ghost
        if((leaf != null) && (leaf.equar.compareTo(0U) == 0) && ((groupContext.epoch + 1u - leaf.epk) >= GroupState.INACTIVITY_DELAY)){

          // Generating a new secret for each new ghost
          val secret = generateSecret(hashLen)
          newGhostSecrets.add(secret)

          // Generating a new encryption key for each new ghost
          // The public keys are returned in the commit message
          val newKeys = deriveKeyPair(secret)

          newGhostMembers.add(groupGhostInfo.addNewGhostMember(it.leafIndex, groupContext.epoch+1u, newKeys.public))

          val newGhostLeafNode = LeafNode(newKeys.public, leaf.signaturePublicKey, leaf.credential,
            leaf.capabilities, LeafNodeSource.Ghost, null, leaf.extensions, Signature(ByteArray(1)), leaf.epk,
            groupContext.epoch + 1u
          )
          newTree = newTree.update(it.leafIndex, newGhostLeafNode)
        }
        // Old Ghost but need to renew Quarantine Key
        else if((leaf != null) && (leaf.source == LeafNodeSource.Ghost)) {
          if(groupContext.epoch + 1u - groupGhostInfo.lastGhostKeyUpdate(it.leafIndex) >= GroupState.UPDATE_QUARANTINE_KEYS_DELAY){
            // Generating a new secret for each new ghost
            val secret = generateSecret(hashLen)
            newGhostSecrets.add(secret)

            // Generating a new encryption key for each new ghost
            // The public keys are returned in the commit message
            val newKeys = deriveKeyPair(secret)

            newGhostMembers.add(groupGhostInfo.addNewGhostKey(it.leafIndex, groupContext.epoch+1u, newKeys.public))

            val newGhostLeafNode = LeafNode(newKeys.public, leaf.signaturePublicKey, leaf.credential,
              leaf.capabilities, LeafNodeSource.Ghost, null, leaf.extensions, Signature(ByteArray(1)), leaf.epk,
              leaf.equar
            )

            newTree = newTree.update(it.leafIndex, newGhostLeafNode)

          }
        }
      }
    }

    Tuple4(newTree, newGhostMembers, newGhostSecrets, deleteMembers)
  }

private fun GroupState.Active.processGhostMembers(
  tree: RatchetTree,
  newGhostUsers: List<GhostMemberCommit>,
  deleteGhostMembers: List<LeafIndex>,
  ghostKeyPair: HpkeKeyPair? = null,
  ) : Either<CommitError,RatchetTree>  = either{

  val interTree = if(deleteGhostMembers.isNotEmpty()){
    deleteGhostMembers.forEach{ groupGhostInfo.removeGhostMemberWithShares(it) }
    tree.remove(deleteGhostMembers)
  } else {
    tree
  }

  var newTree = interTree

  newGhostUsers.forEach { ghostMember ->

    val leaf = interTree.leaves[ghostMember.leafIndex.value.toInt()] ?: raise(CommitError.GhostUserNotFound)


    val newGhostLeafNode = LeafNode(
      ghostMember.ghostEncryptionKey,
      leaf.signaturePublicKey,
      leaf.credential,
      leaf.capabilities,
      LeafNodeSource.Ghost,
      null,
      leaf.extensions,
      Signature(ByteArray(1)),
      leaf.epk,
      if(leaf.source == LeafNodeSource.Ghost) leaf.equar else ghostMember.ghostEncryptionKeyEpoch
    )

//    println(ghostMember.leafIndex.toString() + leaf.source)
    if(leaf.source == LeafNodeSource.Ghost){
      groupGhostInfo.addNewGhostKey(ghostMember.leafIndex, ghostMember.ghostEncryptionKeyEpoch, ghostMember.ghostEncryptionKey)
    } else {
      groupGhostInfo.addNewGhostMember(ghostMember.leafIndex, ghostMember.ghostEncryptionKeyEpoch, ghostMember.ghostEncryptionKey)
    }

    newTree = if(ghostMember.leafIndex.eq(newTree.leafIndex)){
      newTree.update(ghostMember.leafIndex, newGhostLeafNode, ghostKeyPair!!.private)
    }
    else{
      newTree.update(ghostMember.leafIndex, newGhostLeafNode)
    }
  }

  newTree
}

context(GroupState.Active, Raise<CommitError>)
private suspend fun <Identity : Any> Commit.validateAndApply(
  sender: Sender,
  psks: PskLookup,
  authenticationService: AuthenticationService<Identity>,
  newGhostLeafNode: LeafNode<*>? = null,
  newGhostPrivateEncryptionKey: HpkePrivateKey? = null,
): ProcessProposalsResult =
  processProposals(
    proposals,
    updatePath,
    authenticationService,
    when (sender.type) {
      SenderType.Member -> sender.index!!
      SenderType.NewMemberCommit -> null
      else -> raise(InvalidCommit.BadCommitSender(sender.type))
    },
    inReInit = false,
    inBranch = false,
    psks,
    newGhostLeafNode,
    newGhostPrivateEncryptionKey
  ).also { result ->
    if (result.updatePathRequired && updatePath.isNone()) raise(InvalidCommit.MissingUpdatePath)

    updatePath.onSome {
      it.leafNode.validate(
        tree,
        groupContext,
        sender.index
          ?: tree.firstBlankLeaf
          ?: (tree.leafNodeIndices.last + 2U).leafIndex,
        LeafNodeSource.Commit,
      )
    }
  }

context(GroupState)
private fun ProcessProposalsResult.newMemberLeafIndices(): Set<LeafIndex> =
  when (this) {
    is ProcessProposalsResult.CommitByMember -> welcomeTo.map { it.first }.toSet()
    is ProcessProposalsResult.ExternalJoin -> setOf(tree.firstBlankLeaf ?: (tree.leafNodeIndices.last + 2U).leafIndex)
    is ProcessProposalsResult.ReInitCommit -> setOf()
  }

context(Raise<RecipientTreeUpdateError>,Raise<UnexpectedError>)
private fun RatchetTree.applyCommitUpdatePath(
  groupContext: GroupContext,
  updatePath: UpdatePath,
  sender: Sender,
  excludeNewLeaves: Set<LeafIndex>,
  newGhostUsers: List<GhostMemberCommit> = listOf(),
): Triple<RatchetTree, Secret, List<GhostShareHolder>> =
  if (sender.type == SenderType.Member) {
    applyUpdatePath(this, groupContext, sender.index!!, updatePath, excludeNewLeaves, newGhostUsers)
  } else {
    applyUpdatePathExternalJoin(groupContext, updatePath, excludeNewLeaves)
  }

context(GroupState.Active, Raise<SenderCommitError>)
private fun List<Pair<LeafIndex, KeyPackage>>.createWelcome(
  groupInfo: GroupInfo,
  newTree: RatchetTree,
  pathSecrets: List<Secret>,
  welcomeSecret: Secret,
  joinerSecret: Secret,
  pskIds: List<PreSharedKeyId>,
): MlsMessage<Welcome> {
  val welcomeNonce = expandWithLabel(welcomeSecret, "nonce", byteArrayOf(), nonceLen).asNonce
  val welcomeKey = expandWithLabel(welcomeSecret, "key", byteArrayOf(), keyLen)

  val encryptedGroupInfo = encryptAead(welcomeKey, welcomeNonce, Aad.empty, groupInfo.encodeUnsafe())

  val filteredPath = newTree.filteredDirectPath(leafIndex).map { it.first }

  val encryptedGroupSecrets =
    map { (newLeaf, keyPackage) ->
      val commonAncestorIdx = filteredPath.indexOfFirst { newLeaf.isInSubtreeOf(it) }
      val pathSecret = pathSecrets.getOrNull(commonAncestorIdx).toOption()

      GroupSecrets(joinerSecret, pathSecret, pskIds)
        .encrypt(cipherSuite, keyPackage, encryptedGroupInfo)
    }.bindAll()

  return MlsMessage.welcome(
    groupContext.cipherSuite,
    encryptedGroupSecrets,
    encryptedGroupInfo,
  )
}

context(GroupState.Active, Raise<SenderCommitError>)
private fun List<LeafNode<*>>.createWelcomeBack(
  groupInfo: GroupInfo,
  pathSecret: Secret,
  welcomeBackSecret: Secret,
  joinerSecret: Secret,
  pskIds: List<PreSharedKeyId>,
): MlsMessage<WelcomeBackGhost> {
  val welcomeBackNonce = expandWithLabel(welcomeBackSecret, "nonce", byteArrayOf(), nonceLen).asNonce
  val welcomeBackKey = expandWithLabel(welcomeBackSecret, "key", byteArrayOf(), keyLen)

  val encryptedGroupInfo = encryptAead(welcomeBackKey, welcomeBackNonce, Aad.empty, groupInfo.encodeUnsafe())

  val encryptedGroupSecrets =
    map { leaf ->
      WelcomeBackGroupSecrets(joinerSecret, pathSecret, pskIds)
        .encrypt(cipherSuite, leaf.encryptionKey, encryptedGroupInfo)
    }.bindAll()

  return MlsMessage.welcomeBackGhost(
    groupContext.cipherSuite,
    groupId,
    encryptedGroupSecrets,
    encryptedGroupInfo,
  )
}

context(Raise<CommitError>)
private suspend fun <Identity : Any> GroupState.Active.processProposals(
  proposals: List<ProposalOrRef>,
  updatePath: Option<UpdatePath>,
  authenticationService: AuthenticationService<Identity>,
  committerLeafIdx: LeafIndex?,
  inReInit: Boolean = false,
  inBranch: Boolean = false,
  psks: PskLookup,
  newGhostLeafNode: LeafNode<*>? = null,
  newGhostPrivateEncryptionKey: HpkePrivateKey? = null,
): ProcessProposalsResult {
  val resolved: ResolvedProposals = mutableMapOf()

  proposals.forEach { proposalOrRef ->
    val (proposal, sender) =
      when (proposalOrRef) {
        is Proposal -> proposalOrRef to committerLeafIdx

        is Proposal.Ref ->
          if (committerLeafIdx == null) {
            raise(InvalidCommit.NoProposalRefAllowed)
          } else {
            getStoredProposal(proposalOrRef).let { it.proposal to it.sender }
          }
      }

    resolved.compute(proposal.type) { _, current -> (current ?: listOf()) + (proposal to sender) }
  }

  if (committerLeafIdx == null) {
    resolved.validateExternal()
  } else {
    resolved.validateMember(committerLeafIdx)
  }

  var requiresUpdatePath = proposals.isEmpty()
  var updatedTree = tree
  var extensions: List<GroupContextExtension<*>>? = null
  val welcomeTo = mutableListOf<Pair<LeafIndex, KeyPackage>>()

  var pskSecret = zeroesNh
  var pskIndex = 0
  val pskCount = resolved[ProposalType.Psk]?.size ?: 0
  val pskIds = mutableListOf<PreSharedKeyId>()

  var newSignaturePrivateKey: SignaturePrivateKey? = null

  ProposalType.ORDER.asSequence()
    .flatMap { resolved.getAll<Proposal>(it).asSequence() }
    .forEach { (proposal, from) ->
      when (proposal) {
        is GroupContextExtensions -> {
          validations.validated(
            proposal,
            updatedTree,
            resolved.getAll<Remove>(ProposalType.Remove).map { it.first.removed }.toSet(),
          )

          extensions = proposal.extensions
        }

        is Update -> {
          validations.validated(proposal, from!!, updatedTree).bind()

          val cached = cachedUpdate

          proposal.leafNode.epk = groupContext.epoch + 1u

          updatedTree =
            when {
              from != leafIndex -> updatedTree.update(from, proposal.leafNode)

              cached != null && proposal.leafNode == cached.leafNode -> {
                newSignaturePrivateKey = cached.signaturePrivateKey
                updatedTree.update(from, proposal.leafNode, cached.encryptionPrivateKey)
              }

              cached != null ->
                raise(CommitError.CachedUpdateDoesNotMatch(cached.leafNode, proposal.leafNode))

              else -> {
                raise(CommitError.CachedUpdateMissing)
              }
            }

          requiresUpdatePath = true
        }

        is Remove -> {
          validations.validated(proposal, updatedTree).bind()

          if (committerLeafIdx == null) {
            val expectedClient = updatePath.getOrNull()!!.leafNode.credential

            if (!authenticationService.isSameClient(expectedClient, updatedTree.leafNode(proposal.removed).credential).bind()) {
              raise(RemoveValidationError.UnauthorizedExternalRemove(proposal.removed))
            }
          }

          updatedTree = updatedTree.remove(proposal.removed)
          requiresUpdatePath = true
        }

        is Add -> {
          validations.validated(proposal, updatedTree).bind()

//          proposal.keyPackage.leafNode.epk = groupContext.epoch + 1u

          with(authenticationService) { updatedTree.findEquivalentLeaf(proposal.keyPackage.leafNode) }
            ?.also { raise(InvalidCommit.AlreadyMember(proposal.keyPackage, it)) }

          val newLeaf = LeafNode.copy(proposal.keyPackage.leafNode)
          newLeaf.epk = groupContext.epoch + 1u

          val (treeWithNewMember, newMemberLeaf) = updatedTree.insert(newLeaf)

          updatedTree = treeWithNewMember
          welcomeTo.add(newMemberLeaf to proposal.keyPackage)
        }

        is PreSharedKey -> {
          val psk = validations.validated(proposal, psks, inReInit, inBranch).bind()!!

          pskSecret = updatePskSecret(pskSecret, proposal.pskId, psk, pskIndex++, pskCount)
          pskIds.add(proposal.pskId)
        }

        is ExternalInit ->
          return ProcessProposalsResult.ExternalJoin(
            externalInitSecret = export(proposal.kemOutput, deriveKeyPair(keySchedule.externalSecret), "").bind(),
            pskSecret,
            updatedTree,
          )

        is ReInit -> {
          validations.validated(proposal).bind()

          return ProcessProposalsResult.ReInitCommit(proposal, zeroesNh)
        }

      }
    }


  cachedQuarantineEnd.forEach {
    it.leafNode.epk = groupContext.epoch + 1u
    it.leafNode.equar = 0u
    updatedTree = updatedTree.update(it.leafIndex, it.leafNode)
    requiresUpdatePath = true

    groupGhostInfo.removeGhostMember(it.leafIndex)
  }

  if(newGhostLeafNode != null && newGhostPrivateEncryptionKey != null){
    newGhostLeafNode.epk = groupContext.epoch + 1u
    newGhostLeafNode.equar = 0u
    updatedTree = updatedTree.update(leafIndex, newGhostLeafNode, newGhostPrivateEncryptionKey)
    requiresUpdatePath = true

    groupGhostInfo.removeGhostMember(leafIndex)
  }

  return ProcessProposalsResult.CommitByMember(
    requiresUpdatePath,
    updatedTree,
    extensions,
    pskSecret,
    pskIds,
    welcomeTo,
    newSignaturePrivateKey,
  )
}

internal sealed interface ProcessProposalsResult {
  val updatePathRequired: Boolean

  val pskSecret: Secret
  val pskIds: List<PreSharedKeyId>
    get() = listOf()

  val updatedTree: RatchetTree?

  val welcomeTo: List<Pair<LeafIndex, KeyPackage>>?
    get() = null

  context(GroupState.Active)
  fun createNextEpochState(
    groupContext: GroupContext,
    tree: RatchetTree,
    keySchedule: KeySchedule,
  ): GroupState = nextEpoch(groupContext, tree, keySchedule)

  data class CommitByMember(
    override val updatePathRequired: Boolean,
    override val updatedTree: RatchetTree,
    val extensions: List<GroupContextExtension<*>>?,
    override val pskSecret: Secret,
    override val pskIds: List<PreSharedKeyId>,
    override val welcomeTo: List<Pair<LeafIndex, KeyPackage>>,
    val newSignaturePrivateKey: SignaturePrivateKey?,
  ) : ProcessProposalsResult {
    context(GroupState.Active)
    override fun createNextEpochState(
      groupContext: GroupContext,
      tree: RatchetTree,
      keySchedule: KeySchedule,
    ): GroupState = nextEpoch(groupContext, tree, keySchedule, newSignaturePrivateKey ?: signaturePrivateKey)
  }

  data class ExternalJoin(
    val externalInitSecret: Secret,
    override val pskSecret: Secret,
    override val updatedTree: RatchetTree,
  ) : ProcessProposalsResult {
    override val updatePathRequired: Boolean = true
  }

  data class ReInitCommit(
    val reInit: ReInit,
    override val pskSecret: Secret,
  ) : ProcessProposalsResult {
    override val updatePathRequired: Boolean = false
    override val updatedTree: RatchetTree? = null

    context(GroupState.Active)
    override fun createNextEpochState(
      groupContext: GroupContext,
      tree: RatchetTree,
      keySchedule: KeySchedule,
    ): GroupState = suspend(groupContext, tree, keySchedule, reInit)
  }
}
