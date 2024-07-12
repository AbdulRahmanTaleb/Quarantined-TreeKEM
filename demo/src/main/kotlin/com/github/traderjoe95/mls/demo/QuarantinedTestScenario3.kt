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
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.framing.content.Add


//////////////////////////////////////////////////////////////////////
// QUARANTINE TEST: 2 users becoming ghosts one after the other
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val clientsList = listOf("Alice", "Bob", "Charlie", "Dan", "Eve")

  val (clients, groups) = initiateGroup(clientsList)

  //////////////////////////////////////////////////////////////////////
  // SENDING MESSAGES
  //////////////////////////////////////////////////////////////////////
  basicConversation(clients, groups, id = "0")

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // UPDATING KEYS
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  val idxCommit = 1
  val idxUpdate = IntRange(0, clients.size-1).toList()
  updateKeys(
    groups,
    clients,
    clientsList,
    idxUpdate,
    idxCommit,
  )

  // First user to become ghost -> Eve
  val idxGhost1 = clients.size-1

  // Second user to become ghost -> Charlie
  val idxGhost2 = clients.size-3

  // Updating keys for all users except Ghost1 so that it can become ghost first
  for(i in 0..4) {
    updateKeys(
      groups,
      clients,
      clientsList,
      idxUpdate,
      idxCommit,
      listOf(idxGhost1)
    )
  }

  printGroups(groups, clients, clientsList)

//  groups.forEach {
//    println(it.state.ghostMembersShareHolderRank)
//  }

  // Now making Ghost2 a ghost user too
  val idxGhosts = listOf(idxGhost1, idxGhost2)
  for(i in 0..4) {
    updateKeys(
      groups,
      clients,
      clientsList,
      idxUpdate,
      idxCommit,
      idxGhosts
    )
  }

  printGroups(groups, clients, clientsList)

  //////////////////////////////////////////////////////////////////////
  // SENDING MESSAGES
  //////////////////////////////////////////////////////////////////////
  basicConversation(clients, groups, clients.slice(idxGhosts), id = "1")

//  groups.forEach {
//    println(it.state.ghostMembersShareHolderRank)
//  }

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // GHOST RECONNECTING: Ghost1
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("GHOST RECONNECTING: Ghost1")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  // Ghost1 user sending QuarantineEnd message to all group members in order to retrieve shares
  // When users receive this message, they automatically send ShareRecoveryMessage, for those
  // who have a valid share with a shareholder rank = 1
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhost1].ghostReconnect(groups[idxGhost1].groupId)
  clients.filterIndexed{idx, _ -> !idxGhosts.contains(idx)}.forEach{
    it.processNextMessage().getOrThrow()
  }
  clients[idxGhost1].requestWelcomeBackGhost(groups[idxGhost1].groupId)
  clients.filterIndexed{idx, _ -> !idxGhosts.contains(idx)}.forEach{
    it.processNextMessage().getOrThrow()
  }

  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in 0..clients.size-2){
    clients.filterIndexed{idx, _ -> idx != idxGhost2}.forEach {
      it.processNextMessage().getOrThrow()
    }
  }

  // Since Ghost2 holds a share with shareholder rank = 1 for Ghost1,
  // Ghost1 has to ask for the same share with shareholder rank = 2,
  // since Ghost2 is now a ghost and couldn't respond to Ghost1's request
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

  // When a user commits here, it sends to the recovering ghost user a
  // WelcomeBackGhostMessage with the new groupContext for this epoch
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients,
    listOf(clients[idxGhost2])
  )

  // The ghost user processes the WelcomeBackGhost Message
  clients[idxGhost1].processNextMessage().getOrThrow()

  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  printGroups(groups, clients, clientsList)

  //////////////////////////////////////////////////////////////////////
  // SENDING MESSAGES
  //////////////////////////////////////////////////////////////////////
  basicConversation(clients, groups, clients.slice(listOf(idxGhost2)), id = "2")

  //////////////////////////////////////////////////////////////////////
  // RECOVERING MESSAGES TEST
  //////////////////////////////////////////////////////////////////////

  // At this stage, the ghost user have recovered enough shares to reconstruct its ghost key
  // and decrypt all of the messages cached during its quarantine period
  println("RECOVERING MESSAGES TEST")
  clients[idxGhost1].retrievedEnoughShares(groups[idxGhost1].groupId)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[idxGhost1].startGhostMessageRecovery().getOrThrow()
  clients[idxGhost1].processCachedGhostMessages().getOrThrow()
  groups[idxGhost1].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")


  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // GHOST RECONNECTING: Ghost2
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

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

  }

  // When a user commits here, it sends to the recovering ghost user a
  // WelcomeBackGhostMessage with the new groupContext for this epoch
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients
  )

  // The ghost user processes the WelcomeBackGhost Message
  clients[idxGhost2].processNextMessage().getOrThrow()

  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  printGroups(groups, clients, clientsList)

  //////////////////////////////////////////////////////////////////////
  // SENDING MESSAGES
  //////////////////////////////////////////////////////////////////////
  basicConversation(clients, groups, id = "3")

  //////////////////////////////////////////////////////////////////////
  // RECOVERING MESSAGES TEST
  //////////////////////////////////////////////////////////////////////

  // At this stage, the ghost user have recovered enough shares to reconstruct its ghost key
  // and decrypt all of the messages cached during its quarantine period
  println("RECOVERING MESSAGES TEST")
  clients[idxGhost2].retrievedEnoughShares(groups[idxGhost2].groupId)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[idxGhost2].startGhostMessageRecovery().getOrThrow()
  clients[idxGhost2].processCachedGhostMessages().getOrThrow()
  groups[idxGhost2].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")


}
