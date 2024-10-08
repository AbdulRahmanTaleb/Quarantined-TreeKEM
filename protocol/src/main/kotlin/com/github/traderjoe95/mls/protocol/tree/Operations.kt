@file:Suppress("kotlin:S1481")

package com.github.traderjoe95.mls.protocol.tree

import arrow.core.raise.Raise
import arrow.core.raise.nullable
import com.github.traderjoe95.mls.protocol.crypto.ICipherSuite
import com.github.traderjoe95.mls.protocol.error.IsSameClientError
import com.github.traderjoe95.mls.protocol.error.VerifySignatureError
import com.github.traderjoe95.mls.protocol.group.GroupContext
import com.github.traderjoe95.mls.protocol.message.KeyPackage
import com.github.traderjoe95.mls.protocol.service.AuthenticationService
import com.github.traderjoe95.mls.protocol.types.ExternalSenders
import com.github.traderjoe95.mls.protocol.types.crypto.SignaturePublicKey
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import com.github.traderjoe95.mls.protocol.types.framing.content.Commit
import com.github.traderjoe95.mls.protocol.types.framing.content.FramedContent
import com.github.traderjoe95.mls.protocol.types.framing.enums.SenderType
import com.github.traderjoe95.mls.protocol.types.tree.KeyPackageLeafNode
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode
import com.github.traderjoe95.mls.protocol.types.tree.hash.ParentHashInput
import com.github.traderjoe95.mls.protocol.types.tree.hash.ParentHashInput.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.types.tree.hash.TreeHashInput
import com.github.traderjoe95.mls.protocol.types.tree.hash.TreeHashInput.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.types.tree.leaf.ParentHash
import com.github.traderjoe95.mls.protocol.types.tree.leaf.ParentHash.Companion.asParentHash

val RatchetTree.treeHash: ByteArray
  get() = treeHash(root, cipherSuite)

fun RatchetTreeOps.treeHash(cipherSuite: ICipherSuite): ByteArray = treeHash(root, cipherSuite)

fun RatchetTree.treeHash(subtreeRoot: TreeIndex): ByteArray = treeHash(subtreeRoot, cipherSuite)

fun RatchetTreeOps.treeHash(
  subtreeRoot: TreeIndex,
  cipherSuite: ICipherSuite,
): ByteArray =
  cipherSuite.hash(
    if (subtreeRoot.isLeaf) {
      TreeHashInput.forLeaf(subtreeRoot.leafIndex, this[subtreeRoot]?.asLeaf).encodeUnsafe()
    } else {
      val subtreeRootIdx = subtreeRoot.nodeIndex
      TreeHashInput.forParent(
        this@treeHash[subtreeRoot]?.asParent,
        treeHash(subtreeRootIdx.leftChild, cipherSuite),
        treeHash(subtreeRootIdx.rightChild, cipherSuite),
      ).encodeUnsafe()
    },
  )

fun RatchetTreeOps.parentHash(
  cipherSuite: ICipherSuite,
  p: NodeIndex,
  l: TreeIndex,
): ParentHash =
  if (l.nodeIndex == root) {
    ParentHash.empty
  } else {
    val pNode = parentNode(p)

    val s =
      if (l < p) {
        p.rightChild
      } else {
        p.leftChild
      }

    cipherSuite.hash(
      ParentHashInput(
        pNode.encryptionKey,
        if (p == root) ParentHash.empty else pNode.parentHash,
        removeLeaves(pNode.unmergedLeaves.toSet()).treeHash(s, cipherSuite),
      ).encodeUnsafe(),
    ).asParentHash
  }

private fun RatchetTreeOps.removeLeaves(leaves: Set<LeafIndex>): RatchetTreeOps =
  when (this) {
    is RatchetTree -> removeLeaves(leaves)
    is PublicRatchetTree -> removeLeaves(leaves)
  }

//context(Raise<IsSameClientError>)
//fun RatchetTreeOps.findEquivalentLeaf(keyPackage: KeyPackage.Private): LeafIndex? =
//  findEquivalentLeaf(keyPackage.public)
//
//context(Raise<IsSameClientError>)
//fun RatchetTreeOps.findEquivalentLeaf(keyPackage: KeyPackage): LeafIndex? =
//  findLeaf { this == keyPackage.leafNode }?.second

context(Raise<IsSameClientError>)
fun RatchetTreeOps.findEquivalentLeaf(leaf: LeafNode<*>): LeafIndex? =
  findLeaf { this == leaf }?.second

context(AuthenticationService<Identity>, Raise<IsSameClientError>)
suspend fun <Identity : Any> RatchetTreeOps.findEquivalentLeaf(leafNode: LeafNode<*>): LeafIndex? =
  leaves.zipWithLeafIndex().find { (n, _) ->
    n?.credential?.let { cred ->
      isSameClient(cred, leafNode.credential).bind()
    } ?: false
  }?.second

inline fun RatchetTreeOps.findLeaf(predicate: LeafNode<*>.() -> Boolean): Pair<LeafNode<*>, LeafIndex>? =
  leaves.zipWithLeafIndex()
    .mapNotNull { (maybeNode, idx) ->
      nullable { maybeNode.bind() to idx }
    }
    .find { (n, _) -> n.predicate() }

val RatchetTreeOps.nonBlankParentNodeIndices: List<NodeIndex>
  get() = parentNodeIndices.filterNot { it.isBlank }

val RatchetTreeOps.nonBlankLeafNodeIndices: List<NodeIndex>
  get() = leafNodeIndices.filterNot { it.isBlank }

val RatchetTreeOps.nonBlankLeafIndices: List<LeafIndex>
  get() = leafNodeIndices.filterNot { it.isBlank }.map { it.leafIndex }

val RatchetTreeOps.nonBlankNodeIndices: List<NodeIndex>
  get() = nonBlankParentNodeIndices + nonBlankLeafNodeIndices

interface SignaturePublicKeyLookup {
  context(Raise<VerifySignatureError.SignaturePublicKeyKeyNotFound>)
  fun getSignaturePublicKey(
    groupContext: GroupContext,
    framedContent: FramedContent<*>,
  ): SignaturePublicKey

  companion object {
    // For testing purposes
    internal fun only(signaturePublicKey: SignaturePublicKey): SignaturePublicKeyLookup =
      object : SignaturePublicKeyLookup {
        context(Raise<VerifySignatureError.SignaturePublicKeyKeyNotFound>)
        override fun getSignaturePublicKey(
          groupContext: GroupContext,
          framedContent: FramedContent<*>,
        ): SignaturePublicKey = signaturePublicKey
      }
  }
}

context(Raise<VerifySignatureError.SignaturePublicKeyKeyNotFound>)
fun findSignaturePublicKey(
  framedContent: FramedContent<*>,
  groupContext: GroupContext,
  tree: RatchetTreeOps,
): SignaturePublicKey =
  when (framedContent.sender.type) {
    SenderType.Member ->
      tree.leafNodeOrNull(framedContent.sender.index!!)
        ?.signaturePublicKey

    SenderType.External ->
      groupContext.extension<ExternalSenders>()
        ?.externalSenders
        ?.getOrNull(framedContent.sender.index!!.value.toInt())
        ?.signaturePublicKey

    SenderType.NewMemberCommit ->
      (framedContent.content as? Commit)
        ?.updatePath?.getOrNull()
        ?.leafNode
        ?.signaturePublicKey

    SenderType.NewMemberProposal ->
      (framedContent.content as? Add)
        ?.keyPackage
        ?.leafNode
        ?.signaturePublicKey

    else -> error("Unreachable")
  } ?: raise(VerifySignatureError.SignaturePublicKeyKeyNotFound(framedContent.sender))
