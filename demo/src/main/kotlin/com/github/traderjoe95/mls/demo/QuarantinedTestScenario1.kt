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
// BASIC QUARANTINE TEST
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val clientsList = listOf("Alice", "Bob", "Charlie", "Dan")

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

  val idxGhosts = listOf(clients.size-1)
  for(i in 0..2) {
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

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // GHOST RECONNECTING
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("GHOST RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  // Ghost user sending QuarantineEnd message to all group members in order to retrieve shares
  // When users receive this message, they automatically send ShareRecoveryMessage, for those
  // who have a valid share with a shareholder rank = 1
  println("\n---------------------------- Sending Quarantine End")
  clients[clients.size-1].ghostReconnect(groups[clients.size-1].groupId)
  clients.filterIndexed{idx, _ -> !idxGhosts.contains(idx)}.forEach{
    it.processNextMessage().getOrThrow()
  }

  // In this scenario, all users have exactly shareholder rank = 1, so there is a total
  // of clients.size-1 ShareRecoveryMessage sent (3 in this case), the ghost user
  // needs these 3 shares to recover its ghost key
  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in 0..clients.size-1){
    clients.forEach {
      it.processNextMessage().getOrThrow()
    }
  }

//  println("")
//  updateKeys(groups, clients, clientsList, 4, idxUpdate.filter { it != idxGhosts[0] }, idxCommit, noCommit =  true)

  // When a user commits here, it sends to the recovering ghost user a
  // WelcomeBackGhostMessage with the new groupContext for this epoch
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients,
  )

  // The ghost user processes the WelcomeBackGhost Message
  clients[clients.size-1].processNextMessage().getOrThrow()

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
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[clients.size-1].startGhostMessageRecovery().getOrThrow()
  clients[clients.size-1].processCachedGhostMessages().getOrThrow()
  groups[clients.size-1].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  printGroups(groups, clients, clientsList)

}
