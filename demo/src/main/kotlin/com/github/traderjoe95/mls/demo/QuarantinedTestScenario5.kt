package com.github.traderjoe95.mls.demo

import com.github.traderjoe95.mls.demo.util.initiateGroup
import com.github.traderjoe95.mls.demo.util.updateKeysWithoutProcessing
import com.github.traderjoe95.mls.protocol.group.GroupState
import org.bouncycastle.pqc.legacy.math.linearalgebra.IntegerFunctions.pow
import java.security.SecureRandom
import javax.swing.GroupLayout.Group

//////////////////////////////////////////////////////////////////////
// QUARANTINE TEST: testing group with many members
//////////////////////////////////////////////////////////////////////
suspend fun main() {
  val NB_MEMBERS = pow(2,10)
  val NB_GHOSTS = 2
  GroupState.setGhostParams(30u, 30u, 30u)
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

  var idxCommitter = rand.nextInt(clients.size)
  while(idxGhosts.contains(idxCommitter)) idxCommitter = rand.nextInt(clients.size)
  updateKeysWithoutProcessing(
    groups,
    clients,
    clientsList,
    idxCommitter,
  )

  groups[0].state.tree.print()

  var sizeCommitEmpty: Int = 0
  var timeCommitEmpty: Long = 0

  val nbIter = GroupState.INACTIVITY_DELAY.toInt()-2
  for(i in 0..<nbIter){
    idxCommitter = rand.nextInt(clients.size)
    while(idxGhosts.contains(idxCommitter)) idxCommitter = rand.nextInt(clients.size)
    updateKeysWithoutProcessing(
      groups,
      clients,
      clientsList,
      idxCommitter,
      excludeFromUpdate = groups.indices.toList()
    ).let{
      sizeCommitEmpty += it.first
      timeCommitEmpty += it.second
    }
  }

  idxCommitter = rand.nextInt(clients.size)
  while(idxGhosts.contains(idxCommitter)) idxCommitter = rand.nextInt(clients.size)
  val (sizeCommitGhost, timeCommitGhost) =
    updateKeysWithoutProcessing(
      groups,
      clients,
      clientsList,
      idxCommitter,
      excludeFromUpdate = groups.indices.toList()
    )

  println("")
  println("Average for empty commit (over " + nbIter + " iterations):")
  println("size: " + sizeCommitEmpty/nbIter + " bytes")
  println("time: " + timeCommitEmpty/nbIter + " ms")

  println("")
  println("1 commit with " + NB_GHOSTS + " ghost(s):")
  println("size: " + sizeCommitGhost + " bytes")
  println("time: " + timeCommitGhost + " ms")
}
