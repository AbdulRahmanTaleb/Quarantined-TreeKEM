package com.github.traderjoe95.mls.demo.util

import com.github.traderjoe95.mls.demo.Config
import com.github.traderjoe95.mls.demo.client.Client
import com.github.traderjoe95.mls.demo.getOrThrow
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.framing.content.Add

fun ActiveGroupClient<String>.makePublic() {
  DeliveryService.storeGroupInfo(groupInfo().getOrThrow())
}

fun printGroups(groups: List<ActiveGroupClient<String>>, clients: List<Client>, clientsList: List<String>){
  for(j in clients.indices) {
    printGroup(groups[j], clientsList[j], clients.size)
  }
}

fun printGroup(group: ActiveGroupClient<String>, name: String, nbMembers: Int){
  println(name + " EPOCH " + group.state.epoch + ": ")

  println("================================================================")
  for(i in 0..<nbMembers) {
    when(val credential = group.state.members[i].credential){
      is BasicCredential -> println("Leaf[" + i + "] - " + credential.identity.decodeToString() + ": source = " + group.state.members[i].source + ", epk = " + group.state.members[i].epk + " , equar = " + group.state.members[i].equar)
      else -> println("Leaf[" + i + "]: source = " + group.state.members[i].source + ", epk = " + group.state.members[i].epk + " , equar = " + group.state.members[i].equar)
    }

  }
  println("================================================================")
  println()
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
  printGroup(groups[0], clientsList[0].uppercase(), 1)
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

  printGroup(groups[0], clientsList[0].uppercase(), 2)
  printGroup(groups[1], clientsList[1].uppercase(), 2)

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

    for(j in 0..i-2){
      val updateMember = groups[j].update().getOrThrow()
      DeliveryService.sendMessageToGroup(updateMember, groups[j].groupId).getOrThrow()
      clients.forEach {client ->
        client.processNextMessage().getOrThrow()
      }
    }

    val otherAddMemberCommit = groups[i-1].commit(listOf(Add(otherKeyPackage))).getOrThrow()
    DeliveryService.sendMessageToGroup(otherAddMemberCommit, groups[i-1].groupId).getOrThrow()

    clients.subList(0, i).forEach { it.processNextMessage().getOrThrow() }

    groups.add(clients[i].processNextMessage().getOrThrow()!! as ActiveGroupClient<String>)

    for(j in 0..i) {
      printGroup(groups[j], clientsList[j].uppercase(), i+1)
    }

    println("COMMIT DONE BY " + clientsList[i-1].uppercase())
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    println()
  }

  return Pair(clients, groups)
}

suspend fun updateKeys(groups: List<ActiveGroupClient<String>>, clients: List<Client>, clientsList: List<String>, i:Int, updaterGroupsIdx: List<Int>, committerGroupIdx: Int, excludeClients: List<Int> = listOf(), noCommit: Boolean = false){

  println(i.toString() + ": UPDATING " + clientsList.slice(updaterGroupsIdx.filter{ !excludeClients.contains(it) && it!=committerGroupIdx}).map { it.uppercase() } + " KEYS, COMMITTING BY " + clientsList[committerGroupIdx].uppercase())
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  updaterGroupsIdx.forEach { updaterGroup ->
    if((updaterGroup != committerGroupIdx) && (!excludeClients.contains(updaterGroup))){
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
    commit(groups[committerGroupIdx], clients, excludeClients.map{ clients[it] })
  }
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
}

suspend fun commit(committerGroup: ActiveGroupClient<String>, clients: List<Client>, excludeClients: List<Client> = listOf()){
  val commitMsg = committerGroup.commit().getOrThrow()
  DeliveryService.sendMessageToGroup(commitMsg, committerGroup.groupId).getOrThrow()

  clients.forEach {
    if(! excludeClients.contains(it)){
      it.processNextMessage().getOrThrow()
    }
  }
}

suspend fun basicConversation(clients: List<Client>, groups: List<ActiveGroupClient<String>>, excludeClients: List<Client> = listOf(), id: String){
  println("SENDING MESSAGE")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  for(i in 0..<clients.size){
    if(! excludeClients.contains(clients[i])){
      clients[i].sendMessage(groups[i].groupId, "Hello from " + clients[i].userName.uppercase() + " " + id)
      for(j in 0..<clients.size){
        if((j != i) && (! excludeClients.contains(clients[j]))){
          clients[j].processNextMessage()
        }
      }
    }
  }
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")
}
