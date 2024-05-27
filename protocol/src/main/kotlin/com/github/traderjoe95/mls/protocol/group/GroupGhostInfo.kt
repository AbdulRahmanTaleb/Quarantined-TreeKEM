package com.github.traderjoe95.mls.protocol.group

import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import arrow.core.raise.Raise
import com.github.traderjoe95.mls.codec.Encodable
import com.github.traderjoe95.mls.codec.type.DataType
import com.github.traderjoe95.mls.codec.type.V
import com.github.traderjoe95.mls.codec.type.get
import com.github.traderjoe95.mls.codec.type.opaque
import com.github.traderjoe95.mls.codec.type.struct.Struct1T
import com.github.traderjoe95.mls.codec.type.struct.Struct2T
import com.github.traderjoe95.mls.codec.type.struct.Struct3T
import com.github.traderjoe95.mls.codec.type.struct.Struct5T
import com.github.traderjoe95.mls.codec.type.struct.lift
import com.github.traderjoe95.mls.codec.type.struct.struct
import com.github.traderjoe95.mls.codec.type.uint32
import com.github.traderjoe95.mls.codec.type.uint64
import com.github.traderjoe95.mls.protocol.error.GroupGhostInfoError
import com.github.traderjoe95.mls.protocol.tree.RatchetTree
import com.github.traderjoe95.mls.protocol.types.tree.leaf.LeafNodeSource


data class GroupGhostInfo(
  private val currentGhostMembers: MutableList<GhostMember> = mutableListOf(),
  private val ghostMembersShares: MutableList<GhostShareHolder> = mutableListOf(),
  ) {

  fun getCurrentGhosts(): List<LeafIndex> = currentGhostMembers.map { it.leafIndex }


  fun containsGhost(leafIndex: LeafIndex): Boolean{
    currentGhostMembers.firstOrNull {
      it.leafIndex.eq(leafIndex)
    }.also {
      return it != null
    }
  }

  context(Raise<GroupGhostInfoError>)
  fun addNewGhostMember(leafIndex: LeafIndex, epoch: ULong, encryptionKey: HpkePublicKey): GhostMemberCommit{
    currentGhostMembers.firstOrNull{
      it.leafIndex == leafIndex
    }.also{
      if(it == null){
        val g = GhostMember(leafIndex)
        g.addNewEncryptionKey(epoch, encryptionKey)
        currentGhostMembers.add(g)
        return GhostMemberCommit(leafIndex, encryptionKey, epoch)
      }
      else{
        raise(GroupGhostInfoError.MemberIsAlreadyGhost(leafIndex))
      }
    }
  }

  context(Raise<GroupGhostInfoError>)
  fun addNewGhostKey(leafIndex: LeafIndex, epoch: ULong, encryptionKey: HpkePublicKey): GhostMemberCommit{
    currentGhostMembers.firstOrNull{
      it.leafIndex == leafIndex
    }.also{
      if(it == null){
        raise(GroupGhostInfoError.GhostNotFound(leafIndex))
      }
      else{
        it.addNewEncryptionKey(epoch, encryptionKey)
        return GhostMemberCommit(leafIndex, encryptionKey, epoch)
      }
    }
  }

  context(Raise<GroupGhostInfoError>)
  fun removeGhostMember(leafIndex: LeafIndex){
    currentGhostMembers.removeIf {
      it.leafIndex == leafIndex
    }.also{
      if(!it){
        raise(GroupGhostInfoError.GhostNotFound(leafIndex))
      }
    }
  }

  context(Raise<GroupGhostInfoError>)
  private fun removeGhostMemberWithShares(leafIndex: LeafIndex){
    currentGhostMembers.removeIf {
      it.leafIndex == leafIndex
    }.also{
      if(!it){
        raise(GroupGhostInfoError.GhostNotFound(leafIndex))
      }
    }
    ghostMembersShares.removeIf {
      it.leafIndex == leafIndex
    }.also{
      if(!it){
        raise(GroupGhostInfoError.GhostNotFound(leafIndex))
      }
    }
  }

  context(Raise<GroupGhostInfoError>)
  fun removeDeadGhosts(tree: RatchetTree, epoch: ULong,  QUARANTINE_DELAY: ULong): List<LeafIndex>{
    val res =  currentGhostMembers.filter{ ghost ->
      (epoch + 1u - tree.leaves[ghost.leafIndex.value.toInt()]!!.equar) >= QUARANTINE_DELAY
    }.map{ it.leafIndex }

    res.forEach { removeGhostMemberWithShares(it) }

    return res
  }

  context(Raise<GroupGhostInfoError>)
  fun lastGhostKeyUpdate(leafIndex: LeafIndex): ULong{
    currentGhostMembers.firstOrNull{
      it.leafIndex.eq(leafIndex)
    }.also {
      if(it == null){
        raise(GroupGhostInfoError.GhostNotFound(leafIndex))
      }

      return it.ghostEncryptionKeys.fold(0u) { max, epochAndKey -> if(epochAndKey.first > max) epochAndKey.first else max }
    }
  }

  context(Raise<GroupGhostInfoError>)
  fun addNewGhostKeyShares(ghostShareHolders: GhostShareHolderList){
   addNewGhostKeyShares(ghostShareHolders.ghostShareHolders)
  }

  context(Raise<GroupGhostInfoError>)
  fun addNewGhostKeyShares(ghostShareHolders: List<GhostShareHolder>){
    ghostShareHolders.forEach {
      addNewGhostKeyShare(it)
    }
  }

  context(Raise<GroupGhostInfoError>)
  private fun addNewGhostKeyShare(shareHolder: GhostShareHolder){
    currentGhostMembers.firstOrNull{
      it.leafIndex == shareHolder.leafIndex
    }.also{ ghostMember ->
      if(ghostMember == null){
        raise(GroupGhostInfoError.GhostNotFound(shareHolder.leafIndex))
      }

      ghostMember.ghostEncryptionKeys.firstOrNull { (epochG, keyG) ->
        epochG == shareHolder.epoch || keyG.eq(shareHolder.ghostEncryptionKey)
      }.also{ keyAndEpoch ->
        if(keyAndEpoch == null){
          raise(GroupGhostInfoError.KeyNotFoundForEpoch(shareHolder.epoch))
        }
        ghostMembersShares.firstOrNull { ghostShareHolder ->
          (ghostShareHolder.leafIndex == shareHolder.leafIndex) && (ghostShareHolder.epoch == shareHolder.epoch || ghostShareHolder.ghostEncryptionKey.eq(shareHolder.ghostEncryptionKey))
        }.also{
          if(it != null){
            raise(GroupGhostInfoError.GhostShareAlreadySavedForThisKey(shareHolder.ghostEncryptionKey))
          }
          ghostMembersShares.add(GhostShareHolder.copy(shareHolder))
        }
      }
    }
  }

  context(Raise<GroupGhostInfoError>)
  fun hasKeyShares(leafIndex: LeafIndex,
                   rank: UInt): Boolean {
    ghostMembersShares.firstOrNull{
      it.leafIndex == leafIndex && it.ghostShareHolderRank == rank
    }.also{
      return it != null
    }
  }

  context(Raise<GroupGhostInfoError>)
  fun getKeyShares(leafIndex: LeafIndex,
                   rank: UInt): GhostShareHolderList {
    val res = GhostShareHolderList(ghostMembersShares.filter {
      it.leafIndex == leafIndex && it.ghostShareHolderRank == rank
    })

    ghostMembersShares.removeAll{
      it.leafIndex == leafIndex && it.ghostShareHolderRank == rank
    }

    return res
  }

}


data class GhostMember(
  val leafIndex: LeafIndex,
  val ghostEncryptionKeys: MutableList<Pair<ULong, HpkePublicKey>> = mutableListOf(),
): Struct2T.Shape<LeafIndex, MutableList<Pair<ULong, HpkePublicKey>>> {

  context(Raise<GroupGhostInfoError>)
  fun addNewEncryptionKey(epoch: ULong, encryptionKey: HpkePublicKey){
    ghostEncryptionKeys.forEach { (epochG, _) ->
      if(epoch == epochG){
        raise(GroupGhostInfoError.GhostKeyAlreadySavedForThisEpoch)
      }
      if(epoch < epochG){
        raise(GroupGhostInfoError.InvalidEpoch(epoch))
      }
    }
    ghostEncryptionKeys.add(Pair(epoch, encryptionKey))
  }

  context(Raise<GroupGhostInfoError>)
  fun findEncryptionKey(epoch: ULong): HpkePublicKey{
    ghostEncryptionKeys.firstOrNull{ (epochG, _) ->
      (epochG == epoch)
    }.also {
      if(it == null){
        raise(GroupGhostInfoError.KeyNotFoundForEpoch(epoch))
      }
      return it.second
    }
  }
}


data class GhostShareHolder(
  val ghostEncryptionKey: HpkePublicKey,
  val leafIndex: LeafIndex,
  val epoch: ULong,
  val ghostShare: ShamirSecretSharing.SecretShare,
  val ghostShareHolderRank: UInt,
  ) : Struct5T.Shape<HpkePublicKey, LeafIndex, ULong, ShamirSecretSharing.SecretShare, UInt> {

  companion object : Encodable<GhostShareHolder> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<GhostShareHolder> =
      struct("GhostShareHolder") {
        it.field("ghost_encryption_key", HpkePublicKey.T)
          .field("leaf_Index", LeafIndex.T)
          .field("epoch", uint64.asULong)
          .field("ghost_share", ShamirSecretSharing.SecretShare.T)
          .field("ghost_share_holder_rank", uint32.asUInt)
      }.lift(::GhostShareHolder)

    fun create(
      ghostEncryptionKey: HpkePublicKey,
      leafIndex: LeafIndex,
      epoch: ULong,
      ghostShare: ShamirSecretSharing.SecretShare,
      ghostShareHolderRank: UInt
    ): GhostShareHolder =

      GhostShareHolder(
        ghostEncryptionKey,
        leafIndex,
        epoch,
        ghostShare,
        ghostShareHolderRank
      )

    fun copy(
      shareHolder: GhostShareHolder
    ): GhostShareHolder =

      GhostShareHolder(
        shareHolder.ghostEncryptionKey,
        shareHolder.leafIndex,
        shareHolder.epoch,
        shareHolder.ghostShare,
        shareHolder.ghostShareHolderRank
      )

  }

  }


data class GhostShareHolderList(
  val ghostShareHolders: List<GhostShareHolder>
) : Struct1T.Shape<List<GhostShareHolder>> {

  companion object : Encodable<GhostShareHolderList> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<GhostShareHolderList> =
      struct("GhostShareHolderList") {
        it.field("ghost_share_holders", GhostShareHolder.T[V])
      }.lift(::GhostShareHolderList)


    fun construct(
      ghostMembers: List<GhostMemberCommit>,
      ghostSecretShares: List<List<ShamirSecretSharing.SecretShare>>,
      shareIdx: Int,
      holderRank: UInt
      ): GhostShareHolderList{

      return GhostShareHolderList(ghostMembers.mapIndexed{ idx, ghost ->
        GhostShareHolder(ghost.ghostEncryptionKey, ghost.leafIndex, ghost.ghostEncryptionKeyEpoch, ghostSecretShares[idx][shareIdx], holderRank)
      })

    }
  }

}

data class GhostMemberCommit(
  val leafIndex: LeafIndex,
  val ghostEncryptionKey: HpkePublicKey,
  val ghostEncryptionKeyEpoch: ULong,
): Struct3T.Shape<LeafIndex, HpkePublicKey, ULong> {

  override fun hashCode(): Int {
    var result = leafIndex.hashCode()
    result = 31 * result + ghostEncryptionKey.hashCode()
    result = 31 * result + ghostEncryptionKeyEpoch.hashCode()
    return result
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as GhostMemberCommit

    if (leafIndex != other.leafIndex) return false
    if (ghostEncryptionKey.eq(other.ghostEncryptionKey)) return false
    if (ghostEncryptionKeyEpoch != other.ghostEncryptionKeyEpoch) return false

    return true
  }

  companion object : Encodable<GhostMemberCommit> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<GhostMemberCommit> =
      struct("GhostShareHolderList") {
        it.field("leaf_index", LeafIndex.T)
          .field("ghost_encryption_key", HpkePublicKey.T)
          .field("ghost_encryption_key_epoch", uint64.asULong)
      }.lift(::GhostMemberCommit)
  }


}
