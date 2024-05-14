package com.github.traderjoe95.mls.demo

import com.github.traderjoe95.mls.demo.client.Client
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.demo.util.makePublic
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.X509Credential
import com.github.traderjoe95.mls.protocol.types.crypto.Secret.Companion.asSecret
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import com.github.traderjoe95.mls.protocol.types.framing.content.Update
import com.github.traderjoe95.mls.protocol.util.debug

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

suspend fun updateKeys(updaterGroups: List<ActiveGroupClient<String>>, clients: MutableList<Client>, excludeClients: MutableList<Client> = mutableListOf()){

  updaterGroups.forEach { updaterGroup ->
    val updateMember = updaterGroup.update().getOrThrow()
    DeliveryService.sendMessageToGroup(updateMember, updaterGroup.groupId).getOrThrow()

    clients.forEach {
      if (!excludeClients.contains(it)) {
        it.processNextMessage().getOrThrow()
      }
    }
  }
}

suspend fun commit(committerGroup: ActiveGroupClient<String>, clients: MutableList<Client>, excludeClients: MutableList<Client> = mutableListOf()){
  val commitMsg = committerGroup.commit().getOrThrow()
  DeliveryService.sendMessageToGroup(commitMsg, committerGroup.groupId).getOrThrow()

  clients.forEach {
    if(! excludeClients.contains(it)){
      it.processNextMessage().getOrThrow()
    }
  }
}

suspend fun main() {
  val clients = mutableListOf<Client>()
  val groups = mutableListOf<ActiveGroupClient<String>>()

  val clientsList = listOf("Alice", "Bob", "Charlie", "Dan")

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
  printGroup(groups[0], "ALICE", 1)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // ADDING BOB TO THE GROUP
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("ADDING Bob TO THE GROUP")
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

  printGroup(groups[0], "ALICE", 2)
  printGroup(groups[1], "BOB", 2)

  println("Commit done by Alice.")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // ADDING OTHERS TO THE GROUP
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  for(i in 2..<clients.size){
    println("ADDING " + clientsList[i] + " TO THE GROUP")
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    val otherKeyPackage = clients[i-1].getKeyPackageFor(Config.cipherSuite, clientsList[i])
    val otherAddMemberCommit = groups[i-1].commit(listOf(Add(otherKeyPackage))).getOrThrow()
    DeliveryService.sendMessageToGroup(otherAddMemberCommit, groups[i-1].groupId).getOrThrow()

    clients.subList(0, i).forEach { it.processNextMessage().getOrThrow() }

    groups.add(clients[i].processNextMessage().getOrThrow()!! as ActiveGroupClient<String>)

    for(j in 0..i) {
      printGroup(groups[j], clientsList[j], i+1)
    }

    println("COMMIT DONE BY " + clientsList[i-1])
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    println()
  }


  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // UPDATING KEYS
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  val i = 1
  var idxGhosts = listOf<Int>()
  var idxCommit = 1
  var idxUpdate = IntRange(0, clients.size-1).filter { !idxGhosts.contains(it) && it != idxCommit }
  println(i.toString() + ": UPDATING " + clientsList.slice(idxUpdate) + " KEYS, COMMITTING BY " + clientsList[idxCommit])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  updateKeys(
    groups.slice(idxUpdate),
    clients,
    clients.slice(idxGhosts).toMutableList()
  )
  commit(
    groups[idxCommit],
    clients,
    clients.slice(idxGhosts).toMutableList()
  )
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  for(j in 0..<clients.size) {
    printGroup(groups[j], clientsList[j], clients.size)
  }

  idxGhosts = listOf(clients.size-1)
  idxUpdate = IntRange(0, clients.size-1).filter { !idxGhosts.contains(it) && it != idxCommit }
  for(j in 2..3) {
    println(j.toString() + ": UPDATING " + clientsList.slice(idxUpdate) + " KEYS, COMMITTING BY " + clientsList[idxCommit])
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    updateKeys(
      groups.slice(idxUpdate),
      clients,
      clients.slice(idxGhosts).toMutableList()
    )
    commit(
      groups[idxCommit],
      clients,
      clients.slice(idxGhosts).toMutableList()
    )
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")
  }

  for(j in 0..<clients.size) {
    printGroup(groups[j], clientsList[j], clients.size)
  }

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // CHECKING GHOST STATUS
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("CHECKING GHOST STATUS")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  for(j in 0..<clients.size) {
    val st = groups[j].state
    println("Ghosts Groups["+clientsList[j]+"]: ")
    println("Members(" + st.ghostMembers.size + "): " + st.ghostMembers)
    println("Keys(" + st.ghostMembersKeys.size+"): " + st.ghostMembersKeys)
    println("SecretShares(" + st.ghostMembersShares.size+"): " + st.ghostMembersShares)
    println("ShareHolderRanks(" + st.ghostMembersShareHolderRank.size+"): " + st.ghostMembersShareHolderRank)
    println("")
  }

  // GHOST 1
  val listShares = mutableListOf<ShamirSecretSharing.SecretShare>()
  for(j in IntRange(0, groups.size-1).filter{ !idxGhosts.contains(it) }) {
    if(groups[j].state.ghostMembersShareHolderRank[0] == 1) {
      listShares.add(groups[j].state.ghostMembersShares[0])
    }
  }
  val secret = ShamirSecretSharing.retrieveSecret(listShares)
  val keyPair = groups[0].state.derive(secret)
  println(keyPair.public)

  for(j in IntRange(0, groups.size-1).filter{ !idxGhosts.contains(it) }) {
    println(groups[j].state.ghostMembersKeys[0])
    assert(keyPair.public.bytes.contentEquals(groups[j].state.ghostMembersKeys[0].bytes))
  }

  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")


  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // GHOST RECONNECTING
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("GHOST RECONNECTING")
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

  println("\n---------------------------- Sending Quarantine End")
  clients[clients.size-1].ghostReconnect(groups[clients.size-1].groupId)
  clients.filterIndexed{ idx, _ -> !idxGhosts.contains(idx)}.forEach{
    it.processNextMessage().getOrThrow()
  }

  println("\n---------------------------- Receiving Share Recovery Message")
  for(k in 0..clients.size-1){
    println("")

    clients.forEach {
      it.processNextMessage().getOrThrow()
    }
  }

//  for(j in 0..<clients.size) {
//    printGroup(groups[j], clientsList[j], clients.size)
//  }

  println("\n---------------------------- Committing by " + clientsList[idxCommit])
  commit(
    groups[idxCommit],
    clients,
    //clients.slice(idxGhosts).toMutableList()
  )

  clients[clients.size-1].processNextMessage().getOrThrow()

  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  clients[0].sendMessage(groups[0].groupId, "test Alice")
  for(k in 1..<clients.size){
    clients[k].processNextMessage().getOrThrow()
  }


  for(j in 0..<clients.size) {
    printGroup(groups[j], clientsList[j], clients.size)
  }

  idxGhosts = listOf<Int>()
  idxCommit = 1
  idxUpdate = IntRange(0, clients.size-1).filter { !idxGhosts.contains(it) && it != idxCommit }
  println("UPDATING " + clientsList.slice(idxUpdate) + " KEYS, COMMITTING BY " + clientsList[idxCommit])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  updateKeys(
    groups.slice(idxUpdate),
    clients,
    clients.slice(idxGhosts).toMutableList()
  )
  commit(
    groups[idxCommit],
    clients,
    clients.slice(idxGhosts).toMutableList()
  )
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  for(j in 0..<clients.size) {
    printGroup(groups[j], clientsList[j], clients.size)
  }

  // GHOST 2
//  val listShares2 = mutableListOf<ShamirSecretSharing.SecretShare>()
//  for(j in IntRange(0, groups.size-1).filter{ !idxGhosts.contains(it) }) {
//    listShares2.add(groups[j].state.ghostMembersShares[1])
//  }
//  val secret2 = ShamirSecretSharing.retrieveSecret(listShares2)
//  val keyPair2 = groups[0].state.derive(secret2)
//  println(keyPair2.public)
//
//  for(j in IntRange(0, groups.size-1).filter{ !idxGhosts.contains(it) }) {
//    println(groups[j].state.ghostMembersKeys[1])
//    assert(keyPair2.public.bytes.contentEquals(groups[j].state.ghostMembersKeys[1].bytes))
//  }




//  //////////////////////////////////////////////////////////////////////
//  //////////////////////////////////////////////////////////////////////
//  // EXCHANGING MESSAGES
//  //////////////////////////////////////////////////////////////////////
//  //////////////////////////////////////////////////////////////////////
//  // Sending messages
//  println()
//  println("Sending messages:")
//  println("/////////////////////////////////////////////////////////////////////////")
//
//  println("Alice: ")
//  alice.sendMessage(aliceGroup.groupId, "Hello, this is Alice!")
////  print("  [Bob] ")
////  bob.processNextMessage()
//  print("  [Charlie] ")
//  charlie.processNextMessage()
//
////  println("Bob: ")
////  bob.sendMessage(bobGroup.groupId, "Hello, this is Bob!")
////  print("  [Alice] ")
////  alice.processNextMessage()
////  print("  [Charlie] ")
////  charlie.processNextMessage()
//
//  println("Charlie: ")
//  charlie.sendMessage(charlieGroup.groupId, "How are you, Bob?")
//  print("  [Alice] ")
//  alice.processNextMessage()
////  print("  [Bob] ")
////  bob.processNextMessage()

}
