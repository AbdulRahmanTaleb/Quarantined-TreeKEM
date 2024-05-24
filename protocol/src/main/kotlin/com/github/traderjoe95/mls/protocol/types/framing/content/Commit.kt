package com.github.traderjoe95.mls.protocol.types.framing.content

import arrow.core.None
import arrow.core.Option
import arrow.core.some
import com.github.traderjoe95.mls.codec.Encodable
import com.github.traderjoe95.mls.codec.Struct2
import com.github.traderjoe95.mls.codec.type.DataType
import com.github.traderjoe95.mls.codec.type.EnumT
import com.github.traderjoe95.mls.codec.type.ProtocolEnum
import com.github.traderjoe95.mls.codec.type.V
import com.github.traderjoe95.mls.codec.type.enum
import com.github.traderjoe95.mls.codec.type.get
import com.github.traderjoe95.mls.codec.type.optional
import com.github.traderjoe95.mls.codec.type.struct.Struct3T
import com.github.traderjoe95.mls.codec.type.struct.Struct4T
import com.github.traderjoe95.mls.codec.type.struct.Struct5T
import com.github.traderjoe95.mls.codec.type.struct.Struct6T
import com.github.traderjoe95.mls.codec.type.struct.lift
import com.github.traderjoe95.mls.codec.type.struct.member.then
import com.github.traderjoe95.mls.codec.type.struct.struct
import com.github.traderjoe95.mls.codec.util.throwAnyError
import com.github.traderjoe95.mls.protocol.group.GhostMember
import com.github.traderjoe95.mls.protocol.group.GhostMemberCommit
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.types.crypto.HpkeCiphertext
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import com.github.traderjoe95.mls.protocol.types.framing.enums.ContentType
import com.github.traderjoe95.mls.protocol.types.tree.UpdatePath
import com.github.traderjoe95.mls.protocol.util.zipWithIndex

data class Commit(
  val proposals: List<ProposalOrRef>,
  val updatePath: Option<UpdatePath>,
  val ghostUsers: List<GhostMemberCommit>,
  val ghostReconnectKeys: List<HpkePublicKey>,
  val encryptedGroupInfoForGhostReconnect: List<HpkeCiphertext>,
) : Content.Handshake<Commit>, Struct5T.Shape<List<ProposalOrRef>, Option<UpdatePath>, List<GhostMemberCommit>, List<HpkePublicKey>, List<HpkeCiphertext>> {

  constructor(proposals: List<ProposalOrRef>, updatePath: UpdatePath, ghostUsers: List<GhostMemberCommit>, ghostReconnectKeys: List<HpkePublicKey>, encryptedGroupInfoForGhostReconnect: List<HpkeCiphertext>) : this(proposals, updatePath.some(), ghostUsers, ghostReconnectKeys ,encryptedGroupInfoForGhostReconnect)
  constructor(proposals: List<ProposalOrRef>, updatePath: UpdatePath) : this(proposals, updatePath.some(), emptyList(), emptyList(), emptyList())

  constructor(proposals: List<ProposalOrRef>) : this(proposals, None, emptyList(), emptyList(), emptyList())

  override val contentType: ContentType.Commit = ContentType.Commit

  companion object : Encodable<Commit> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<Commit> =
      struct("Commit") {
        it.field("proposals", ProposalOrRef.T[V])
          .field("update_path", optional[UpdatePath.T])
          .field("ghost_users", GhostMemberCommit.T[V])
          .field("ghost_reconnect_keys", HpkePublicKey.T[V])
          .field("encrypted_group_info_for_ghost_reconnect", HpkeCiphertext.T[V])
      }.lift(::Commit)

    val empty: Commit
      get() = Commit(listOf(), None, listOf(), listOf(), listOf())
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Commit

    if (proposals.size != other.proposals.size) return false
    if (proposals.zipWithIndex().any { (proposalOrRef, idx) -> proposalOrRef neq other.proposals[idx] }) return false
    if (updatePath != other.updatePath) return false

    if (ghostUsers.size != other.ghostUsers.size) return false
    if (ghostUsers.zipWithIndex().any { (leafIdx, idx) -> !leafIdx.equals(other.ghostUsers[idx].leafIndex) }) return false

    if (ghostReconnectKeys.size != other.ghostReconnectKeys.size) return false
    if (ghostReconnectKeys.zipWithIndex().any { (key, idx) -> !key.eq(other.ghostReconnectKeys[idx]) }) return false

    if (encryptedGroupInfoForGhostReconnect.size != other.encryptedGroupInfoForGhostReconnect.size) return false
    if (encryptedGroupInfoForGhostReconnect.zipWithIndex().any { (ciphertext, idx) -> !ciphertext.ciphertext.eq(other.encryptedGroupInfoForGhostReconnect[idx].ciphertext) }) return false


    return true
  }

  override fun hashCode(): Int {
    var result = proposals.fold(1) { hc, el -> hc * 31 + el.hashCode }
    result = 31 * result + updatePath.hashCode()
    result = 31 * result + contentType.hashCode()
    result = ghostUsers.fold(result) { hc, li -> hc * 31 + li.hashCode()}
    result = ghostReconnectKeys.fold(result) { hc, li -> hc * 31 + li.hashCode()}
    result = encryptedGroupInfoForGhostReconnect.fold(result) { hc, li -> hc * 31 + li.hashCode()}

    return result
  }
}

sealed interface ProposalOrRef {
  val proposalOrRef: ProposalOrRefType

  infix fun eq(other: ProposalOrRef): Boolean =
    when {
      this is Proposal && other is Proposal -> this == other
      this is Proposal.Ref && other is Proposal.Ref -> this eq other
      else -> false
    }

  infix fun neq(other: ProposalOrRef): Boolean = !(this eq other)

  val hashCode: Int
    get() = hashCode()

  companion object : Encodable<ProposalOrRef> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<ProposalOrRef> =
      struct("ProposalOrRef") {
        it.field("type", ProposalOrRefType.T)
          .select<ProposalOrRef, _>(ProposalOrRefType.T, "type") {
            case(ProposalOrRefType.Proposal).then(Proposal.T)
              .case(ProposalOrRefType.Reference).then(Proposal.Ref.T)
          }
      }.lift({ _, p -> p }, { Struct2(it.proposalOrRef, it) })
  }
}

enum class ProposalOrRefType(ord: UInt, override val isValid: Boolean = true) : ProtocolEnum<ProposalOrRefType> {
  @Deprecated("This reserved value isn't used by the protocol for now", level = DeprecationLevel.ERROR)
  Reserved(0U, false),

  Proposal(1U),
  Reference(2U),
  ;

  override val ord: UIntRange = ord..ord

  override fun toString(): String = "$name[${ord.first}]"

  companion object {
    val T: EnumT<ProposalOrRefType> = throwAnyError { enum(upperBound = 0xFFU) }
  }
}
