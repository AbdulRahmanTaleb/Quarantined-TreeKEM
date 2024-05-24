package com.github.traderjoe95.mls.protocol.error

import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey

sealed interface GroupGhostInfoError: ProcessMessageError, SenderCommitError, CommitError {
  data object GhostKeyAlreadySavedForThisEpoch: GroupGhostInfoError

  data class GhostShareAlreadySavedForThisKey(val key: HpkePublicKey): GroupGhostInfoError

  data class MemberIsAlreadyGhost(val leafIndex: LeafIndex): GroupGhostInfoError

  data class GhostNotFound(val leafIndex: LeafIndex): GroupGhostInfoError

  data class InvalidEpoch(val epoch: ULong): GroupGhostInfoError

  data class KeyNotFoundForEpoch(val epoch: ULong): GroupGhostInfoError
}
