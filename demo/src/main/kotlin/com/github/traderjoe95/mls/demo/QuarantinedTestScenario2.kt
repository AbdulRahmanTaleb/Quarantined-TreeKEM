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
// QUARANTINE TEST WITH 2 GHOSTS ONE AFTER THE OTHER
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
    1,
    idxUpdate,
    idxCommit,
  )

  // First user to become ghost -> Eve
  val idxGhost1 = clients.size-1

  // Second user to become ghost -> Charlie
  val idxGhost2 = clients.size-3

  // Updating keys for all users except Ghost1 so that it can become ghost first
  for(i in 0..1) {
    updateKeys(
      groups,
      clients,
      clientsList,
      i+2,
      idxUpdate,
      idxCommit,
      listOf(idxGhost1)
    )
  }

  printGroups(groups, clients, clientsList)

//  groups.forEach {
//    println(it.state.ghostMembersShareHolderRank)
//  }

  val idxGhosts = listOf(idxGhost1, idxGhost2)
  // Now making Ghost2 a ghost user too
  for(i in 0..1) {
    updateKeys(
      groups,
      clients,
      clientsList,
      i+2,
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
  // GHOST RECONNECTING
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("GHOST RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  // Ghost1 user sending QuarantineEnd message to all group members in order to retrieve shares
  // When users receive this message, they automatically send ShareRecoveryMessage, for those
  // who have a valid share with a shareholder rank = 1
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

  if(!clients[idxGhost1].retrievedEnoughShares(groups[idxGhost1].groupId)){
    println("\n---------------------------- Receiving ADDITIONAL Share Recovery Message")
    clients[idxGhost1].sendShareResend(groups[idxGhost1].groupId)
    clients.filterIndexed{idx, _ -> !idxGhosts.contains(idx)}.forEach{
      it.processNextMessage().getOrThrow()
    }
    println("\n---------------------------- Receiving ADDITIONAL Share Recovery Message")
    clients.filterIndexed{idx, _ -> idx != idxGhost2}.forEach {
      it.processNextMessage().getOrThrow()
    }
  }

//  println("")
//  updateKeys(groups, clients, clientsList, 4, idxUpdate.filter { !idxGhosts.contains(it) }, idxCommit, noCommit =  true)

  // When a user commits here, it sends to the recovering ghost user a
  // WelcomeBackGhostMessage with the new groupContext for this epoch
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients,
    listOf(clients[idxGhost2])
  )

  println("here")
  // The ghost user processes the WelcomeBackGhost Message
  clients[idxGhost1].processNextMessage().getOrThrow()

  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  printGroups(groups, clients, clientsList)

  //////////////////////////////////////////////////////////////////////
  // SENDING MESSAGES
  //////////////////////////////////////////////////////////////////////
  basicConversation(clients, groups, clients.slice(idxGhosts), id = "2")

  //////////////////////////////////////////////////////////////////////
  // RECOVERING MESSAGES TEST
  //////////////////////////////////////////////////////////////////////

  // At this stage, the ghost user have recovered enough shares to reconstruct its ghost key
  // and decrypt all of the messages cached during its quarantine period
  println("RECOVERING MESSAGES TEST")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[idxGhost1].startGhostMessageRecovery().getOrThrow()
  clients[idxGhost1].processCachedGhostMessages().getOrThrow()
  groups[idxGhost1].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

}
