package com.github.traderjoe95.mls.demo

import com.github.traderjoe95.mls.demo.client.Client
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.demo.util.basicConversation
import com.github.traderjoe95.mls.demo.util.commit
import com.github.traderjoe95.mls.demo.util.initiateGroup
import com.github.traderjoe95.mls.demo.util.printGroup
import com.github.traderjoe95.mls.demo.util.printGroups
import com.github.traderjoe95.mls.demo.util.updateKeys
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.group.GroupState
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.framing.content.Add


//////////////////////////////////////////////////////////////////////
// QUARANTINE TEST: user becomes ghost, share distribution done
// using horizontal method
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val clientsList = listOf("Alice", "Bob", "Charlie", "Dan")

  val (clients, groups) = initiateGroup(clientsList)

  // A basic conversation
  basicConversation(clients, groups, id = "0")

  // Updating keys
  val idxCommit = 3
  updateKeys(
    groups,
    clients,
    clientsList,
    idxCommit,
  )

  groups[0].tree.print()

  val idxGhosts = listOf(0)
  for(i in 0..<GroupState.INACTIVITY_DELAY.toInt()) {
    updateKeys(
      groups,
      clients,
      clientsList,
      idxCommit,
      idxGhosts
    )
  }
  printGroups(groups, clientsList, idxGhosts)

  // A basic conversation
  basicConversation(clients, groups, idxGhosts, id = "1")

  // Alice reconnecting after their quarantine
  val idxGhost = idxGhosts[0]
  println(clientsList[idxGhost] + " RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhost].ghostReconnect(groups[idxGhost].groupId)
  clients.forEach{
    it.processNextMessage().getOrThrow()
  }
  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in 0..clients.size-1){
    clients.forEach {
      it.processNextMessage().getOrThrow()
    }
  }
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients,
  )
  // Gregory does not request a welcomeBack, but decrypts their cached messages
  // instead to recover the current state
  println("RECOVERING MESSAGES TEST")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[idxGhost].startGhostMessageRecovery().getOrThrow()
  clients[idxGhost].processCachedGhostMessages().getOrThrow()
  groups[idxGhost].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  updateKeys(
    groups,
    clients,
    clientsList,
    committerIdx = 0,
  )
  printGroups(groups, clientsList)

  // A basic conversation
  basicConversation(clients, groups, id = "4")


}
