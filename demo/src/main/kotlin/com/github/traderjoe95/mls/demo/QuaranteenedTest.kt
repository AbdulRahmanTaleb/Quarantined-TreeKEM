package com.github.traderjoe95.mls.demo

import com.github.traderjoe95.mls.demo.client.Client
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.demo.util.makePublic
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import com.github.traderjoe95.mls.protocol.types.framing.content.Update
import com.github.traderjoe95.mls.protocol.util.debug


fun printGroup(group: ActiveGroupClient<String>, name: String, nbMembers: Int){
  println(name + " EPOCH " + group.state.epoch + ": ")

  println("================================================================")
  for(i in 0..<nbMembers) {
    println("val[" + i + "] = " + group.state.members[i].epk)
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

  val alice = Client("Alice")
  val bob = Client("Bob")
  val charlie = Client("Charlie")

  alice.generateKeyPackages(10U)
  bob.generateKeyPackages(10U)
  charlie.generateKeyPackages(10U)

  clients.add(alice)
  clients.add(bob)
  clients.add(charlie)

  val excludeClients = mutableListOf(bob)

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // GROUP CREATION BY ALICE
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  // Alice creates the group. At this point, she is the only member
  println("CREATING GROUP")
  println("/////////////////////////////////////////////////////////////////////////")
  val aliceGroup = alice.createGroup().getOrThrow()
  printGroup(aliceGroup, "ALICE", 1)
  println(aliceGroup.state.members[0].encryptionKey)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // ADDING BOB TO THE GROUP
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("ADDING BOB TO THE GROUP")
  println("/////////////////////////////////////////////////////////////////////////")
  // Alice adds Bob and creates a Welcome message for him to join the group and bootstrap his shared cryptographic state
  // -- In principle, this also sends a Commit message for all group members, but as Alice is the only person in the
  // -- group, it won't really go to anyone
  val bobKeyPackage = alice.getKeyPackageFor(Config.cipherSuite, "Bob")
  val addBob = aliceGroup.addMember(bobKeyPackage).getOrThrow()
  DeliveryService.sendMessageToGroup(addBob, aliceGroup.groupId).getOrThrow()

  // Process the proposal
  alice.processNextMessage().getOrThrow()

  // Alice now commits the proposal
  val addBobCommit = aliceGroup.commit().getOrThrow()
  DeliveryService.sendMessageToGroup(addBobCommit, aliceGroup.groupId).getOrThrow()

  // Process the commit, Alice now sends the Welcome to Bob
  alice.processNextMessage().getOrThrow()

  // Bob processes the Welcome and bootstraps his state
  val bobGroup = bob.processNextMessage().getOrThrow()!! as ActiveGroupClient<String>

  printGroup(aliceGroup, "ALICE", 2)
  printGroup(bobGroup, "BOB", 2)

  println("Commit done by Alice.")
  println(aliceGroup.state.members[0].encryptionKey)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // ADDING CHARLIE TO THE GROUP
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  println("ADDING CHARLIE TO THE GROUP")
  println("/////////////////////////////////////////////////////////////////////////")
  // -- Alice and Bob have shared cryptographic state.
  // ---- For Alice, it's already the second epoch of the group she sees (1st: only Alice, 2nd: Alice+Bob)
  // 1. Bob updates the group by adding Charlie
  // 2. This sends a Commit message to all existing members of the group (i.e. Alice), changing the group state to
  //    include Charlie and establish shared cryptographic state with him
  // 3. This sends a Welcome message to Charlie which he can use to join the group and bootstrap the shared
  //    cryptographic state
  val charlieKeyPackage = bob.getKeyPackageFor(Config.cipherSuite, "Charlie")
  val addCharlieCommit = bobGroup.commit(listOf(Add(charlieKeyPackage))).getOrThrow()
  DeliveryService.sendMessageToGroup(addCharlieCommit, bobGroup.groupId).getOrThrow()

  // Alice processes the commit
  alice.processNextMessage().getOrThrow()

  // Bob processes the commit and sends the welcome message
  bob.processNextMessage().getOrThrow()

  // Charlie receives the commit
  val charlieGroup = charlie.processNextMessage().getOrThrow()!! as ActiveGroupClient<String>

  printGroup(aliceGroup, "ALICE", 3)
  printGroup(bobGroup, "BOB", 3)
  printGroup(charlieGroup, "CHARLIE", 3)
  println("Commit done by Bob.")
  println(aliceGroup.state.members[0].encryptionKey)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  groups.add(aliceGroup)
  groups.add(bobGroup)
  groups.add(charlieGroup)

  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // UPDATING KEYS
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////

  // Updating alice's keys, committing by Charlie
  println("UPDATING ALICE KEYS, COMMITTING BY CHARLIE")
  println("/////////////////////////////////////////////////////////////////////////")
  updateKeys(aliceGroup, charlieGroup, clients)
  printGroup(aliceGroup, "ALICE", 3)
  printGroup(bobGroup, "BOB", 3)
  printGroup(charlieGroup, "CHARLIE", 3)
  println("Commit done by Charlie.")
  println(aliceGroup.state.members[0].encryptionKey)
  println(charlieGroup.state.tree.print())
  println(aliceGroup.state.tree.print())
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  // Updating charlie's keys, committing by Alice
  println("UPDATING CHARLIE KEYS, COMMITTING BY ALICE")
  println("/////////////////////////////////////////////////////////////////////////")
  updateKeys(charlieGroup, aliceGroup, clients, excludeClients)
  printGroup(aliceGroup, "ALICE", 3)
  printGroup(bobGroup, "BOB", 3)
  printGroup(charlieGroup, "CHARLIE", 3)
  println("Commit done by Alice.")
  println(aliceGroup.state.members[0].encryptionKey)
  println(aliceGroup.state.tree.print())
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  // Updating Alice's keys, committing by Charlie
  println("UPDATING ALICE KEYS, COMMITTING BY CHARLIE")
  println("/////////////////////////////////////////////////////////////////////////")
  updateKeys(aliceGroup, charlieGroup, clients, excludeClients)
  printGroup(aliceGroup, "ALICE", 3)
  printGroup(bobGroup, "BOB", 3)
  printGroup(charlieGroup, "CHARLIE", 3)
  println("Commit done by Charlie.")
  println(aliceGroup.state.members[0].encryptionKey)
  println(charlieGroup.state.tree.print())
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()


  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // EXCHANGING MESSAGES
  //////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////
  // Sending messages
  println()
  println("Sending messages:")
  println("/////////////////////////////////////////////////////////////////////////")

  println("Alice: ")
  alice.sendMessage(aliceGroup.groupId, "Hello, this is Alice!")
//  print("  [Bob] ")
//  bob.processNextMessage()
  print("  [Charlie] ")
  charlie.processNextMessage()

//  println("Bob: ")
//  bob.sendMessage(bobGroup.groupId, "Hello, this is Bob!")
//  print("  [Alice] ")
//  alice.processNextMessage()
//  print("  [Charlie] ")
//  charlie.processNextMessage()

  println("Charlie: ")
  charlie.sendMessage(charlieGroup.groupId, "How are you, Bob?")
  print("  [Alice] ")
  alice.processNextMessage()
//  print("  [Bob] ")
//  bob.processNextMessage()

}
