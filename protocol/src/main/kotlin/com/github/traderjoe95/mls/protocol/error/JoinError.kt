package com.github.traderjoe95.mls.protocol.error

import com.github.traderjoe95.mls.protocol.psk.ResumptionPskId
import com.github.traderjoe95.mls.protocol.types.GroupContextExtension
import com.github.traderjoe95.mls.protocol.types.tree.leaf.Capabilities

sealed interface WelcomeJoinError {
  data object NoMatchingKeyPackage : WelcomeJoinError

  data object NoGroupSecretsForKeyPackage : WelcomeJoinError

  data object OwnLeafNotFound : WelcomeJoinError

  data object MultipleResumptionPsks : WelcomeJoinError

  data class WrongResumptionEpoch(val epoch: ULong) : WelcomeJoinError

  data class MissingResumptionGroup(val pskId: ResumptionPskId) : WelcomeJoinError
}

sealed interface WelcomeBackGhostError : ProcessMessageError{

  data object NoGroupSecretsForGhost : WelcomeBackGhostError

}

sealed interface ShareResendError : ProcessMessageError{

  data object InvalidLeafIndexNotFoundInCachedQuarantineEnd : ShareResendError

}

sealed interface ExternalJoinError {
  data object MissingExternalPub : ExternalJoinError

  data object UnexpectedError : ExternalJoinError
}

sealed interface NewMemberAddProposalError

sealed interface JoinError : WelcomeJoinError, ExternalJoinError, NewMemberAddProposalError {
  data object MissingRatchetTree : JoinError

  data object AlreadyMember : JoinError
}

sealed interface ExtensionSupportError : JoinError, GroupCreationError {
  data class UnsupportedGroupContextExtensions(
    val capabilities: Capabilities,
    val unsupported: List<GroupContextExtension<*>>,
  ) : ExtensionSupportError
}
