package com.github.traderjoe95.mls.demo

import com.github.traderjoe95.mls.demo.util.initiateGroup
import com.github.traderjoe95.mls.demo.util.updateKeys
import com.github.traderjoe95.mls.protocol.group.GroupState
import org.bouncycastle.pqc.legacy.math.linearalgebra.IntegerFunctions.pow
import java.security.SecureRandom

//////////////////////////////////////////////////////////////////////
// QUARANTINE TEST: testing horizontal method
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val NB_MEMBERS = pow(2,5)
  val NB_GHOSTS = 3
  val clientsList = mutableListOf<String>()

  for(i in 1..NB_MEMBERS){
    clientsList.add(i.toString())
  }

  val rand = SecureRandom()
  val idxGhosts = mutableListOf<Int>()
  for(i in 1..NB_GHOSTS){
    idxGhosts.add(rand.nextInt(clientsList.size))
  }

  val (clients, groups) = initiateGroup(clientsList)

  var idxCommitter = rand.nextInt(clients.size)
  while(idxGhosts.contains(idxCommitter)) idxCommitter = rand.nextInt(clients.size)
  println("idxGhosts = " + idxGhosts)
  idxGhosts.forEach { clients[it].declareAsGhost() }

  for(i in 0..<GroupState.INACTIVITY_DELAY.toInt()){
    updateKeys(
      groups,
      clients,
      clientsList,
      idxCommitter
    )
  }


//  printGroups(groups, clientsList, idxGhosts)
}
