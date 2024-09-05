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

fun printGroups(clients: List<Client>, groups: List<ActiveGroupClient<String>>, clientsList: List<String>){

  var notGhost = 0
  while(clients[notGhost].isGhost()) notGhost++

  println("Updated view of Group:")
  printGroup(groups[notGhost])

  groups.forEachIndexed { idx, group ->
    if(clients[idx].isGhost()){
      println("Ghost " + clientsList[idx] + " view of Group:")
      printGroup(groups[idx])
    }
    else{
      if(!compareGroups(groups[notGhost], group)){
        throw(IllegalStateException("Incoherent views of group among members"))
      }
    }
  }

  groups.forEachIndexed { idx, it ->
    it.state.groupGhostInfo.printRanks(clientsList[idx])
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

suspend fun initiateGroup(clientsList: List<String>, update: Boolean = true): Pair<List<Client>, List<ActiveGroupClient<String>>> {
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
  println("ADDING OTHERS TO THE GROUP")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  val otherKeyPackages = IntRange(2, clientsList.size - 1).map{
    clients[0].getKeyPackageFor(Config.cipherSuite, clientsList[it])
  }
  val proposals = otherKeyPackages.map{ Add(it) }

  val otherAddMembersCommit = groups[0].commit(proposals).getOrThrow()
  DeliveryService.sendMessageToGroup(otherAddMembersCommit, groups[0].groupId).getOrThrow()
  clients.subList(0, 2).forEach { it.processNextMessage().getOrThrow() }

  IntRange(2, clientsList.size - 1).forEach {
    print(it.toString() + ", ")
    if(it % 50 == 0){ println("") }
    groups.add(clients[it].processNextMessage().getOrThrow()!! as ActiveGroupClient<String>)
  }
  println("")

  println("COMMIT DONE BY " + clientsList[0].uppercase())
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  if(update){
    updateKeys(groups, clients, clientsList, 0)
  }

  printGroups(clients, groups, clientsList)

  return Pair(clients, groups)
}

suspend fun updateKeys(groups: List<ActiveGroupClient<String>>, clients: List<Client>, clientsList: List<String>, committerIdx: Int, excludeFromUpdate: List<Int> = listOf() ,noCommit: Boolean = false){

  println("UPDATING " + clientsList.slice(clients.indices.filter{ !clients[it].isGhost() && it!=committerIdx && !excludeFromUpdate.contains(it)}).map { it.uppercase() } + " KEYS, COMMITTING BY " + clientsList[committerIdx].uppercase())
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  clients.indices.forEach { updaterGroup ->
    if((updaterGroup != committerIdx) && (!clients[updaterGroup].isGhost()) && !(excludeFromUpdate.contains(updaterGroup))){
      val updateMember = groups[updaterGroup].update().getOrThrow()
      DeliveryService.sendMessageToGroup(updateMember, groups[updaterGroup].groupId).getOrThrow()

      clients.forEach { client ->
        client.processNextMessage().getOrThrow()
      }
    }
  }

  if(!noCommit){
    commit(groups[committerIdx], clients)
  }
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
}

suspend fun commit(committerGroup: ActiveGroupClient<String>, clients: List<Client>){

  val commitMsg: ByteArray
  val time = measureTime {
    commitMsg = committerGroup.commit().getOrThrow()
  }
  println("Construction of commit message (" + commitMsg.size + " bytes) took " + time.inWholeMilliseconds + " ms")

  DeliveryService.sendMessageToGroup(commitMsg, committerGroup.groupId).getOrThrow()

  clients.forEach {
    it.processNextMessage().getOrThrow()
  }
}

suspend fun updateKeysWithoutProcessing(groups: List<ActiveGroupClient<String>>, clients: List<Client>, clientsList: List<String>, committerIdx: Int, excludeFromUpdate: List<Int> = listOf()){

  println("UPDATING " + clientsList.slice(clients.indices.filter{ !clients[it].isGhost() && it!=committerIdx && !excludeFromUpdate.contains(it)}).map { it.uppercase() } + " KEYS, COMMITTING BY " + clientsList[committerIdx].uppercase())
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  clients.indices.forEach { updaterGroup ->
    if((updaterGroup != committerIdx) && (!clients[updaterGroup].isGhost()) && !(excludeFromUpdate.contains(updaterGroup))){
      val updateMember = groups[updaterGroup].update().getOrThrow()
      DeliveryService.sendMessageToGroup(updateMember, groups[updaterGroup].groupId).getOrThrow()
    }
  }

  commitWithoutProcessing(groups, clients, committerIdx)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
}

suspend fun commitWithoutProcessing(groups: List<ActiveGroupClient<String>>, clients: List<Client>, committerIdx: Int){

  val committerGroup = groups[committerIdx]
  val committerClient = clients[committerIdx]
  while(!committerClient.noMessagesToProcess()){
    committerClient.processNextMessage().getOrThrow()
  }

  val commitMsg: ByteArray
  val time = measureTime {
    commitMsg = committerGroup.commit().getOrThrow()
  }
  println("Construction of commit message (" + commitMsg.size + " bytes) took " + time.inWholeMilliseconds + " ms")

  DeliveryService.sendMessageToGroup(commitMsg, committerGroup.groupId).getOrThrow()
}

suspend fun basicConversation(clients: List<Client>, groups: List<ActiveGroupClient<String>>, id: String){
  println("SENDING MESSAGE")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  for(i in 0..<clients.size){
    if(!clients[i].isGhost()){
      clients[i].sendMessage(groups[i].groupId, "Hello from " + clients[i].userName.uppercase() + " " + id)

      clients.forEach {
        it.processNextMessage().getOrThrow()
      }
    }
  }
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")
}

suspend fun recoverCachedMessaes(client: Client, group: ActiveGroupClient<String>, clientName: String){
  println("$clientName RECOVERING CACHED MESSAGES")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  group.startGhostMessageRecovery().getOrThrow()
  client.processCachedGhostMessages().getOrThrow()
  group.endGhostMessageRecovery().getOrThrow()
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")
}
