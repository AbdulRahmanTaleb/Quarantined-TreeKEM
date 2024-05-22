package com.github.traderjoe95.mls.protocol.message

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import com.github.traderjoe95.mls.codec.Encodable
import com.github.traderjoe95.mls.codec.type.DataType
import com.github.traderjoe95.mls.codec.type.struct.Struct1T
import com.github.traderjoe95.mls.codec.type.struct.Struct2T
import com.github.traderjoe95.mls.codec.type.struct.Struct3T
import com.github.traderjoe95.mls.codec.type.struct.Struct4T
import com.github.traderjoe95.mls.codec.type.struct.Struct5T
import com.github.traderjoe95.mls.codec.type.struct.lift
import com.github.traderjoe95.mls.codec.type.struct.struct
import com.github.traderjoe95.mls.codec.type.uint32
import com.github.traderjoe95.mls.codec.type.uint64
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.error.CreateSignatureError
import com.github.traderjoe95.mls.protocol.error.KeyPackageMismatchError
import com.github.traderjoe95.mls.protocol.error.VerifySignatureError
import com.github.traderjoe95.mls.protocol.group.GroupContext
import com.github.traderjoe95.mls.protocol.message.QuarantineEnd.Tbs.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.types.Credential
import com.github.traderjoe95.mls.protocol.types.Extension
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.GroupInfoExtension
import com.github.traderjoe95.mls.protocol.types.KeyPackageExtension
import com.github.traderjoe95.mls.protocol.types.KeyPackageExtensions
import com.github.traderjoe95.mls.protocol.types.LeafNodeExtensions
import com.github.traderjoe95.mls.protocol.types.RefinedBytes
import com.github.traderjoe95.mls.protocol.types.crypto.HpkeCiphertext
import com.github.traderjoe95.mls.protocol.types.crypto.HpkeKeyPair
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePrivateKey
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import com.github.traderjoe95.mls.protocol.types.crypto.Mac
import com.github.traderjoe95.mls.protocol.types.crypto.Signature
import com.github.traderjoe95.mls.protocol.types.crypto.SignatureKeyPair
import com.github.traderjoe95.mls.protocol.types.crypto.SignaturePrivateKey
import com.github.traderjoe95.mls.protocol.types.extensionList
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion
import com.github.traderjoe95.mls.protocol.types.framing.enums.WireFormat
import com.github.traderjoe95.mls.protocol.types.tree.KeyPackageLeafNode
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode
import com.github.traderjoe95.mls.protocol.types.tree.UpdateLeafNode
import com.github.traderjoe95.mls.protocol.types.tree.leaf.Capabilities
import com.github.traderjoe95.mls.protocol.types.tree.leaf.LeafNodeSource
import com.github.traderjoe95.mls.protocol.types.tree.leaf.Lifetime
import com.github.traderjoe95.mls.protocol.util.plus
import java.time.Instant
import javax.crypto.Cipher
import kotlin.time.Duration

data class ShareRecoveryMessage(
  val groupId: GroupId,
  val leafIndex: LeafIndex,
  val shareHolderRank: UInt,
  val encryptionKey: HpkePublicKey,
  val encryptedShare: HpkeCiphertext,
) : Message, Struct5T.Shape<GroupId, LeafIndex, UInt, HpkePublicKey, HpkeCiphertext> {
  override val wireFormat: WireFormat = WireFormat.MlsShareRecoveryMessage

  override val encoded: ByteArray by lazy { encodeUnsafe() }

  companion object : Encodable<ShareRecoveryMessage> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<ShareRecoveryMessage> =
      struct("ShareRecoveryMessage") {
        it.field("group_id", GroupId.T)
          .field("leaf_index", LeafIndex.T)
          .field("share_holder_rank", uint32.asUInt)
          .field("encryption_key", HpkePublicKey.T)
          .field("encrypted_share", HpkeCiphertext.T)
      }.lift(::ShareRecoveryMessage)

    fun create(
      groupId: GroupId,
      leafIndex: LeafIndex,
      shareHolderRank: UInt,
      encryptionKey: HpkePublicKey,
      encryptedShare: HpkeCiphertext
    ): ShareRecoveryMessage =
        ShareRecoveryMessage(
          groupId,
          leafIndex,
          shareHolderRank,
          encryptionKey,
          encryptedShare,
          )
  }

}
