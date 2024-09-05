package com.github.traderjoe95.mls.protocol.tree

import arrow.core.raise.Raise
import arrow.core.raise.ensure
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.error.HpkeDecryptError
import com.github.traderjoe95.mls.protocol.error.SenderTreeUpdateError
import com.github.traderjoe95.mls.protocol.error.UnexpectedError
import com.github.traderjoe95.mls.protocol.group.GhostMember
import com.github.traderjoe95.mls.protocol.group.GhostMemberCommit
import com.github.traderjoe95.mls.protocol.group.GhostShareHolder
import com.github.traderjoe95.mls.protocol.group.GhostShareHolderCommitList
import com.github.traderjoe95.mls.protocol.group.GhostShareHolderCommitList.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.group.GroupContext
import com.github.traderjoe95.mls.protocol.group.GroupState
import com.github.traderjoe95.mls.protocol.types.crypto.Secret
import com.github.traderjoe95.mls.protocol.types.tree.GhostShareDistribution
import com.github.traderjoe95.mls.protocol.types.tree.leaf.LeafNodeSource

///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
// Default Share Distribution Method Utilities
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
internal fun getFilteredDirectPathCleaned(
  tree: RatchetTree,
  from: LeafIndex,
  excludeNewLeaves: Set<LeafIndex>,
): List<Pair<NodeIndex, List<NodeIndex>>> {

  return tree.filteredDirectPath(from).mapNotNull { (nodeIdx, resolution) ->
    val encryptFor = resolution.filter {
      !(it.isLeaf && tree.leafNode(it).source == LeafNodeSource.Ghost) &&
        !(it.isLeaf && excludeNewLeaves.contains(it.leafIndex))
    }
    if(encryptFor.isNotEmpty()){
      Pair(nodeIdx, encryptFor)
    } else {
      null
    }

  }
}

context(Raise<UnexpectedError>)
internal fun getNbSharesWithDefaultMethodFromFilteredDirectPath(
  filteredDirectPath: List<Pair<NodeIndex, List<NodeIndex>>>,
): Int {
  var nbShares = filteredDirectPath.size
  // the direct neighbor of the committer gets the same share (0) as the committer
  // so if the first parent of the committer is not in the filtered direct path,
  // we have to add an additional share that the committer will hold anyway
  if (filteredDirectPath[0].first.level != 1u) {
    nbShares++
  } else {
    ensure(filteredDirectPath[0].second.size == 1 && filteredDirectPath[0].second[0].isLeaf) {
      raise(UnexpectedError("UnexpectedInconsistentFirstNodeInFilteredPath"))
    }
  }
  return nbShares
}

context(Raise<UnexpectedError>)
internal fun canUseDefaultShareDistribution(
  tree: RatchetTree,
  from: LeafIndex,
  excludeNewLeaves: Set<LeafIndex>,
): Boolean {
  val nbShares = getNbSharesWithDefaultMethodFromFilteredDirectPath(
    getFilteredDirectPathCleaned(tree, from, excludeNewLeaves)
  )
  return nbShares >= GroupState.MINIMUM_SECRET_SHARING_NB
}

context(Raise<SenderTreeUpdateError>,Raise<UnexpectedError>)
internal fun generateSharesUsingDefaultShareDistribution(
  tree: RatchetTree,
  from: LeafIndex,
  excludeNewLeaves: Set<LeafIndex>,
  groupContext: GroupContext,
  newGhostMembers: List<GhostMemberCommit>,
  newGhostSecrets: List<Secret>,
): Pair<List<GhostShareDistribution>, List<GhostShareHolder>> {
  with(tree.cipherSuite) {

    if (!canUseDefaultShareDistribution(tree, from, excludeNewLeaves)) {
      raise(UnexpectedError("NotEnoughNodesForDefaultShareDistributionMethod"))
    }

    val filteredDirectPath = getFilteredDirectPathCleaned(tree, from, excludeNewLeaves)
    val nbShares = getNbSharesWithDefaultMethodFromFilteredDirectPath(filteredDirectPath)
    val t = GroupState.computeSecretSharingTValue(nbShares)
    println("Default Share Distribution Method used")
    println("Parameters for secret sharing for this epoch: m = $nbShares, t = $t\n")
    println("Need to perform a total of " +
      filteredDirectPath.fold(0) { acc, elem -> acc + elem.second.size }
      + " encryptions")
//    filteredDirectPath.forEach {
//      println(it.second)
//    }

    val ghostSecretShares = newGhostSecrets.map {
      ShamirSecretSharing.generateShares(it.bytes, t, nbShares)
    }

    val ownRank: UInt = if ((filteredDirectPath[0].first.level == 1u) &&
      (filteredDirectPath[0].second[0].leafIndex < from)
    ) {
      2u
    } else {
      1u
    }
    val ownGhostSecretShares = List(ghostSecretShares.size) { idx ->
      GhostShareHolder.create(
        newGhostMembers[idx].ghostEncryptionKey,
        newGhostMembers[idx].leafIndex,
        groupContext.epoch + 1u,
        ghostSecretShares[idx][0],
        ownRank,
      )
    }

    val provisionalGroupCtx = groupContext.provisional(tree).encoded

    val ghostSharesToDistribute = filteredDirectPath.map { nodeAndRes ->
      val (nodeIdx, encryptFor) = nodeAndRes
      GhostShareDistribution(
        tree.parentNode(nodeIdx).encryptionKey,
        encryptFor.map { idx ->
          encryptWithLabel(
            tree.node(idx).encryptionKey,
            "GhostShareDistribution",
            provisionalGroupCtx,
            GhostShareHolderCommitList.construct(
              newGhostMembers,
              ghostSecretShares,
              nodeIdx.level.toInt() - 1,
            ).encodeUnsafe()
          ).bind()
        }
      )
    }

    return Pair(ghostSharesToDistribute, ownGhostSecretShares)
  }
}

context(Raise<HpkeDecryptError>, Raise<UnexpectedError>)
internal fun decryptSharesUsingDefaultShareDistribution(
  tree: RatchetTree,
  from: LeafIndex,
  excludeNewLeaves: Set<LeafIndex>,
  groupContext: GroupContext,
  shares: List<GhostShareDistribution>,
): List<GhostShareHolder> {

  if (!canUseDefaultShareDistribution(tree, from, excludeNewLeaves)) {
    raise(UnexpectedError("NotEnoughNodesForDefaultShareDistributionMethod"))
  }

  var idxCommonAncestor = -1
  val filteredDirectPath = getFilteredDirectPathCleaned(tree, from, excludeNewLeaves)
  for(i in filteredDirectPath.indices){
    if(tree.leafIndex.isInSubtreeOf(filteredDirectPath[i].first)){
      idxCommonAncestor = i
      break
    }
  }
  if(idxCommonAncestor == -1) { error("No ancestor of own leaf index found in filtered direct path of committer") }
//  println("Common ancestor with " + tree.leafIndex + " is " + filteredDirectPath[idxCommonAncestor])
//  println(filteredDirectPath[idxCommonAncestor].first.subtreeRange)

  if(
    !shares[idxCommonAncestor].encryptionKey.eq(tree.node(filteredDirectPath[idxCommonAncestor].first).encryptionKey)
  ) { raise(UnexpectedError("UpdatePath Error in shares")) }

  filteredDirectPath[idxCommonAncestor].second
    .zip(shares[idxCommonAncestor].encryptedGhostSecrets)
    .firstNotNullOfOrNull { (node, ciphertext) -> tree.getKeyPair(node)?.let(ciphertext::to) }
    ?.let { (ciphertext, keyPair) ->
      tree.cipherSuite.decryptWithLabel(
        keyPair,
        "GhostShareDistribution",
        groupContext.encoded,
        ciphertext,
      ).bind()
    }
    ?.let{

      val rank =
        if (from.isInSubtreeOf(filteredDirectPath[idxCommonAncestor].first.rightChild)) {
          tree.public.getLeafRankInSubtree(tree.leafIndex, filteredDirectPath[idxCommonAncestor].first.leftChild)
        } else {
          tree.public.getLeafRankInSubtree(tree.leafIndex, filteredDirectPath[idxCommonAncestor].first.rightChild)
        }

      return  GhostShareHolderCommitList.decodeUnsafe(it).ghostShareHolders.map { shareHolder ->
        GhostShareHolder.create(shareHolder, rank)
      }
    }

  return listOf()
}

///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
// Horizontal Share Distribution Method Utilities
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
context(Raise<UnexpectedError>)
internal fun canUseHorizontalShareDistribution(
  tree: RatchetTree,
  excludeNewLeaves: Set<LeafIndex>,
): Boolean {
  return tree.public.getLevelWithEnoughNodes(GroupState.MINIMUM_SECRET_SHARING_NB, excludeNewLeaves) != null
}

context(Raise<SenderTreeUpdateError>,Raise<UnexpectedError>)
internal fun generateSharesUsingHorizontalShareDistribution(
  tree: RatchetTree,
  from: LeafIndex,
  excludeNewLeaves: Set<LeafIndex>,
  groupContext: GroupContext,
  newGhostMembers: List<GhostMemberCommit>,
  newGhostSecrets: List<Secret>,
): Pair<List<GhostShareDistribution>, List<GhostShareHolder>> {
  with(tree.cipherSuite) {

    if (!canUseHorizontalShareDistribution(tree, excludeNewLeaves)) {
      raise(UnexpectedError("NotEnoughNodesForHorizontalShareDistributionMethod"))
    }

    val nodes = tree.public.getLevelWithEnoughNodes(GroupState.MINIMUM_SECRET_SHARING_NB, excludeNewLeaves)!!
    val nbShares = nodes.size
    val t = GroupState.computeSecretSharingTValue(nbShares)
    println("Horizontal Share Distribution Method used")
    println("Parameters for secret sharing for this epoch: m = $nbShares, t = $t\n")

    val ghostSecretShares = newGhostSecrets.map {
      ShamirSecretSharing.generateShares(it.bytes, t, nbShares)
    }

    val ownRank = tree.public.getLeafRankInSubtree(from,  nodes.first { from.isInSubtreeOf(it) })
    val ownGhostSecretShares = List(ghostSecretShares.size) { idx ->
      GhostShareHolder.create(
        newGhostMembers[idx].ghostEncryptionKey,
        newGhostMembers[idx].leafIndex,
        groupContext.epoch + 1u,
        ghostSecretShares[idx][0],
        ownRank,
      )
    }

    val provisionalGroupCtx = groupContext.provisional(tree).encoded

    var shareIdx = 1
    val ghostSharesToDistribute = nodes.map{
      GhostShareDistribution(
        tree.node(it).encryptionKey,
        listOf(encryptWithLabel(
          tree.node(it).encryptionKey,
          "GhostShareDistribution",
          provisionalGroupCtx,
          GhostShareHolderCommitList.construct(
            newGhostMembers,
            ghostSecretShares,
            if(from.isInSubtreeOf(it)) 0 else shareIdx++,
          ).encodeUnsafe()
        ).bind())
      )
    }

    return Pair(ghostSharesToDistribute, ownGhostSecretShares)
  }
}


context(Raise<HpkeDecryptError>, Raise<UnexpectedError>)
internal fun decryptSharesUsingHorizontalShareDistribution(
  tree: RatchetTree,
  excludeNewLeaves: Set<LeafIndex>,
  groupContext: GroupContext,
  shares: List<GhostShareDistribution>,
): List<GhostShareHolder> {

  if (!canUseHorizontalShareDistribution(tree, excludeNewLeaves)) {
    raise(UnexpectedError("NotEnoughNodesForHorizontalShareDistributionMethod"))
  }

  shares.forEach {
    if(it.encryptedGhostSecrets.size > 1){
      raise(UnexpectedError("InvalidFormatForEncryptedSharesInHorizontalMethod"))
    }
  }

  tree.public.getLevelWithEnoughNodes(GroupState.MINIMUM_SECRET_SHARING_NB, excludeNewLeaves)?.firstOrNull{
    tree.leafIndex.isInSubtreeOf(it)
  }?.let { nodeIdx ->
    shares.firstOrNull{ ghostShare ->
      ghostShare.encryptionKey.eq(tree.node(nodeIdx).encryptionKey)
    }?.let { ghostShare ->
      tree.cipherSuite.decryptWithLabel(
        tree.getKeyPair(nodeIdx)!!,
        "GhostShareDistribution",
        groupContext.encoded,
        ghostShare.encryptedGhostSecrets[0],
      ).bind().let {
        val rank = tree.public.getLeafRankInSubtree(tree.leafIndex, nodeIdx)

        return  GhostShareHolderCommitList.decodeUnsafe(it).ghostShareHolders.map { shareHolder ->
          GhostShareHolder.create(shareHolder, rank)
        }
      }
    }
  }

  return listOf()
}
