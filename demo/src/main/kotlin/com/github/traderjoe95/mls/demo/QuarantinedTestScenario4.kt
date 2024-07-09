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


//////////////////////////////////////////////////////////////////////
// QUARANTINE TEST:
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val clientsList = listOf("a", "b", "c", "d", "e", "f")

  val (clients, groups) = initiateGroup(clientsList)

  println("HEREEEEEEEE +++++++++++++++++++++++++++++++++++++++++++++++++++")

//  groups[0].tree.print()

  for(idxCommit in 0..3){
    updateKeys(
      groups,
      clients,
      clientsList,
      i = idxCommit,
      updaterGroupsIdx = listOf(4),
      committerGroupIdx =  idxCommit,
    )
  }

//  updateKeys(
//    groups,
//    clients,
//    clientsList,
//    i = 3,
//    updaterGroupsIdx = listOf(3),
//    committerGroupIdx =  4,
//  )

  groups[0].tree.print()

//  updateKeys(
//    groups,
//    clients,
//    clientsList,
//    i = 4,
//    updaterGroupsIdx = listOf(3),
//    committerGroupIdx = 4,
//    excludeClients = listOf(5)
//  )
}
