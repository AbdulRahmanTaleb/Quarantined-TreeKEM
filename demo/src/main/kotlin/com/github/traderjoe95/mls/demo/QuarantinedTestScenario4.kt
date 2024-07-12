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
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.framing.content.Add


suspend fun test1(){
  val clientsList = listOf("a", "b", "c", "d", "e", "f")

  val (clients, groups) = initiateGroup(clientsList)

  for(idxCommit in 0..3){
    updateKeys(
      groups,
      clients,
      clientsList,
      updaterGroupsIdx = listOf(4),
      committerGroupIdx =  idxCommit,
    )
  }

  groups[0].tree.print()

  val idxGhost = 5

  updateKeys(
    groups,
    clients,
    clientsList,
    updaterGroupsIdx = listOf(),
    committerGroupIdx = 4,
    excludeClients = listOf(idxGhost)
  )

  basicConversation(clients, groups, clients.slice(listOf(idxGhost)), id = "1")

  for(i in 0..3){
    updateKeys(
      groups,
      clients,
      clientsList,
      updaterGroupsIdx = listOf(0,1,2,3),
      committerGroupIdx =  4,
      excludeClients =  listOf(idxGhost)
    )
  }

  println("GHOST RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println("\n---------------------------- Sending Quarantine End")
  clients[idxGhost].ghostReconnect(groups[idxGhost].groupId)
  clients.filterIndexed{idx, _ -> idx != idxGhost}.forEach{
    it.processNextMessage().getOrThrow()
  }
  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in 0..clients.size-1){
    clients.forEach {
      it.processNextMessage().getOrThrow()
    }
  }
  println("\n---------------------------- Committing by " + clientsList[4])
  commit(
    groups[4],
    clients,
  )
  clients[idxGhost].processNextMessage().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  println("RECOVERING MESSAGES TEST")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups[idxGhost].startGhostMessageRecovery().getOrThrow()
  clients[idxGhost].processCachedGhostMessages().getOrThrow()
  groups[idxGhost].endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  basicConversation(clients, groups, id = "2")
}

//////////////////////////////////////////////////////////////////////
// QUARANTINE TEST: testing horizontal method
//////////////////////////////////////////////////////////////////////
suspend fun main() {
 test1()
}
