package com.github.traderjoe95.mls.protocol.error

import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import com.github.traderjoe95.mls.protocol.types.framing.enums.ContentType
import com.github.traderjoe95.mls.protocol.types.framing.enums.SenderType
import com.github.traderjoe95.mls.protocol.types.framing.enums.WireFormat

sealed interface CreateMessageError :
  SenderCommitError,
  CreateRemoveError,
  CreateUpdateError,
  CreateAddError,
  CreatePreSharedKeyError,
  CreateGroupContextExtensionsError,
  CreateReInitError,
  CreateQuarantineEndError,
  CreateRequestWelcomeBackGhostError,
  CreateShareResendMessageError

sealed interface MessageSenderError : PrivateMessageSenderError, PublicMessageSenderError {
  data class InvalidSenderType(val senderType: SenderType, val reason: String) : MessageSenderError
}

sealed interface MessageRecipientError : PrivateMessageRecipientError, PublicMessageRecipientError {
  data class UnexpectedWireFormat(
    val wireFormat: WireFormat,
    val expectedWireFormat: WireFormat? = null,
  ) : MessageRecipientError, CreateAddError, WelcomeJoinError, ExternalJoinError

  data class UnexpectedContent(
    val contentType: ContentType<*>,
    val expectedContentType: ContentType<*>,
  ) : MessageRecipientError

  data class WrongGroup(val groupId: GroupId, val expectedGroupId: GroupId) : HistoryAccessError
}

sealed interface MessageError : MessageSenderError, MessageRecipientError, PrivateMessageError, PublicMessageError

sealed interface ProcessMessageError {
  data object MustUseCachedStateForOwnCommit : ProcessMessageError

  data class HandshakeMessageForWrongEpoch(val groupId: GroupId, val epoch: ULong, val currentEpoch: ULong) :
    ProcessMessageError
}


sealed interface GhostRecoveryProcessError {
  data object NotEnoughSharesForKeyRecoveryError : GhostRecoveryProcessError

  data object WrongGhostEpoch : GhostRecoveryProcessError

  data object UnexpectedEpochAfterMessageRecovery : GhostRecoveryProcessError

  data object IncoherentStates : GhostRecoveryProcessError

  data class IncorrectRecoveredKey(val expected: HpkePublicKey, val obtained: HpkePublicKey) : GhostRecoveryProcessError

  data object UnexpectedErrorUserIsNotGhost : GhostRecoveryProcessError

}
