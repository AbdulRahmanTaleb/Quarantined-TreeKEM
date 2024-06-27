package com.github.traderjoe95.mls.protocol.tree

import arrow.core.Either
import arrow.core.Tuple4
import arrow.core.firstOrNone
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
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.error.UnexpectedError
import com.github.traderjoe95.mls.protocol.group.GhostMember
import com.github.traderjoe95.mls.protocol.group.GhostMemberCommit
import com.github.traderjoe95.mls.protocol.group.GhostShareHolder
import com.github.traderjoe95.mls.protocol.group.GhostShareHolderList
import com.github.traderjoe95.mls.protocol.group.GhostShareHolderList.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.types.crypto.HpkeCiphertext
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import com.github.traderjoe95.mls.protocol.types.tree.GhostShareDistribution

context(Raise<SenderTreeUpdateError>)
internal fun createUpdatePath(
  originalTree: RatchetTree,
  excludeNewLeaves: Set<LeafIndex>,
  groupContext: GroupContext,
  signaturePrivateKey: SignaturePrivateKey,
  newGhostMembers: List<GhostMemberCommit> = listOf(),
  newGhostSecrets: List<Secret> = listOf(),
  minimum_secret_sharing_nb: Int = 3,
): Either<UnexpectedError, Tuple4<RatchetTree, UpdatePath, List<Secret>, List<GhostShareHolder>>> =
  createUpdatePath(
    originalTree,
    originalTree.leafIndex,
    excludeNewLeaves,
    groupContext,
    signaturePrivateKey,
    newGhostMembers,
    newGhostSecrets,
    minimum_secret_sharing_nb)

context(Raise<SenderTreeUpdateError>)
internal fun createUpdatePath(
  originalTree: RatchetTree,
  from: LeafIndex,
  excludeNewLeaves: Set<LeafIndex>,
  groupContext: GroupContext,
  signaturePrivateKey: SignaturePrivateKey,
  newGhostMembers: List<GhostMemberCommit> = listOf(),
  newGhostSecrets: List<Secret> = listOf(),
  minimum_secret_sharing_nb: Int = 3,
): Either<UnexpectedError, Tuple4<RatchetTree, UpdatePath, List<Secret>, List<GhostShareHolder>>> = either {
  with(originalTree.cipherSuite) {
    val oldLeafNode = originalTree.leafNode(from)

    val leafPathSecret = generateSecret(hashLen)
    val leafNodeSecret = deriveSecret(leafPathSecret, "node")
    val leafKp = deriveKeyPair(leafNodeSecret)

    val directPath = originalTree.directPath(from)
    val filteredDirectPath = originalTree.filteredDirectPath(from)

    val onlyGhostsInRes = filteredDirectPath.map{ true }.toMutableList()


    val ghostSecretShares = mutableListOf<List<ShamirSecretSharing.SecretShare>>()
    var horizontalShareDistributionUsed = false
    var nodeIndices =  listOf<NodeIndex>()
    if (newGhostSecrets.isNotEmpty()) {
      // Trying with the default share distribution method
      var nbShares = filteredDirectPath.size + 1
      filteredDirectPath.mapIndexed { idx, (_, res) ->
        res.map {
          if((!it.isLeaf) || (originalTree.leaves[it.leafIndex.value.toInt()]!!.source != LeafNodeSource.Ghost)){
            onlyGhostsInRes[idx] = false
          }
        }
        if(onlyGhostsInRes[idx]){ nbShares-- }
      }

      // If not enough shares can be constructed with the default method,
      // try the horizontal share distribution method
      if(nbShares < minimum_secret_sharing_nb){

        println("Not enough shares with the default share distribution method, trying the horizontal method ...")

        val res = originalTree.public.getLevelWithEnoughNodes(minimum_secret_sharing_nb, from)
          ?: raise(UnexpectedError("NotEnoughNodesForSecretSharing"))

        nbShares = res.first
        nodeIndices = res.second
        println(nodeIndices)

        horizontalShareDistributionUsed = true
      }

      println("nb_shares = " + nbShares)

      newGhostSecrets.forEach {
        ghostSecretShares.add(ShamirSecretSharing.generateShares(it.bytes, nbShares, nbShares))
      }
    }

    val ownGhostSecretShares = ghostSecretShares.mapIndexed{ idx, _ ->
      GhostShareHolder.create(
        newGhostMembers[idx].ghostEncryptionKey,
        newGhostMembers[idx].leafIndex,
        groupContext.epoch + 1u,
        ghostSecretShares[idx][0],
        1u,
      )
    }

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

//    println(filteredDirectPath)
    val updatePathNodes =
      filteredDirectPath.zip(pathSecrets.drop(1)).map { (nodeAndRes, pathSecret) ->
        val (nodeIdx, resolution) = nodeAndRes
        val encryptFor = resolution - excludedNodeIndices

        println(encryptFor)
//        println(updatedTree.leafNode(encryptFor[0]).encryptionKey)

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

    var shareIdx = 1
    val ghostShares: List<GhostShareDistribution>

    if(horizontalShareDistributionUsed){
      ghostShares = nodeIndices.map{
        if(directPath[it.level.toInt()-1].value == it.value){
          GhostShareDistribution(
            updatedTree.node(it).encryptionKey,
            listOf(encryptWithLabel(
              originalTree.node(it).encryptionKey,
              "GhostShareDistribution",
              provisionalGroupCtx.encoded,
              GhostShareHolderList.construct(
                newGhostMembers,
                ghostSecretShares,
                0,
                0u,
              ).encodeUnsafe()
            ).bind())
          )
        }else{
          shareIdx++
          GhostShareDistribution(
            updatedTree.node(it).encryptionKey,
            listOf(encryptWithLabel(
              originalTree.node(it).encryptionKey,
              "GhostShareDistribution",
              provisionalGroupCtx.encoded,
              GhostShareHolderList.construct(
                newGhostMembers,
                ghostSecretShares,
                shareIdx - 1,
                0u,
              ).encodeUnsafe()
            ).bind())
          )
        }
      }
    } else {
      ghostShares = filteredDirectPath.mapIndexed { index, nodeAndRes ->
        val (nodeIdx, resolution) = nodeAndRes
        val encryptFor = resolution - excludedNodeIndices

        GhostShareDistribution(
          updatedTree.parentNode(nodeIdx).encryptionKey,
          when {
            !onlyGhostsInRes[index] -> {
//              println(nodeAndRes)
              shareIdx++
              var holderRank = 0u
              encryptFor.mapNotNull { idx ->
                when {
                  !(idx.isLeaf && originalTree.leaves[idx.leafIndex.value.toInt()]!!.source == LeafNodeSource.Ghost) -> {
                    holderRank++
                    encryptWithLabel(
                      originalTree.node(idx).encryptionKey,
                      "GhostShareDistribution",
                      provisionalGroupCtx.encoded,
                      GhostShareHolderList.construct(
                        newGhostMembers,
                        ghostSecretShares,
                        shareIdx - 1,
                        holderRank,
                      ).encodeUnsafe()
                    ).bind()
                  }

                  else -> null
                }
              }
            }

            else -> listOf()
          },
        )
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

context(Raise<RecipientTreeUpdateError>)
internal fun applyUpdatePath(
  originalTree: RatchetTree,
  groupContext: GroupContext,
  fromLeafIndex: LeafIndex,
  updatePath: UpdatePath,
  excludeNewLeaves: Set<LeafIndex>,
  newGhostUsers: List<GhostMemberCommit> = listOf(),
  ghostMembers: List<LeafIndex> = listOf(),
): Triple<RatchetTree, Secret, GhostShareHolderList> {
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
      ghostMembers,
    )

  updatedTree = updatedTree.insertPathSecrets(commonAncestor, pathSecret)

  return Triple(updatedTree, updatedTree.private.commitSecret, ghostSecretShares)
}

context(Raise<RecipientTreeUpdateError>)
internal fun RatchetTree.applyUpdatePathExternalJoin(
  groupContext: GroupContext,
  updatePath: UpdatePath,
  excludeNewLeaves: Set<LeafIndex>,
): Triple<RatchetTree, Secret, GhostShareHolderList> =
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

context(Raise<HpkeDecryptError>)
internal fun RatchetTree.extractCommonPathSecret(
  fromLeafIdx: LeafIndex,
  updatePath: UpdatePath,
  groupContext: GroupContext,
  excludeNewLeaves: Set<NodeIndex>,
  newGhostUsers: List<GhostMemberCommit> = listOf(),
  ghostMembers: List<LeafIndex> = listOf(),
): Triple<NodeIndex, Secret, GhostShareHolderList> {
  val filteredDirectPath = filteredDirectPath(fromLeafIdx)

  var idxCommonAncestor = -1
  for(i in 0..<filteredDirectPath.size){
    if(leafIndex.isInSubtreeOf(filteredDirectPath[i].first)){
      idxCommonAncestor = i
      break
    }
  }

  if(idxCommonAncestor == -1) { error("No ancestor of own leaf index found in filtered direct path of committer") }

  val (nodeIdx, resolution) = filteredDirectPath[idxCommonAncestor]
  val updateNode = updatePath.nodes[idxCommonAncestor]

  val pathSecret =
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

  var ghostSecretShares = GhostShareHolderList(listOf())
  if(newGhostUsers.isNotEmpty()) {
    if ((updatePath.nodes.size == updatePath.shares.size) && (updatePath.nodes[idxCommonAncestor].encryptionKey.eq(
        updatePath.shares[idxCommonAncestor].encryptionKey
      ))
    ) {
      val share = updatePath.shares[idxCommonAncestor]
      val excludeGhosts = ghostMembers.map { it.nodeIndex }
      val secrets = (resolution - excludeNewLeaves - excludeGhosts.toSet())
        .zip(share.encryptedGhostSecrets)
        .firstNotNullOfOrNull { (node, ciphertext) -> getKeyPair(node)?.let(ciphertext::to) }
        ?.let { (ciphertext, keyPair) ->
          cipherSuite.decryptWithLabel(
            keyPair,
            "GhostShareDistribution",
            groupContext.encoded,
            ciphertext,
          ).bind()
        }

      if (secrets != null) {
        ghostSecretShares = GhostShareHolderList.decodeUnsafe(secrets)
      }
    } else {
      println("Ghost shares were not encrypted using the default share method, trying to find a level node from the horizontal setting ...")
      val res = public.getLevelWithEnoughNodes(3, fromLeafIdx)
      if (res != null) {
        val nodeIndices = res.second
        val ownDirectPath = directPath(leafIndex)
        nodeIndices.firstOrNull { it.value == ownDirectPath[it.level.toInt() - 1].value }
          ?.let { nodeIdxShare ->
            updatePath.shares.firstOrNull { it.encryptionKey.eq(public.node(nodeIdxShare).encryptionKey) }
              ?.let { ghostShareDistribution ->
                ghostSecretShares = GhostShareHolderList.decodeUnsafe(
                  cipherSuite.decryptWithLabel(
                    getKeyPair(nodeIdxShare)!!,
                    "GhostShareDistribution",
                    groupContext.encoded,
                    ghostShareDistribution.encryptedGhostSecrets[0],
                  ).bind()
                )
                println("Found with Horizontal Method")
                val rank = public.getRankFromLevel(leafIndex, nodeIdxShare)
                ghostSecretShares = GhostShareHolderList(ghostSecretShares.ghostShareHolders.map {
                  GhostShareHolder.changeRank(it, rank)
                })
                println(ghostSecretShares)
              }
          }
      }
    }
  }

  return Triple(nodeIdx, pathSecret, ghostSecretShares)
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
