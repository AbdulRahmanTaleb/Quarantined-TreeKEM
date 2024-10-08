package com.github.traderjoe95.mls.protocol.group

import com.github.traderjoe95.mls.protocol.message.KeyPackage
import com.github.traderjoe95.mls.protocol.message.MlsCommitMessage
import com.github.traderjoe95.mls.protocol.message.MlsMessage
import com.github.traderjoe95.mls.protocol.message.Welcome
import com.github.traderjoe95.mls.protocol.message.WelcomeBackGhost
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode

typealias WelcomeMessages = List<PrepareCommitResult.WelcomeMessage>
typealias WelcomeBackGhostMessages = List<PrepareCommitResult.WelcomeBackGhostMessage>

data class PrepareCommitResult(
  val newGroupState: GroupState,
  val commit: MlsCommitMessage,
  val welcomeMessages: WelcomeMessages,
  val welcomeBackGhostMessages: WelcomeBackGhostMessages
) {
  data class WelcomeMessage(
    val welcome: MlsMessage<Welcome>,
    val to: List<KeyPackage>,
  )

  data class WelcomeBackGhostMessage(
    val welcome: MlsMessage<WelcomeBackGhost>,
    val to: List<LeafNode<*>>,
  )
}
