package com.github.traderjoe95.mls.demo

import arrow.core.raise.recover
import com.github.traderjoe95.mls.demo.util.basicConversation
import com.github.traderjoe95.mls.demo.util.commit
import com.github.traderjoe95.mls.demo.util.initiateGroup
import com.github.traderjoe95.mls.demo.util.printGroups
import com.github.traderjoe95.mls.demo.util.recoverCachedMessaes
import com.github.traderjoe95.mls.demo.util.updateKeys
import com.github.traderjoe95.mls.protocol.group.GroupState


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
  val idxGhost1 = clientsList.indexOf("Gregory")
  idxGhost1.let { clients[it].declareAsGhost() }
  // Updating keys for all users except Ghost1 so that it can become ghost first
  for(i in 0..< GroupState.INACTIVITY_DELAY.toInt()) {
    updateKeys(
      groups,
      clients,
      clientsList,
      idxCommit
    )
  }
  printGroups(clients, groups, clientsList)

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

  // So Charlie holds the second share with shareholder rank = 1, and Dan the second share with rank = 2
  // and Eve holds the third share with shareholder rank = 1, and Fred the third share with rank = 2
  // In the following, we will transform Eve and Charlie into ghosts, so when Gregory reconnects,
  // they cannot retrieve the second and third share by asking for shareholder rank = 1, since
  // Eve and Charlie are not connected, and they
  // need to send another request with shareholder rank = 2
  // Since Gregory needs at least two shares (m=3, t=2 in this scenario), having at least two shares suffices


  // Second and third users to become ghost -> Eve & Charlie
  val idxGhost2 = clientsList.indexOf("Eve")
  val idxGhost3 = clientsList.indexOf("Charlie")
  val idxGhosts = listOf(idxGhost1, idxGhost2, idxGhost3)
  idxGhosts.forEach { clients[it].declareAsGhost() }
  for(i in 0..<GroupState.INACTIVITY_DELAY.toInt()) {
    updateKeys(
      groups,
      clients,
      clientsList,
      idxCommit
    )
  }
  printGroups(clients, groups, clientsList)
  basicConversation(clients, groups, id = "1")

  // Gregory reconnecting
  println("GHOST RECONNECTING: " + clientsList[idxGhost1])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhost1].ghostReconnect(groups[idxGhost1].groupId)
  clients.forEach{
    it.processNextMessage().getOrThrow()
  }
  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in 0..<clients.size){
    clients.forEach {
      it.processNextMessage().getOrThrow()
    }
  }
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients
  )

  // Since Eve and Charlie holds a share with shareholder rank = 1 for Gregory,
  // Gregory has to ask for the same share with shareholder rank = 2,
  // since Eve and Charlie are now ghosts and couldn't respond to Ghost1's request
  if(!clients[idxGhost1].retrievedEnoughShares(groups[idxGhost1].groupId)){
    println("\n---------------------------- Sending ADDITIONAL Share Resend Message")
    clients[idxGhost1].sendShareResend(groups[idxGhost1].groupId)
    clients.forEach{
      it.processNextMessage().getOrThrow()
    }
    println("\n---------------------------- Receiving ADDITIONAL Share Recovery Message")
    for(i in 0..clients.size){
      clients.forEach {
        it.processNextMessage().getOrThrow()
      }
    }
  }
  // At this stage, Gregory have recovered enough shares to reconstruct its ghost key
  // and decrypt all of the messages cached during its quarantine period
  recoverCachedMessaes(clients[idxGhost1], groups[idxGhost1], clientsList[idxGhost1])

  printGroups(clients, groups, clientsList)
  basicConversation(clients, groups, id = "2")

  // Eve reconnecting
  println("GHOST RECONNECTING: " + clientsList[idxGhost2])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
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
  for(k in clients.indices){
    clients.forEach {
      it.processNextMessage().getOrThrow()
    }
  }
  if(!clients[idxGhost2].retrievedEnoughShares(groups[idxGhost2].groupId)){
    println("\nSOMETHING IS WRONG")
    return
  }
  idxCommit = clientsList.indexOf("Gregory")
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients
  )
  // The ghost user processes the WelcomeBackGhost Message
  clients[idxGhost2].processNextMessage().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  // At this stage, Eve has recovered enough shares to reconstruct their ghost key
  // and decrypt all of the messages cached during its quarantine period
  recoverCachedMessaes(clients[idxGhost2], groups[idxGhost2], clientsList[idxGhost2])


  // Charlie reconnecting
  println("GHOST RECONNECTING: " + clientsList[idxGhost3])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhost3].ghostReconnect(groups[idxGhost3].groupId)
  clients.forEach{
    it.processNextMessage().getOrThrow()
  }
  clients[idxGhost3].requestWelcomeBackGhost(groups[idxGhost3].groupId)
  clients.forEach{
    it.processNextMessage().getOrThrow()
  }
  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in clients.indices){
    clients.forEach {
      it.processNextMessage().getOrThrow()
    }
  }
  if(!clients[idxGhost3].retrievedEnoughShares(groups[idxGhost3].groupId)){
    println("\nSOMETHING IS WRONG")
    return
  }
  idxCommit = clientsList.indexOf("Eve")
  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients
  )
  // The ghost user processes the WelcomeBackGhost Message
  clients[idxGhost3].processNextMessage().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  printGroups(clients, groups, clientsList)
  basicConversation(clients, groups, id = "3")

  // At this stage, Charlie has recovered enough shares to reconstruct their ghost key
  // and decrypt all of the messages cached during its quarantine period
  recoverCachedMessaes(clients[idxGhost3], groups[idxGhost3], clientsList[idxGhost3])


}
