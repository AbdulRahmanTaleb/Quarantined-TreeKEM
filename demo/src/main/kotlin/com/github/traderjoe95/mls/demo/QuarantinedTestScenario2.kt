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
// QUARANTINE TEST: user becomes ghost, passes the maximum
// inactivity delay and gets deleted
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
  for(i in 0..4) {
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

  for(i in 0..14) {
    updateKeys(
      groups,
      clients,
      clientsList,
      i,
      idxUpdate,
      idxCommit,
      idxGhosts
    )
  }

  println("GHOST RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  try {
    println("\n---------------------------- Sending Quarantine End")
    clients[idxGhosts[0]].ghostReconnect(groups[idxGhosts[0]].groupId)
    clients.filterIndexed{idx, _ -> !idxGhosts.contains(idx)}.forEach{
      it.processNextMessage().getOrThrow()
    }

    println("\n---------------------------- Receiving Share Recovery Message")
    for(k in 0..clients.size-2){
      clients.forEach {
        it.processNextMessage().getOrThrow()
      }
    }

    println("SOMETHING IS WRONG, TEST SHOULD END WITH EXCEPTION SINCE GHOST LEAF IS DELETED AND CAN'T RECONNECT ANYMORE")
  }  catch (exception: IllegalStateException) {
    println("+++ Test throws exception as expected since ghost leaf trying to reconnect is already deleted from the group, exception says: " + exception.message)
    println("Test Success !")
  }






}
