package com.github.traderjoe95.mls.protocol.client

import com.github.traderjoe95.mls.protocol.message.GroupInfo
import com.github.traderjoe95.mls.protocol.message.KeyPackage
import com.github.traderjoe95.mls.protocol.message.QuarantineEnd
import com.github.traderjoe95.mls.protocol.message.ShareRecoveryMessage
import com.github.traderjoe95.mls.protocol.message.Welcome
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.framing.content.ApplicationData
import com.github.traderjoe95.mls.protocol.types.framing.content.AuthenticatedContent

sealed interface ProcessMessageResult<out Identity : Any> {
  data class WelcomeMessageReceived(
    val welcome: Welcome,
  ) : ProcessMessageResult<Nothing>

  data class GroupInfoMessageReceived(
    val groupInfo: GroupInfo,
  ) : ProcessMessageResult<Nothing>

  data class KeyPackageMessageReceived(
    val keyPackage: KeyPackage,
  ) : ProcessMessageResult<Nothing>

  data class ShareRecoveryMessageReceived(
    val shareRecoveryMessage: ShareRecoveryMessage,
  ) : ProcessMessageResult<Nothing>
  data class QuarantineEndReceived(
    val groupId: GroupId,
    val shareRecoveryMessage: ByteArray?
  ): ProcessMessageResult<Nothing> {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as QuarantineEndReceived

      if (groupId != other.groupId) return false
      if (shareRecoveryMessage != null) {
        if (other.shareRecoveryMessage == null) return false
        if (!shareRecoveryMessage.contentEquals(other.shareRecoveryMessage)) return false
      } else if (other.shareRecoveryMessage != null) return false

      return true
    }

    override fun hashCode(): Int {
      var result = groupId.hashCode()
      result = 31 * result + (shareRecoveryMessage?.contentHashCode() ?: 0)
      return result
    }
  }

  data class ApplicationMessageReceived(
    val groupId: GroupId,
    val applicationData: AuthenticatedContent<ApplicationData>,
  ) : ProcessMessageResult<Nothing>

  data class HandshakeMessageReceived<out Identity : Any>(
    val groupId: GroupId,
    val result: ProcessHandshakeResult<Identity>,
  ) : ProcessMessageResult<Identity>

  data object WelcomeBackGhostMessageIgnored: ProcessMessageResult<Nothing>

  data object WelcomeBackGhostMessageProcessed: ProcessMessageResult<Nothing>

  data object MessageToCachForLater: ProcessMessageResult<Nothing>
}
