package com.github.traderjoe95.mls.protocol.message

import arrow.core.raise.Raise
import arrow.core.raise.ensure
import com.github.traderjoe95.mls.codec.Encodable
import com.github.traderjoe95.mls.codec.type.DataType
import com.github.traderjoe95.mls.codec.type.struct.Struct3T
import com.github.traderjoe95.mls.codec.type.struct.lift
import com.github.traderjoe95.mls.codec.type.struct.member.then
import com.github.traderjoe95.mls.codec.type.struct.struct
import com.github.traderjoe95.mls.codec.util.throwAnyError
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.error.MessageRecipientError
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.crypto.Ciphertext
import com.github.traderjoe95.mls.protocol.types.framing.content.Content
import com.github.traderjoe95.mls.protocol.types.framing.enums.ContentType
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion.MLS_1_0
import com.github.traderjoe95.mls.protocol.types.framing.enums.WireFormat

class MlsMessage<out M : Message>
  @PublishedApi
  internal constructor(
    val protocolVersion: ProtocolVersion,
    val message: M,
  ) : Struct3T.Shape<ProtocolVersion, WireFormat, M> {
    constructor(message: M) : this(MLS_1_0, message)

    @get:JvmName("encoded")
    val encoded: ByteArray by lazy { encodeUnsafe() }

    val wireFormat: WireFormat
      get() = message.wireFormat

    override fun component1(): ProtocolVersion = protocolVersion

    override fun component2(): WireFormat = message.wireFormat

    override fun component3(): M = message

    companion object : Encodable<MlsMessage<*>> {
      internal fun <C : Content<C>> public(message: PublicMessage<C>): MlsMessage<PublicMessage<C>> = MlsMessage(MLS_1_0, message)

      internal fun <C : Content<C>> private(message: PrivateMessage<C>): MlsMessage<PrivateMessage<C>> = MlsMessage(MLS_1_0, message)

      internal fun welcome(
        cipherSuite: CipherSuite,
        encryptedGroupSecrets: List<EncryptedGroupSecrets>,
        encryptedGroupInfo: Ciphertext,
      ): MlsMessage<Welcome> = welcome(Welcome(cipherSuite, encryptedGroupSecrets, encryptedGroupInfo))

      internal fun welcomeBackGhost(
        cipherSuite: CipherSuite,
        groupId: GroupId,
        encryptedGroupSecrets: List<WelcomeBackEncryptedGroupSecrets>,
        encryptedGroupInfo: Ciphertext,
      ): MlsMessage<WelcomeBackGhost> = welcomeBackGhost(WelcomeBackGhost(cipherSuite, groupId, encryptedGroupSecrets, encryptedGroupInfo))

      @JvmStatic
      fun welcome(message: Welcome): MlsMessage<Welcome> = MlsMessage(MLS_1_0, message)

      @JvmStatic
      fun welcomeBackGhost(message: WelcomeBackGhost): MlsMessage<WelcomeBackGhost> = MlsMessage(MLS_1_0, message)

      @JvmStatic
      fun groupInfo(message: GroupInfo): MlsMessage<GroupInfo> = MlsMessage(MLS_1_0, message)

      @JvmStatic
      fun keyPackage(message: KeyPackage): MlsMessage<KeyPackage> = MlsMessage(MLS_1_0, message)

      inline fun <reified M : Message> MlsMessage<Message>.coerceFormat(): MlsMessage<M> = MlsMessage(protocolVersion, message as M)

      context(Raise<MessageRecipientError.UnexpectedWireFormat>)
      inline fun <reified M : Message> MlsMessage<Message>.ensureFormat(wireFormat: WireFormat? = null): MlsMessage<M> =
        if (message is M) {
          MlsMessage(protocolVersion, message)
        } else {
          raise(MessageRecipientError.UnexpectedWireFormat(this.wireFormat, wireFormat))
        }

      context(Raise<MessageRecipientError>)
      inline fun <C : Content<C>, reified M : GroupMessage<C>> MlsMessage<Message>.ensureFormatAndContent(
        wireFormat: WireFormat,
        contentType: ContentType<C>,
      ): MlsMessage<M> =
        if (message is M && this.wireFormat == wireFormat) {
          MlsMessage(protocolVersion, message).also {
            ensure(message.contentType == contentType) {
              MessageRecipientError.UnexpectedContent(
                message.contentType,
                contentType,
              )
            }
          }
        } else {
          raise(MessageRecipientError.UnexpectedWireFormat(this.wireFormat, wireFormat))
        }

      @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
      override val T: DataType<MlsMessage<*>> =
        throwAnyError {
          struct("MLSMessage") {
            it.field("version", ProtocolVersion.T, MLS_1_0)
              .field("wire_format", WireFormat.T)
              .select<Message, _>(WireFormat.T, "wire_format") {
                case(WireFormat.MlsPublicMessage).then(PublicMessage.T, "public_message")
                  .case(WireFormat.MlsPrivateMessage).then(PrivateMessage.T, "private_message")
                  .case(WireFormat.MlsWelcome).then(Welcome.T, "welcome")
                  .case(WireFormat.MlsGroupInfo).then(GroupInfo.T, "group_info")
                  .case(WireFormat.MlsKeyPackage).then(KeyPackage.T, "key_package")
                  .case(WireFormat.MlsQuarantineEnd).then(QuarantineEnd.T, "quarantine_end")
                  .case(WireFormat.MlsShareRecoveryMessage).then(ShareRecoveryMessage.T, "share_recovery_message")
                  .case(WireFormat.MlsWelcomeBackGhost).then(WelcomeBackGhost.T, "welcome_back_ghost")
                  .case(WireFormat.MlsShareResend).then(ShareResend.T, "share_resend")
                  .case(WireFormat.MlsRequestWelcomeBackGhost).then(RequestWelcomeBackGhost.T, "request_welcome_back_ghost")
              }
          }.lift { v, _, msg -> MlsMessage(v, msg) }
        }
    }
  }
