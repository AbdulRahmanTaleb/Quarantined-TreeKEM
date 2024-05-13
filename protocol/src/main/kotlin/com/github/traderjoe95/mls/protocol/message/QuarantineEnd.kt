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

data class QuarantineEnd(
  val groupId: GroupId,
  val cipherSuite: CipherSuite,
  val leafIndex: LeafIndex,
  val leafNode: UpdateLeafNode,
  val signature: Signature,
) : Message,
  Struct5T.Shape<GroupId, CipherSuite, LeafIndex, UpdateLeafNode, Signature> {
  override val wireFormat: WireFormat = WireFormat.MlsQuarantineEnd

  override val encoded: ByteArray by lazy { encodeUnsafe() }


  fun verifySignature(): Either<VerifySignatureError, QuarantineEnd> =
    either {
      this@QuarantineEnd.apply {
        cipherSuite.verifyWithLabel(
          leafNode.signaturePublicKey,
          "QuarantineEndTBS",
          Tbs(groupId, cipherSuite, leafIndex, leafNode).encodeUnsafe(),
          signature,
        )
      }
    }

  companion object : Encodable<QuarantineEnd> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<QuarantineEnd> =
      struct("QuarantineEnd") {
        it.field("group_id", GroupId.T)
          .field("cipher_suite", CipherSuite.T)
          .field("leaf_index", LeafIndex.T)
          .field("leaf_node", LeafNode.t(LeafNodeSource.Update))
          .field("signature", Signature.T)
      }.lift(::QuarantineEnd)

    fun create(
      groupId: GroupId,
      cipherSuite: CipherSuite,
      leafIndex: LeafIndex,
      leafNode: UpdateLeafNode,
      signaturePrivateKey: SignaturePrivateKey,
    ): Either<CreateSignatureError, QuarantineEnd> =
      either {
        QuarantineEnd(
          groupId,
          cipherSuite,
          leafIndex,
          leafNode,
          cipherSuite.signWithLabel(
            signaturePrivateKey,
            "QuarantineEndTBS",
            Tbs(groupId, cipherSuite, leafIndex, leafNode).encodeUnsafe(),
          ).bind(),
        )
      }
  }

  data class Tbs(
    val groupId: GroupId,
    val cipherSuite: CipherSuite,
    val leafIndex: LeafIndex,
    val leafNode: UpdateLeafNode,
  ) : Struct4T.Shape<GroupId, CipherSuite, LeafIndex, UpdateLeafNode> {

    companion object : Encodable<Tbs> {
      @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
      override val T: DataType<Tbs> =
        struct("QuarantineEnd") {
          it.field("group_id", GroupId.T)
            .field("cipher_suite", CipherSuite.T)
            .field("leaf_index", LeafIndex.T)
            .field("leaf_node", LeafNode.t(LeafNodeSource.Update))
        }.lift(QuarantineEnd::Tbs)
    }

  }

}
