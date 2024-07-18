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
import java.lang.System.Logger
import java.lang.System.getenv

//////////////////////////////////////////////////////////////////////
// BASIC QUARANTINE TEST: two users become ghosts
// the first one rejoins the group by asking for a welcome back message
// the second one rejoins the group by decrypting cached messages
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val clientsList = listOf("Alice", "Bob", "Charlie", "Dan", "Eve", "Fred", "Gregory")
  val (clients, groups) = initiateGroup(clientsList)

  // A basic conversation
  basicConversation(clients, groups, id = "0")

  // All users except idxGhosts (Dan) update their keys
  val idxCommit = 1
  val idxGhosts = listOf(clients.size-2,clients.size-1)

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

  for(i in 0..<GroupState.UPDATE_QUARANTINE_KEYS_DELAY.toInt()*2) {
    updateKeys(
      groups,
      clients,
      clientsList,
      idxCommit,
      idxGhosts
    )
  }

  // A basic conversation (except for idxGhosts - Dan)
  basicConversation(clients, groups, idxGhosts, id = "1")

  val idxGhost1 = idxGhosts[0]
  val idxGhost2 = idxGhosts[1]

  groups.forEachIndexed {idx, it ->
    println(clientsList[idx])
    it.state.groupGhostInfo.printRanks()
    println("")
  }

  // Fred reconnecting after their quarantine
  println(clientsList[idxGhost1] + " RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  // Fred sends QuarantineEnd message to all group members in order to retrieve shares
  // When users receive this message, they automatically send ShareRecoveryMessage, for those
  // who have a valid share with a shareholder rank = 1
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhost1].ghostReconnect(groups[idxGhost1].groupId)
  clients.forEach{
    it.processNextMessage().getOrThrow()
  }
  // In this scenario, all users have exactly shareholder rank = 1
  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in 0..clients.size-1){
    clients.forEach {
      it.processNextMessage().getOrThrow()
    }
  }
  // Fred also sends a RequestWelcomeBackGhost message to ask for the current groupInfo
  // and be able to join the group without having to decrypt cached conversations
  println("\n---------------------------- Sending Request Welcome Back")
  clients[idxGhost1].requestWelcomeBackGhost(groups[idxGhost1].groupId)
  clients.forEach{
    it.processNextMessage().getOrThrow()
  }
  // When a user commits here, it sends to the recovering ghost user a
  // WelcomeBackGhostMessage with the new groupContext for this epoch, since
  // the ghost asked for it
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients,
  )
  // The ghost user processes the WelcomeBackGhost Message
  clients[idxGhost1].processNextMessage().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")
  printGroups(groups, clientsList,listOf(idxGhost2))

  // A basic conversation
  basicConversation(clients, groups, listOf(idxGhost2), id = "3")
  // At this stage, the ghost user have recovered enough shares to reconstruct its ghost key
  // and decrypt all of the messages cached during its quarantine period
  println("RECOVERING MESSAGES TEST")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[idxGhost1].startGhostMessageRecovery().getOrThrow()
  clients[idxGhost1].processCachedGhostMessages().getOrThrow()
  groups[idxGhost1].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  updateKeys(
    groups,
    clients,
    clientsList,
    committerIdx = 0,
    excludeClients = listOf(idxGhost2)
  )
  printGroups(groups, clientsList, listOf(idxGhost2))

  // Gregory reconnecting after their quarantine
  println(clientsList[idxGhost2] + " RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhost2].ghostReconnect(groups[idxGhost2].groupId)
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
  groups[idxGhost2].startGhostMessageRecovery().getOrThrow()
  clients[idxGhost2].processCachedGhostMessages().getOrThrow()
  groups[idxGhost2].endGhostMessageRecovery().getOrThrow()
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

  DeliveryService.empty()
}
