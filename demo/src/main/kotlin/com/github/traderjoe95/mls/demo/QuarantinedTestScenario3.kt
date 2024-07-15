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
// QUARANTINE TEST: a ghost user has to ask for shares with share
// holder rank = 2, since they could not retrieve all shares with
// rank = 1
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val clientsList = listOf("Alice", "Bob", "Charlie", "Dan", "Eve", "Fred", "Gregory")
  val (clients, groups) = initiateGroup(clientsList)
  basicConversation(clients, groups, id = "0")

  var idxCommit = 1
  updateKeys(
    groups,
    clients,
    clientsList,
    idxCommit,
  )

  // First user to become ghost -> Gregory
  val idxGhost1 = clients.size-1
  // Updating keys for all users except Ghost1 so that it can become ghost first
  for(i in 0..< GroupState.INACTIVITY_DELAY.toInt()) {
    updateKeys(
      groups,
      clients,
      clientsList,
      idxCommit,
      listOf(idxGhost1)
    )
  }
  printGroups(groups, clientsList, listOf(idxGhost1))

  // At this point, Gregory became a ghost, the committer is Bob
  // the structure of the tree is :

  // groups[0].tree.print()
  // L0:  node (A) -  node (B) -  node (C) -  node (D) -  node (E) -  node (F) -  node (G) -  null
  // L1:  node -  null -  null -  null
  // L2:  node -  null
  // L3:  node

  // Since the committer is Bob, they chose the default share distribution method (3 shares) and assigned
  // the first share to Alice and Bob, the second share to Charlie and Dan, and the third share to
  // Eve and Fred

  // So Eve hold the third share with shareholder rank = 1, and Fred the third share with rank = 2
  // In the following, we will transform Eve into a ghost, so when Gregory reconnects, they cannot
  // retrieve the third share by asking for shareholder rank = 1, since Eve is not connected, and they
  // need to send another request with shareholder rank = 2



  // Second user to become ghost -> Eve
  val idxGhost2 = clients.size-3
  val idxGhosts = listOf(idxGhost1, idxGhost2)
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
  basicConversation(clients, groups, idxGhosts, id = "1")

  // Gregory reconnecting
  println("GHOST RECONNECTING: " + clientsList[idxGhost1])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhost1].ghostReconnect(groups[idxGhost1].groupId)
  clients.filterIndexed{idx, _ -> !idxGhosts.contains(idx)}.forEach{
    it.processNextMessage().getOrThrow()
  }
  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in 0..clients.size-2){
    clients.filterIndexed{idx, _ -> idx != idxGhost2}.forEach {
      it.processNextMessage().getOrThrow()
    }
  }

  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients,
    listOf(clients[idxGhost2])
  )

  // Since Eve holds a share with shareholder rank = 1 for Gregory,
  // Gregory has to ask for the same share with shareholder rank = 2,
  // since Eve is now a ghost and couldn't respond to Ghost1's request
  if(!clients[idxGhost1].retrievedEnoughShares(groups[idxGhost1].groupId)){
    println("\n---------------------------- Sending ADDITIONAL Share Resend Message")
    clients[idxGhost1].sendShareResend(groups[idxGhost1].groupId)
    clients.filterIndexed{idx, _ -> !idxGhosts.contains(idx)}.forEach{
      it.processNextMessage().getOrThrow()
    }
    println("\n---------------------------- Receiving ADDITIONAL Share Recovery Message")
    clients.filterIndexed{idx, _ -> idx != idxGhost2}.forEach {
      it.processNextMessage().getOrThrow()
    }
  }

  // At this stage, Gregory have recovered enough shares to reconstruct its ghost key
  // and decrypt all of the messages cached during its quarantine period
  println("RECOVERING MESSAGES TEST")
  clients[idxGhost1].retrievedEnoughShares(groups[idxGhost1].groupId)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[idxGhost1].startGhostMessageRecovery().getOrThrow()
  clients[idxGhost1].processCachedGhostMessages().getOrThrow()
  groups[idxGhost1].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  printGroups(groups, clientsList, listOf(idxGhost2))
  basicConversation(clients, groups, listOf(idxGhost2), id = "2")

  // Eve reconnecting
  println("GHOST RECONNECTING: Ghost2")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  // Ghost2 user sending QuarantineEnd message to all group members in order to retrieve shares
  // When users receive this message, they automatically send ShareRecoveryMessage, for those
  // who have a valid share with a shareholder rank = 1
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhost2].ghostReconnect(groups[idxGhost2].groupId)
  clients.forEach{
    it.processNextMessage().getOrThrow()
  }
  clients[idxGhost2].requestWelcomeBackGhost(groups[idxGhost2].groupId)
  clients.forEach{
    it.processNextMessage().getOrThrow()
  }
  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in 0..clients.size-1){
    clients.forEach {
      it.processNextMessage().getOrThrow()
    }
  }
  if(!clients[idxGhost2].retrievedEnoughShares(groups[idxGhost2].groupId)){
    println("\nSOMETHING IS WRONG")
    return
  }

  idxCommit = clientsList.size - 1 // Gregory (the former ghost)
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients
  )

  // The ghost user processes the WelcomeBackGhost Message
  clients[idxGhost2].processNextMessage().getOrThrow()

  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  printGroups(groups, clientsList)
  basicConversation(clients, groups, id = "3")

  //////////////////////////////////////////////////////////////////////
  // RECOVERING MESSAGES TEST
  //////////////////////////////////////////////////////////////////////
  // At this stage, Eve has recovered enough shares to reconstruct their ghost key
  // and decrypt all of the messages cached during its quarantine period
  println("RECOVERING MESSAGES TEST")
  clients[idxGhost2].retrievedEnoughShares(groups[idxGhost2].groupId)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[idxGhost2].startGhostMessageRecovery().getOrThrow()
  clients[idxGhost2].processCachedGhostMessages().getOrThrow()
  groups[idxGhost2].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")


}
