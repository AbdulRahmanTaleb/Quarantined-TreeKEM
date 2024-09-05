package com.github.traderjoe95.mls.demo

import com.github.traderjoe95.mls.demo.util.initiateGroup
import com.github.traderjoe95.mls.demo.util.updateKeys
import com.github.traderjoe95.mls.demo.util.updateKeysWithoutProcessing
import com.github.traderjoe95.mls.protocol.group.GroupState
import org.bouncycastle.pqc.legacy.math.linearalgebra.IntegerFunctions.pow
import java.security.SecureRandom

//////////////////////////////////////////////////////////////////////
// QUARANTINE TEST: testing group with many members
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val NB_MEMBERS = pow(2,7)
  val NB_GHOSTS = 2
  val clientsList = mutableListOf<String>()

  for(i in 1..NB_MEMBERS){
    clientsList.add(i.toString())
  }
  val (clients, groups) = initiateGroup(clientsList, update=false)

  val rand = SecureRandom()
  val idxGhosts = mutableListOf<Int>()
  for(i in 1..NB_GHOSTS){
    var elem = rand.nextInt(clientsList.size)
    while(idxGhosts.contains(elem)) elem = rand.nextInt(clientsList.size)
    idxGhosts.add(elem)
  }
  println("idxGhosts = " + idxGhosts)
  idxGhosts.forEach { clients[it].declareAsGhost() }

  for(i in 0..<GroupState.INACTIVITY_DELAY.toInt()){
    var idxCommitter = rand.nextInt(clients.size)
    while(idxGhosts.contains(idxCommitter)) idxCommitter = rand.nextInt(clients.size)
    updateKeysWithoutProcessing(
      groups,
      clients,
      clientsList,
      idxCommitter,
      excludeFromUpdate = if(i != 0) groups.indices.toList() else listOf()
    )

    if(i == 0) groups[0].tree.print()

//    if(i == GroupState.INACTIVITY_DELAY.toInt()-2){
////      println("idxCommitter = " + idxCommitter)
//      println(groups[idxCommitter].tree.print())
//    }
  }


//  printGroups(groups, clientsList, idxGhosts)
}
