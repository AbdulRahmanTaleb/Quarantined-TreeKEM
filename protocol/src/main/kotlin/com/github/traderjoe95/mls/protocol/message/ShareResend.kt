package com.github.traderjoe95.mls.protocol.message

import arrow.core.Either
import arrow.core.raise.either
import com.github.traderjoe95.mls.codec.Encodable
import com.github.traderjoe95.mls.codec.type.DataType
import com.github.traderjoe95.mls.codec.type.struct.Struct4T
import com.github.traderjoe95.mls.codec.type.struct.Struct5T
import com.github.traderjoe95.mls.codec.type.struct.lift
import com.github.traderjoe95.mls.codec.type.struct.struct
import com.github.traderjoe95.mls.codec.type.uint32
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.error.CreateSignatureError
import com.github.traderjoe95.mls.protocol.error.VerifySignatureError
import com.github.traderjoe95.mls.protocol.message.ShareResend.Tbs.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.crypto.Signature
import com.github.traderjoe95.mls.protocol.types.crypto.SignaturePrivateKey
import com.github.traderjoe95.mls.protocol.types.crypto.SignaturePublicKey
import com.github.traderjoe95.mls.protocol.types.framing.enums.WireFormat
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode
import com.github.traderjoe95.mls.protocol.types.tree.UpdateLeafNode
import com.github.traderjoe95.mls.protocol.types.tree.leaf.LeafNodeSource

data class ShareResend(
  val groupId: GroupId,
  val cipherSuite: CipherSuite,
  val leafIndex: LeafIndex,
  val requiredShareHolderRank: UInt,
  val signature: Signature,
) : Message,
  Struct5T.Shape<GroupId, CipherSuite, LeafIndex, UInt, Signature> {
  override val wireFormat: WireFormat = WireFormat.MlsShareResend

  override val encoded: ByteArray by lazy { encodeUnsafe() }


  fun verifySignature(signaturePublicKey: SignaturePublicKey): Either<VerifySignatureError, ShareResend> =
    either {
      this@ShareResend.apply {
        cipherSuite.verifyWithLabel(
          signaturePublicKey,
          "ShareResendTBS",
          Tbs(groupId, cipherSuite, leafIndex, requiredShareHolderRank).encodeUnsafe(),
          signature,
        )
      }
    }

  companion object : Encodable<ShareResend> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<ShareResend> =
      struct("ShareResend") {
        it.field("group_id", GroupId.T)
          .field("cipher_suite", CipherSuite.T)
          .field("leaf_index", LeafIndex.T)
          .field("required_share_holder_rank", uint32.asUInt)
          .field("signature", Signature.T)
      }.lift(::ShareResend)

    fun create(
      groupId: GroupId,
      cipherSuite: CipherSuite,
      leafIndex: LeafIndex,
      requiredShareHolderRank: UInt,
      signaturePrivateKey: SignaturePrivateKey,
    ): Either<CreateSignatureError, ShareResend> =
      either {
        ShareResend(
          groupId,
          cipherSuite,
          leafIndex,
          requiredShareHolderRank,
          cipherSuite.signWithLabel(
            signaturePrivateKey,
            "ShareResendTBS",
            Tbs(groupId, cipherSuite, leafIndex, requiredShareHolderRank).encodeUnsafe(),
          ).bind(),
        )
      }
  }

  data class Tbs(
    val groupId: GroupId,
    val cipherSuite: CipherSuite,
    val leafIndex: LeafIndex,
    val requiredShareHolderRank: UInt,
  ) : Struct4T.Shape<GroupId, CipherSuite, LeafIndex, UInt> {

    companion object : Encodable<Tbs> {
      @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
      override val T: DataType<Tbs> =
        struct("ShareResend") {
          it.field("group_id", GroupId.T)
            .field("cipher_suite", CipherSuite.T)
            .field("leaf_index", LeafIndex.T)
            .field("required_share_holder_rank", uint32.asUInt)
        }.lift(ShareResend::Tbs)
    }
  }

}
