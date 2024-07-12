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
// BASIC QUARANTINE TEST: A user becomes ghost, reconnects
// and recovers previous messages
// In this case, the ghost reconnects by asking for a WelcomeBack
// message
//////////////////////////////////////////////////////////////////////
suspend fun test1_1(){
  val clientsList = listOf("Alice", "Bob", "Charlie", "Dan", "Eve", "Fred")
  val (clients, groups) = initiateGroup(clientsList)

  // A basic conversation
  basicConversation(clients, groups, id = "0")

  // All users except idxGhosts (Dan) update their keys
  val idxCommit = 1
  val idxUpdate = IntRange(0, clients.size-1).toList()
  val idxGhosts = listOf(clients.size-1)
  for(i in 0..<GroupState.INACTIVITY_DELAY.toInt()) {
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

  // A basic conversation (except for idxGhosts - Dan)
  basicConversation(clients, groups, clients.slice(idxGhosts), id = "1")

  // Dan reconnecting after their quarantine
  println("GHOST RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  // Dan sends QuarantineEnd message to all group members in order to retrieve shares
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

  // Dan also sends a RequestWelcomeBackGhost message to ask for the current groupInfo
  // and be able to join the group without having to decrypt cached conversations
  println("\n---------------------------- Sending Request Welcome Back")
  clients[clients.size-1].requestWelcomeBackGhost(groups[clients.size-1].groupId)
  clients.filterIndexed{idx, _ -> !idxGhosts.contains(idx)}.forEach{
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
  clients[clients.size-1].processNextMessage().getOrThrow()

  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  printGroups(groups, clients, clientsList)

  // A basic conversation
  basicConversation(clients, groups, id = "3")

  // At this stage, the ghost user have recovered enough shares to reconstruct its ghost key
  // and decrypt all of the messages cached during its quarantine period
  println("RECOVERING MESSAGES TEST")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[clients.size-1].startGhostMessageRecovery().getOrThrow()
  clients[clients.size-1].processCachedGhostMessages().getOrThrow()
  groups[clients.size-1].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  updateKeys(
    groups,
    clients,
    clientsList,
    updaterGroupsIdx = clients.indices.toList(),
    committerGroupIdx = 0
  )

  printGroups(groups, clients, clientsList)

  DeliveryService.empty()
}

//////////////////////////////////////////////////////////////////////
// BASIC QUARANTINE TEST: A user becomes ghost, reconnects
// and recovers previous messages
// In this case, the ghost reconnects by reconstructing its keys
//////////////////////////////////////////////////////////////////////
suspend fun test1_2(){
  val clientsList = listOf("Alice", "Bob", "Charlie", "Dan", "Eve", "Fred")
  val (clients, groups) = initiateGroup(clientsList)

  // A basic conversation
  basicConversation(clients, groups, id = "0")

  // All users except idxGhosts (Dan) update their keys
  val idxCommit = 1
  val idxUpdate = IntRange(0, clients.size-1).toList()
  val idxGhosts = listOf(clients.size-1)
  for(i in 0..<GroupState.INACTIVITY_DELAY.toInt()) {
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

  // A basic conversation (except for idxGhosts - Dan)
  basicConversation(clients, groups, clients.slice(idxGhosts), id = "1")

  // Dan reconnecting after their quarantine
  println("GHOST RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  // Dan sends QuarantineEnd message to all group members in order to retrieve shares
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

  // When a user commits here, it sends to the recovering ghost user a
  // WelcomeBackGhostMessage with the new groupContext for this epoch, since
  // the ghost asked for it
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients,
  )

  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  printGroups(groups, clients, clientsList)

  // A basic conversation
  basicConversation(clients, groups, id = "3")

  updateKeys(
    groups,
    clients,
    clientsList,
    updaterGroupsIdx = idxUpdate.filter{ !idxGhosts.contains(it) },
    committerGroupIdx = idxCommit,
  )

  // At this stage, the ghost user have recovered enough shares to reconstruct its ghost key
  // and decrypt all of the messages cached during its quarantine period
  println("RECOVERING MESSAGES TEST")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[clients.size-1].startGhostMessageRecovery().getOrThrow()
  clients[clients.size-1].processCachedGhostMessages().getOrThrow()
  groups[clients.size-1].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  updateKeys(
    groups,
    clients,
    clientsList,
    updaterGroupsIdx = clients.indices.toList(),
    committerGroupIdx = 0
  )

  printGroups(groups, clients, clientsList)

  DeliveryService.empty()
}


suspend fun main() {
  test1_2()
}
