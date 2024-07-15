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
// QUARANTINE TEST: user becomes ghost, passes the maximum
// inactivity delay and gets deleted
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

  // Doing many commits so that the ghost user stays inactive
  // long enough to be deleted from the group
  for(i in 0..<GroupState.DELETE_FROM_QUARANTINE_DELAY.toInt()) {
    updateKeys(
      groups,
      clients,
      clientsList,
      idxCommit,
      idxGhosts
    )
  }

  println("GHOST " + clientsList[idxGhosts[0]] +  " RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhosts[0]].ghostReconnect(groups[idxGhosts[0]].groupId)

  var error = true
  clients.filterIndexed{idx, _ -> !idxGhosts.contains(idx)}.forEach {
    try {
      it.processNextMessage().getOrThrow()
      println("SOMETHING IS WRONG, TEST SHOULD END WITH EXCEPTION SINCE GHOST LEAF IS DELETED AND CAN'T RECONNECT ANYMORE")
      error = false
    }  catch (exception: IllegalStateException) {
      println("+++ Test throws exception as expected since ghost leaf trying to reconnect is already deleted from the group, exception says: " + exception.message)
    }
  }

  if(error){
    println("Test Success !")
  }

  // A basic conversation
  basicConversation(clients, groups, idxGhosts, id = "2")
  printGroups(groups, clientsList, idxGhosts)






}
