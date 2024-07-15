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
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import org.bouncycastle.pqc.legacy.math.linearalgebra.IntegerFunctions.pow
import java.security.SecureRandom
import kotlin.time.measureTime

//////////////////////////////////////////////////////////////////////
// QUARANTINE TEST: testing horizontal method
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val clientsList = mutableListOf<String>()

  for(i in 1..pow(2, 7)){
    clientsList.add(i.toString())
  }

  val rand = SecureRandom()
  val idxGhosts = mutableListOf<Int>()

  for(i in 1..2){
    idxGhosts.add(rand.nextInt(clientsList.size))
  }
  println("idxGhosts = " + idxGhosts)


  val (clients, groups) = initiateGroup(clientsList)

  var idxCommitter = rand.nextInt(clients.size)
  while(idxGhosts.contains(idxCommitter)) idxCommitter = rand.nextInt(clients.size)

  for(i in 0..<GroupState.INACTIVITY_DELAY.toInt()){
    updateKeys(
      groups,
      clients,
      clientsList,
      idxCommitter,
      idxGhosts,
    )
  }


//  printGroups(groups, clientsList, idxGhosts)
}
