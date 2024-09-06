package com.github.traderjoe95.mls.protocol.tree

import arrow.core.Either
import arrow.core.Tuple4
import arrow.core.prependTo
import arrow.core.raise.Raise
import com.github.traderjoe95.mls.codec.util.uSize
import com.github.traderjoe95.mls.protocol.crypto.ICipherSuite
import com.github.traderjoe95.mls.protocol.error.HpkeDecryptError
import com.github.traderjoe95.mls.protocol.error.JoinError
import com.github.traderjoe95.mls.protocol.error.RecipientTreeUpdateError
import com.github.traderjoe95.mls.protocol.error.SenderTreeUpdateError
import com.github.traderjoe95.mls.protocol.error.WrongParentHash
import com.github.traderjoe95.mls.protocol.error.WrongUpdatePathLength
import com.github.traderjoe95.mls.protocol.group.GroupContext
import com.github.traderjoe95.mls.protocol.types.RefinedBytes.Companion.neqNullable
import com.github.traderjoe95.mls.protocol.types.crypto.Secret
import com.github.traderjoe95.mls.protocol.types.crypto.Secret.Companion.asSecret
import com.github.traderjoe95.mls.protocol.types.crypto.SignaturePrivateKey
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode
import com.github.traderjoe95.mls.protocol.types.tree.ParentNode
import com.github.traderjoe95.mls.protocol.types.tree.UpdatePath
import com.github.traderjoe95.mls.protocol.types.tree.UpdatePathNode
import com.github.traderjoe95.mls.protocol.types.tree.leaf.LeafNodeSource
import com.github.traderjoe95.mls.protocol.util.foldWith
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.github.traderjoe95.mls.codec.util.get
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.error.UnexpectedError
import com.github.traderjoe95.mls.protocol.group.GhostMemberCommit
import com.github.traderjoe95.mls.protocol.group.GhostShareHolder
import com.github.traderjoe95.mls.protocol.group.GhostShareHolderCommitList
import com.github.traderjoe95.mls.protocol.group.GhostShareHolderCommitList.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.group.GroupState
import com.github.traderjoe95.mls.protocol.types.tree.GhostShareDistribution


context(Raise<SenderTreeUpdateError>)
internal fun createUpdatePath(
  originalTree: RatchetTree,
  excludeNewLeaves: Set<LeafIndex>,
  groupContext: GroupContext,
  signaturePrivateKey: SignaturePrivateKey,
  newGhostMembers: List<GhostMemberCommit> = listOf(),
  newGhostSecrets: List<Secret> = listOf(),
): Either<UnexpectedError, Tuple4<RatchetTree, UpdatePath, List<Secret>, List<GhostShareHolder>>> =
  createUpdatePath(
    originalTree,
    originalTree.leafIndex,
    excludeNewLeaves,
    groupContext,
    signaturePrivateKey,
    newGhostMembers,
    newGhostSecrets
  )

context(Raise<SenderTreeUpdateError>)
internal fun createUpdatePath(
  originalTree: RatchetTree,
  from: LeafIndex,
  excludeNewLeaves: Set<LeafIndex>,
  groupContext: GroupContext,
  signaturePrivateKey: SignaturePrivateKey,
  newGhostMembers: List<GhostMemberCommit> = listOf(),
  newGhostSecrets: List<Secret> = listOf(),
): Either<UnexpectedError, Tuple4<RatchetTree, UpdatePath, List<Secret>, List<GhostShareHolder>>> = either {
  with(originalTree.cipherSuite) {
    val oldLeafNode = originalTree.leafNode(from)

    val leafPathSecret = generateSecret(hashLen)
    val leafNodeSecret = deriveSecret(leafPathSecret, "node")
    val leafKp = deriveKeyPair(leafNodeSecret)

    val directPath = originalTree.directPath(from)
    val filteredDirectPath = originalTree.filteredDirectPath(from)

    println("filtered path size = " + filteredDirectPath.size)
    println("nb encryptions = " + filteredDirectPath.fold(0) { acc, elem -> acc + elem.second.size })


    val pathSecrets = mutableListOf(leafPathSecret)

    val updatedTreeWithoutLeaf =
      originalTree
        .blank(directPath)
        .foldWith(filteredDirectPath) { (nodeIdx, _) ->
          val newPathSecret = deriveSecret(pathSecrets.last(), "path").also(pathSecrets::add)
          val nodeSecret = deriveSecret(newPathSecret, "node")
          val nodeKp = deriveKeyPair(nodeSecret)
          nodeSecret.wipe()

          set(nodeIdx, ParentNode.new(nodeKp.public), newPathSecret)
        }
        .foldWith(
          from.nodeIndex.prependTo(filteredDirectPath.map { it.first }).zipWithNext().reversed(),
        ) { (nodeIdx, parent) ->
          updateOrNull(nodeIdx) {
            withParentHash(
              parentHash = parentHash(
                cipherSuite,
                parent,
                from
              )
            )
          }
        }

    val newLeafNode =
      LeafNode.commit(
        originalTree.cipherSuite,
        leafKp.public,
        oldLeafNode,
        updatedTreeWithoutLeaf.parentHash(
          originalTree.cipherSuite,
          filteredDirectPath.first().first,
          from,
        ),
        from,
        groupContext.groupId,
        signaturePrivateKey,
        groupContext.epoch + 1U,
      ).bind()
    val updatedTree =
      updatedTreeWithoutLeaf.set(
        from,
        newLeafNode,
        leafKp.private,
      )

    val provisionalGroupCtx = groupContext.provisional(updatedTree)
    val excludedNodeIndices = excludeNewLeaves.map { it.nodeIndex }.toSet()

    // Creating Ghost Shares and Distributing them
    var updatePathNodes: List<UpdatePathNode> = listOf()
    var ghostShares: List<GhostShareDistribution> = listOf()
    var ownGhostSecretShares: List<GhostShareHolder> = listOf()

    if(newGhostSecrets.isNotEmpty() && canUseDefaultShareDistribution(updatedTree, from, excludeNewLeaves)){
      generateSharesUsingDefaultShareDistribution(
        updatedTree,
        from,
        excludeNewLeaves,
        groupContext,
        filteredDirectPath,
        pathSecrets.drop(1),
        newGhostMembers,
        newGhostSecrets,
      ).let{
        updatePathNodes = it.first
        ownGhostSecretShares = it.second
      }
    }

    else if(newGhostSecrets.isNotEmpty()){
      if(canUseHorizontalShareDistribution(updatedTree, excludeNewLeaves)) {
        generateSharesUsingHorizontalShareDistribution(
          updatedTree,
          from,
          excludeNewLeaves,
          groupContext,
          newGhostMembers,
          newGhostSecrets,
        ).let{
          ghostShares = it.first
          ownGhostSecretShares = it.second
        }
      }
      else {
        raise(UnexpectedError("NotEnoughNodesForSecretSharing"))
      }
    }

    if(updatePathNodes.size == 0){
      updatePathNodes =
        filteredDirectPath.zip(pathSecrets.drop(1)).map { (nodeAndRes, pathSecret) ->
          val (nodeIdx, resolution) = nodeAndRes
          val encryptFor = resolution - excludedNodeIndices

          UpdatePathNode(
            updatedTree.parentNode(nodeIdx).encryptionKey,
            encryptFor.map { idx ->
              encryptWithLabel(
                originalTree.node(idx).encryptionKey,
                "UpdatePathNode",
                provisionalGroupCtx.encoded,
                pathSecret.bytes,
              ).bind()
            })
        }
    }


    Tuple4(
      updatedTree,
      UpdatePath(newLeafNode, updatePathNodes, ghostShares),
      pathSecrets.drop(1),
      ownGhostSecretShares,
    )
  }
}

context(Raise<RecipientTreeUpdateError>, Raise<UnexpectedError>)
internal fun applyUpdatePath(
  originalTree: RatchetTree,
  groupContext: GroupContext,
  fromLeafIndex: LeafIndex,
  updatePath: UpdatePath,
  excludeNewLeaves: Set<LeafIndex>,
  newGhostUsers: List<GhostMemberCommit> = listOf(),
): Triple<RatchetTree, Secret, List<GhostShareHolder>> {
  var updatedTree = originalTree.mergeUpdatePath(fromLeafIndex, updatePath)

  val provisionalGroupCtx = groupContext.provisional(updatedTree)

  val excludedNodeIndices = excludeNewLeaves.map { it.nodeIndex }.toSet()
  val (commonAncestor, pathSecret, ghostSecretShares) =
    updatedTree.extractCommonPathSecret(
      fromLeafIndex,
      updatePath,
      provisionalGroupCtx,
      excludedNodeIndices,
      newGhostUsers,
    )

  updatedTree = updatedTree.insertPathSecrets(commonAncestor, pathSecret)

  return Triple(updatedTree, updatedTree.private.commitSecret, ghostSecretShares)
}

context(Raise<RecipientTreeUpdateError>,Raise<UnexpectedError>)
internal fun RatchetTree.applyUpdatePathExternalJoin(
  groupContext: GroupContext,
  updatePath: UpdatePath,
  excludeNewLeaves: Set<LeafIndex>,
): Triple<RatchetTree, Secret, List<GhostShareHolder>> =
  insert(updatePath.leafNode).let { (tree, newLeaf) ->
    applyUpdatePath(tree, groupContext, newLeaf, updatePath, excludeNewLeaves)
  }

context(Raise<RecipientTreeUpdateError>)
internal fun RatchetTree.mergeUpdatePath(
  fromLeafIdx: LeafIndex,
  updatePath: UpdatePath,
): RatchetTree {
  val directPath = directPath(fromLeafIdx)
  val filteredDirectPath = filteredDirectPath(fromLeafIdx)

  if (filteredDirectPath.uSize != updatePath.size) {
    raise(WrongUpdatePathLength(filteredDirectPath.uSize, updatePath.size))
  }

  return blank(directPath)
    .foldWith(filteredDirectPath.zip(updatePath.nodes)) { (nodeAndRes, updateNode) ->
      set(nodeAndRes.first, ParentNode.new(updateNode.encryptionKey))
    }
    .foldWith(filteredDirectPath.map { it.first }.zipWithNext().reversed()) { (nodeIdx, parentIdx) ->
      updateOrNull(nodeIdx) { withParentHash(parentHash = parentHash(cipherSuite, parentIdx, fromLeafIdx)) }
    }
    .let { updatedWithoutLeaf ->
      val computedParentHash = updatedWithoutLeaf.parentHash(cipherSuite, filteredDirectPath.first().first, fromLeafIdx)

      if (updatePath.leafNode.parentHash neqNullable computedParentHash) {
        raise(WrongParentHash(computedParentHash.bytes, updatePath.leafNode.parentHash!!.bytes))
      }

      // updatePath.leafNode.epk = 1000U
      updatedWithoutLeaf.set(fromLeafIdx, updatePath.leafNode)
    }
}

context(Raise<HpkeDecryptError>, Raise<UnexpectedError>)
internal fun RatchetTree.extractCommonPathSecret(
  fromLeafIdx: LeafIndex,
  updatePath: UpdatePath,
  groupContext: GroupContext,
  excludeNewLeaves: Set<NodeIndex>,
  newGhostUsers: List<GhostMemberCommit> = listOf(),
): Triple<NodeIndex, Secret, List<GhostShareHolder>> {
  val filteredDirectPath = filteredDirectPath(fromLeafIdx)

  var idxCommonAncestor = -1
  for(i in filteredDirectPath.indices){
    if(leafIndex.isInSubtreeOf(filteredDirectPath[i].first)){
      idxCommonAncestor = i
      break
    }
  }

  if(idxCommonAncestor == -1) { error("No ancestor of own leaf index found in filtered direct path of committer") }

  val (nodeIdx, resolution) = filteredDirectPath[idxCommonAncestor]
  val updateNode = updatePath.nodes[idxCommonAncestor]

  var pathSecret: Secret? = null
  val excludeNewNodes = excludeNewLeaves.map { it.leafIndex }.toSet()
  var ghostSecretShares: List<GhostShareHolder> = listOf()

  if(newGhostUsers.isNotEmpty() && canUseDefaultShareDistribution(this, fromLeafIdx, excludeNewNodes)){
    decryptSharesUsingDefaultShareDistribution(
      this,
      fromLeafIdx,
      excludeNewNodes,
      groupContext,
      newGhostUsers,
      updateNode,
      nodeIdx,
      resolution
    ).let{
      pathSecret = it.first
      ghostSecretShares = it.second
    }
  }
  else if(newGhostUsers.isNotEmpty()){
    if(canUseHorizontalShareDistribution(this, excludeNewNodes)){
      ghostSecretShares = decryptSharesUsingHorizontalShareDistribution(this, excludeNewNodes, groupContext, newGhostUsers, updatePath.shares)
    }
  }

  if(pathSecret == null){
    pathSecret =
      (resolution - excludeNewLeaves)
        .zip(updateNode.encryptedPathSecret)
        .firstNotNullOf { (node, ciphertext) -> getKeyPair(node)?.let(ciphertext::to) }
        .let { (ciphertext, keyPair) ->
          cipherSuite.decryptWithLabel(
            keyPair,
            "UpdatePathNode",
            groupContext.encoded,
            ciphertext,
          ).bind().asSecret
        }
  }

  return Triple(nodeIdx, pathSecret!!, ghostSecretShares)
}

context(ICipherSuite, Raise<JoinError>)
internal fun RatchetTree.insertPathSecrets(
  ownLeafIdx: LeafIndex,
  senderLeafIdx: LeafIndex,
  pathSecret: Secret,
): RatchetTree {
  return insertPathSecrets(
    filteredDirectPath(senderLeafIdx)
      .map { it.first }
      .find { ownLeafIdx.isInSubtreeOf(it) && senderLeafIdx.isInSubtreeOf(it) }
      ?: error("No ancestor of own leaf index found in filtered direct path of sender"),
    pathSecret,
  )
}
