package com.github.traderjoe95.mls.protocol.error

import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.message.KeyPackage
import com.github.traderjoe95.mls.protocol.message.ShareRecoveryMessage
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.types.ProposalType
import com.github.traderjoe95.mls.protocol.types.crypto.SignaturePublicKey
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion
import com.github.traderjoe95.mls.protocol.types.framing.enums.SenderType
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode

sealed interface ProposalValidationError : CommitError {
  data class BadExternalProposal(val proposalType: ProposalType, val senderType: SenderType) : ProposalValidationError
}

sealed interface KeyPackageValidationError : AddValidationError {
  data class IncompatibleCipherSuite(val keyPackage: CipherSuite, val group: CipherSuite) : KeyPackageValidationError

  data class IncompatibleProtocolVersion(val keyPackage: ProtocolVersion, val group: ProtocolVersion) :
    KeyPackageValidationError

  data class InitKeyReuseAsEncryptionKey(val keyPackage: KeyPackage) : KeyPackageValidationError
}

sealed interface ShareRecoveryMessageError : ProcessMessageError {

  data object MissingCachedUpdateForGhost: ShareRecoveryMessageError
}

sealed interface WelcomeBackGhostMessageError : ProcessMessageError {

  data object MissingCachedUpdateForGhost: WelcomeBackGhostMessageError

  data object MissingRatchetTree: WelcomeBackGhostMessageError
}


sealed interface QuarantineEndValidationError : ProposalValidationError {
  data class IncompatibleCipherSuite(val keyPackage: CipherSuite, val group: CipherSuite) : QuarantineEndValidationError

  data class InvalidNewLeaf(val quarantineEnd: LeafNode<*>, val group: LeafNode<*>) : QuarantineEndValidationError

  data class LeafNotFound(val leafIdx: LeafIndex) : QuarantineEndValidationError

  data class LeafIsNotGhost(val leafIdx: LeafIndex) : QuarantineEndValidationError
}

sealed interface RequestWelcomeBackGhostValidationError : ProposalValidationError {
  data class IncompatibleCipherSuite(val keyPackage: CipherSuite, val group: CipherSuite) : RequestWelcomeBackGhostValidationError


  data class LeafNotFound(val leafIdx: LeafIndex) : RequestWelcomeBackGhostValidationError
}

sealed interface ShareResendValidationError : ProposalValidationError {
  data class IncompatibleCipherSuite(val keyPackage: CipherSuite, val group: CipherSuite) : ShareResendValidationError

  data class LeafNotFound(val leafIdx: LeafIndex) : ShareResendValidationError

}

sealed interface AddValidationError : ProposalValidationError, CreateAddError

sealed interface UpdateValidationError : ProposalValidationError, CreateUpdateError

sealed interface RemoveValidationError : ProposalValidationError, CreateRemoveError {
  data class BlankLeafRemoved(val leafIndex: LeafIndex) : RemoveValidationError

  data class UnauthorizedExternalRemove(val leafIndex: LeafIndex) : RemoveValidationError
}

sealed interface PreSharedKeyValidationError : ProposalValidationError, CreatePreSharedKeyError

sealed interface ReInitValidationError : ProposalValidationError, CreateReInitError {
  data class ReInitDowngrade(val from: ProtocolVersion, val to: ProtocolVersion) : ReInitValidationError
}

sealed interface GroupContextExtensionsValidationError : ProposalValidationError, CreateGroupContextExtensionsError
