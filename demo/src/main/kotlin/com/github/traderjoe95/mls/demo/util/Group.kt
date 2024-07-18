package com.github.traderjoe95.mls.demo.util


import com.github.traderjoe95.mls.demo.Config
import com.github.traderjoe95.mls.demo.client.Client
import com.github.traderjoe95.mls.demo.getOrThrow
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.group.GroupState
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import java.lang.IllegalStateException
import kotlin.time.measureTime

fun ActiveGroupClient<String>.makePublic() {
  DeliveryService.storeGroupInfo(groupInfo().getOrThrow())
}

fun printGroups(groups: List<ActiveGroupClient<String>>, clientsList: List<String>, idxGhosts: List<Int> = listOf()){

  var notGhost = 0
  while(idxGhosts.contains(notGhost)) notGhost++

  println("Updated view of Group:")
  printGroup(groups[notGhost])

  groups.forEachIndexed { idx, group ->
    if(idxGhosts.contains(idx)){
      println("Ghost " + clientsList[idx] + " view of Group:")
      printGroup(groups[idx])
    }
    else{
      if(!compareGroups(groups[notGhost], group)){
        throw(IllegalStateException("Incoherent views of group among members"))
      }
    }

    println(clientsList[idx] + ":")
    group.state.groupGhostInfo.printRanks()
    println()


  }
}

fun printGroup(group: ActiveGroupClient<String>){
  println("EPOCH " + group.state.epoch + ": ")

  println("================================================================")
  for(i in 0..<group.state.members.size) {
    when(val credential = group.state.members[i].credential){
      is BasicCredential -> println("Leaf[" + i + "] - " + credential.identity.decodeToString() + ": source = " + group.state.members[i].source + ", epk = " + group.state.members[i].epk + " , equar = " + group.state.members[i].equar)
      else -> println("Leaf[" + i + "]: source = " + group.state.members[i].source + ", epk = " + group.state.members[i].epk + " , equar = " + group.state.members[i].equar)
    }

  }
  println("================================================================")
  println()
}

fun compareGroups(group1: ActiveGroupClient<String>, group2: ActiveGroupClient<String>): Boolean{

  if(group1.state.members.size != group2.state.members.size) return false

  for(i in 0..<group1.state.members.size) {
    val member1 = group1.state.members[i]
    val member2 = group2.state.members[i]

    if(member1 != member2){
      return false
    }
  }

  return true
}

suspend fun initiateGroup(clientsList: List<String>): Pair<List<Client>, List<ActiveGroupClient<String>>> {
  val clients = mutableListOf<Client>()
  val groups = mutableListOf<ActiveGroupClient<String>>()

  for(cl in clientsList){
    val client = Client(cl)
    clients.add(client)
    client.generateKeyPackages(10U)
  }

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // GROUP CREATION BY ALICE
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("CREATING GROUP")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  groups.add(clients[0].createGroup().getOrThrow())
  printGroup(groups[0])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // ADDING BOB TO THE GROUP
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("ADDING " + clientsList[1].uppercase() + " TO THE GROUP")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  val keyPackage = clients[0].getKeyPackageFor(Config.cipherSuite, clientsList[1])
  val addMember = groups[0].addMember(keyPackage).getOrThrow()
  DeliveryService.sendMessageToGroup(addMember, groups[0].groupId).getOrThrow()

  clients[0].processNextMessage().getOrThrow()

  val addCommit = groups[0].commit().getOrThrow()
  DeliveryService.sendMessageToGroup(addCommit, groups[0].groupId).getOrThrow()

  clients[0].processNextMessage().getOrThrow()

  // Bob processes the Welcome and bootstraps his state
  groups.add(clients[1].processNextMessage().getOrThrow()!! as ActiveGroupClient<String>)

//  printGroup(groups[0], clientsList[0].uppercase(), 2)
//  printGroup(groups[1], clientsList[1].uppercase(), 2)

  println("Commit done by "+ clientsList[0].uppercase() +".")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // ADDING OTHERS TO THE GROUP
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  for(i in 2..<clients.size){
    println("ADDING " + clientsList[i].uppercase() + " TO THE GROUP")
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    val otherKeyPackage = clients[i-1].getKeyPackageFor(Config.cipherSuite, clientsList[i])

    if((i % GroupState.INACTIVITY_DELAY.toInt()) < 1){
      println("updating " + (i % GroupState.INACTIVITY_DELAY.toInt()))
      for(j in 0..i-2){
        val updateMember = groups[j].update().getOrThrow()
        DeliveryService.sendMessageToGroup(updateMember, groups[j].groupId).getOrThrow()
        clients.forEach {client ->
          client.processNextMessage().getOrThrow()
        }
      }
    }

    val otherAddMemberCommit = groups[i-1].commit(listOf(Add(otherKeyPackage))).getOrThrow()
    DeliveryService.sendMessageToGroup(otherAddMemberCommit, groups[i-1].groupId).getOrThrow()

    clients.subList(0, i).forEach { it.processNextMessage().getOrThrow() }

    groups.add(clients[i].processNextMessage().getOrThrow()!! as ActiveGroupClient<String>)

//    for(j in 0..i) {
//      printGroup(groups[j], clientsList[j].uppercase(), i+1)
//    }

    println("COMMIT DONE BY " + clientsList[i-1].uppercase())
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    println()
  }

  updateKeys(groups, clients, clientsList, 0)

  printGroups(groups, clientsList)

  return Pair(clients, groups)
}

suspend fun updateKeys(groups: List<ActiveGroupClient<String>>, clients: List<Client>, clientsList: List<String>, committerIdx: Int, excludeClients: List<Int> = listOf(), excludeClientsOnlyFromUpdate: List<Int> = listOf(), noCommit: Boolean = false){

  println("UPDATING " + clientsList.slice(clients.indices.filter{ !excludeClients.contains(it) && it!=committerIdx}).map { it.uppercase() } + " KEYS, COMMITTING BY " + clientsList[committerIdx].uppercase())
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  clients.indices.forEach { updaterGroup ->
    if((updaterGroup != committerIdx) && (!excludeClients.contains(updaterGroup)) && (!excludeClientsOnlyFromUpdate.contains(updaterGroup))){
      val updateMember = groups[updaterGroup].update().getOrThrow()
      DeliveryService.sendMessageToGroup(updateMember, groups[updaterGroup].groupId).getOrThrow()

      clients.forEachIndexed {idx, client ->
        if (!excludeClients.contains(idx)) {
          client.processNextMessage().getOrThrow()
        }
      }
    }
  }

  if(!noCommit){
    commit(groups[committerIdx], clients, excludeClients.map{ clients[it] })
  }
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
}

suspend fun commit(committerGroup: ActiveGroupClient<String>, clients: List<Client>, excludeClients: List<Client> = listOf()){

  val commitMsg: ByteArray
  val time = measureTime {
    commitMsg = committerGroup.commit().getOrThrow()
  }
  println("Construction of commit message took " + time.inWholeMilliseconds + " ms")

  DeliveryService.sendMessageToGroup(commitMsg, committerGroup.groupId).getOrThrow()

  clients.forEach {
    if(! excludeClients.contains(it)){
      it.processNextMessage().getOrThrow()
    }
  }
}

suspend fun basicConversation(clients: List<Client>, groups: List<ActiveGroupClient<String>>, excludeClients: List<Int> = listOf(), id: String){
  println("SENDING MESSAGE")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  for(i in 0..<clients.size){
    if(! excludeClients.contains(i)){
      clients[i].sendMessage(groups[i].groupId, "Hello from " + clients[i].userName.uppercase() + " " + id)
      for(j in 0..<clients.size){
        if((j != i) && (! excludeClients.contains(j))){
          clients[j].processNextMessage()
        }
      }
    }
  }
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")
}
