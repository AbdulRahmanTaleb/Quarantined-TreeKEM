package com.github.traderjoe95.mls.protocol.message

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.raise.Raise
import arrow.core.raise.either
import com.github.traderjoe95.mls.codec.Encodable
import com.github.traderjoe95.mls.codec.decodeAs
import com.github.traderjoe95.mls.codec.type.DataType
import com.github.traderjoe95.mls.codec.type.V
import com.github.traderjoe95.mls.codec.type.get
import com.github.traderjoe95.mls.codec.type.optional
import com.github.traderjoe95.mls.codec.type.struct.Struct1T
import com.github.traderjoe95.mls.codec.type.struct.Struct2T
import com.github.traderjoe95.mls.codec.type.struct.Struct3T
import com.github.traderjoe95.mls.codec.type.struct.Struct4T
import com.github.traderjoe95.mls.codec.type.struct.lift
import com.github.traderjoe95.mls.codec.type.struct.struct
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.crypto.ICipherSuite
import com.github.traderjoe95.mls.protocol.error.DecoderError
import com.github.traderjoe95.mls.protocol.error.HpkeDecryptError
import com.github.traderjoe95.mls.protocol.error.HpkeEncryptError
import com.github.traderjoe95.mls.protocol.error.WelcomeBackGhostError
import com.github.traderjoe95.mls.protocol.error.WelcomeJoinError
import com.github.traderjoe95.mls.protocol.psk.PreSharedKeyId
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.crypto.Aad
import com.github.traderjoe95.mls.protocol.types.crypto.Ciphertext
import com.github.traderjoe95.mls.protocol.types.crypto.HpkeCiphertext
import com.github.traderjoe95.mls.protocol.types.crypto.HpkeKeyPair
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import com.github.traderjoe95.mls.protocol.types.crypto.Secret
import com.github.traderjoe95.mls.protocol.types.framing.content.PreSharedKey
import com.github.traderjoe95.mls.protocol.types.framing.enums.WireFormat

data class WelcomeBackGhost(
  val cipherSuite: CipherSuite,
  val groupId: GroupId,
  val secrets: List<WelcomeBackEncryptedGroupSecrets>,
  val encryptedGroupInfo: Ciphertext,
) : Message, Struct4T.Shape<CipherSuite, GroupId, List<WelcomeBackEncryptedGroupSecrets>, Ciphertext> {
  override val wireFormat: WireFormat = WireFormat.MlsWelcomeBackGhost

  override val encoded: ByteArray by lazy { encodeUnsafe() }

  fun decryptWelcomeBackGroupSecrets(
    keyPair: HpkeKeyPair,
  ): Either<WelcomeBackGhostError, WelcomeBackGroupSecrets> =
    either {
      secrets
        .find { it.ghostKey.eq(keyPair.public) }
        ?.decrypt(cipherSuite, keyPair, encryptedGroupInfo)
        ?: raise(WelcomeBackGhostError.NoGroupSecretsForGhost )
    }

  fun decryptWelcomeBackGroupInfo(
    joinerSecret: Secret,
    pskSecret: Secret,
  ): Either<WelcomeBackGhostError, GroupInfo> =
    either {
      val joinerExtracted = cipherSuite.extract(joinerSecret, pskSecret)
      val welcomeBackSecret = cipherSuite.deriveSecret(joinerExtracted, "welcome_back")

      val nonce = cipherSuite.expandWithLabel(welcomeBackSecret, "nonce", byteArrayOf(), cipherSuite.nonceLen).asNonce
      val key = cipherSuite.expandWithLabel(welcomeBackSecret, "key", byteArrayOf(), cipherSuite.keyLen)

      DecoderError.wrap {
        GroupInfo.decode(cipherSuite.decryptAead(key, nonce, Aad.empty, encryptedGroupInfo).bind())
      }
    }

  companion object : Encodable<WelcomeBackGhost> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<WelcomeBackGhost> =
      struct("WelcomeBackGhost") {
        it.field("cipher_suite", CipherSuite.T)
          .field("group_id", GroupId.T)
          .field("secrets", WelcomeBackEncryptedGroupSecrets.T[V])
          .field("encrypted_group_info", Ciphertext.T)
      }.lift(::WelcomeBackGhost)
  }
}

data class WelcomeBackGroupSecrets(
  val joinerSecret: Secret,
  val pathSecret: Secret,
  val preSharedKeyIds: List<PreSharedKeyId> = listOf(),
) : Struct3T.Shape<Secret, Secret, List<PreSharedKeyId>> {
  fun encrypt(
    cipherSuite: CipherSuite,
    encryptionKey: HpkePublicKey,
    encryptedGroupInfo: Ciphertext,
  ): Either<HpkeEncryptError, WelcomeBackEncryptedGroupSecrets> =
    either {
      WelcomeBackEncryptedGroupSecrets(
        encryptionKey,
        cipherSuite.encryptWithLabel(
          encryptionKey,
          "WelcomeBack",
          encryptedGroupInfo.bytes,
          encodeUnsafe(),
        ).bind(),
      )
    }

  companion object : Encodable<WelcomeBackGroupSecrets> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<WelcomeBackGroupSecrets> =
      struct("GroupSecrets") {
        it.field("joiner_secret", Secret.T)
          .field("path_secret", Secret.T)
          .field("psks", PreSharedKeyId.T[V])
      }.lift(::WelcomeBackGroupSecrets)
  }
}

data class WelcomeBackEncryptedGroupSecrets(
  val ghostKey: HpkePublicKey,
  val encryptedGroupSecrets: HpkeCiphertext,
) : Struct2T.Shape<HpkePublicKey, HpkeCiphertext> {
  context(Raise<HpkeDecryptError>, Raise<DecoderError>)
  internal fun decrypt(
    cipherSuite: ICipherSuite,
    encryptKeyPair: HpkeKeyPair,
    encryptedGroupInfo: Ciphertext,
  ): WelcomeBackGroupSecrets =
    DecoderError.wrap {
      cipherSuite.decryptWithLabel(
        encryptKeyPair,
        "WelcomeBack",
        encryptedGroupInfo.bytes,
        encryptedGroupSecrets,
      ).bind().decodeAs(WelcomeBackGroupSecrets.T)
    }

  companion object : Encodable<WelcomeBackEncryptedGroupSecrets> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<WelcomeBackEncryptedGroupSecrets> =
      struct("GroupSecrets") {
        it.field("ghost_key", HpkePublicKey.T)
          .field("encrypted_group_secrets", HpkeCiphertext.T)
      }.lift(::WelcomeBackEncryptedGroupSecrets)
  }
}
