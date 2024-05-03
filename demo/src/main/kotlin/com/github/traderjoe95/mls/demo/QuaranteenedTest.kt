package com.github.traderjoe95.mls.demo

import com.github.traderjoe95.mls.demo.client.Client
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.demo.util.makePublic
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import com.github.traderjoe95.mls.protocol.types.framing.content.Update
import com.github.traderjoe95.mls.protocol.util.debug

fun printGroup(group: ActiveGroupClient<String>, name: String, nbMembers: Int){
  println(name + " EPOCH " + group.state.epoch + ": ")

  println("================================================================")
  for(i in 0..<nbMembers) {
    println("Leaf[" + i + "]: epk = " + group.state.members[i].epk + ", source = " + group.state.members[i].source + " , equar = " + group.state.members[i].equar)
  }
  println("================================================================")
  println()
}

suspend fun updateKeys(updaterGroup: ActiveGroupClient<String>, committerGroup: ActiveGroupClient<String>, clients: MutableList<Client>, excludeClients: MutableList<Client> = mutableListOf()){
  val updateMember = updaterGroup.update().getOrThrow()
  DeliveryService.sendMessageToGroup(updateMember, updaterGroup.groupId).getOrThrow()

  clients.forEach {
    if(! excludeClients.contains(it)){
      it.processNextMessage().getOrThrow()
    }
  }

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

    println("COMMIT DONE BY " + clientsList[i-1])
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    println()
  }

  for(i in 0..<clients.size) {
    printGroup(groups[i], clientsList[i], clients.size)
  }

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // UPDATING KEYS
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  val excludeClients = mutableListOf(clients[2])

  var i = 1
  var idxUpdate = 0
  var idxCommit = 1
  println(i.toString() + ": UPDATING " + clientsList[idxUpdate] + " KEYS, COMMITTING BY " + clientsList[idxCommit])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  updateKeys(groups[idxUpdate], groups[idxCommit], clients, excludeClients)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  for(j in 0..<clients.size) {
    println(groups[j].state.ghostMembers)
  }

  i = 2
  idxUpdate = 1
  idxCommit = 3
  println(i.toString() + ": UPDATING " + clientsList[idxUpdate] + " KEYS, COMMITTING BY " + clientsList[idxCommit])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  updateKeys(groups[idxUpdate], groups[idxCommit], clients, excludeClients)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  for(j in 0..<clients.size) {
    println(groups[j].state.ghostMembers)
  }

  i = 3
  idxUpdate = 0
  idxCommit = 1
  println(i.toString() + ": UPDATING " + clientsList[idxUpdate] + " KEYS, COMMITTING BY " + clientsList[idxCommit])
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  updateKeys(groups[idxUpdate], groups[idxCommit], clients, excludeClients)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n")

  for(j in 0..<clients.size) {
    printGroup(groups[j], clientsList[j], clients.size)
  }

  val listShares = mutableListOf<ShamirSecretSharing.SecretShare>()
  for(j in IntRange(0, groups.size-1).filter{ it != 2 }) {
    println(groups[j].state.ghostMembers)
    println(groups[j].state.ghostMembersKeys)
    println(groups[j].state.ghostMembersShares)
    listShares.add(groups[j].state.ghostMembersShares[0])
    println("")
  }

  val secret = ShamirSecretSharing.retrieveSecret(listShares)
  print(secret)

  val keyPair = groups[0].state.derive(secret)
  print(keyPair.public)

  for(j in IntRange(0, groups.size-1).filter{ it != 2 }) {
    assert(keyPair.public == groups[j].state.ghostMembersKeys[0])
  }




//  // Updating charlie's keys, committing by Alice
//  println("UPDATING CHARLIE KEYS, COMMITTING BY ALICE")
//  println("/////////////////////////////////////////////////////////////////////////")
//  updateKeys(charlieGroup, aliceGroup, clients, excludeClients)
//  printGroup(aliceGroup, "ALICE", 3)
//  printGroup(bobGroup, "BOB", 3)
//  printGroup(charlieGroup, "CHARLIE", 3)
//  println("Commit done by Alice.")
//  println(aliceGroup.state.members[0].encryptionKey)
//  println(aliceGroup.state.tree.print())
//  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
//  println()
//
//  // Updating Alice's keys, committing by Charlie
//  println("UPDATING ALICE KEYS, COMMITTING BY CHARLIE")
//  println("/////////////////////////////////////////////////////////////////////////")
//  updateKeys(aliceGroup, charlieGroup, clients, excludeClients)
//  printGroup(aliceGroup, "ALICE", 3)
//  printGroup(bobGroup, "BOB", 3)
//  printGroup(charlieGroup, "CHARLIE", 3)
//  println("Commit done by Charlie.")
//  println(aliceGroup.state.members[0].encryptionKey)
//  println(charlieGroup.state.tree.print())
//  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
//  println()
//
//
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
