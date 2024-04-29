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

suspend fun main() {
  val alice = Client("Alice")
  val bob = Client("Bob")
  val charlie = Client("Charlie")

  alice.generateKeyPackages(10U)
  bob.generateKeyPackages(10U)
  charlie.generateKeyPackages(10U)

  // Alice creates the group. At this point, she is the only member
  val aliceGroup = alice.createGroup().getOrThrow()
  //aliceGroup.makePublic()
  printGroup(aliceGroup, "ALICE", 1)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  // Alice adds Bob and creates a Welcome message for him to join the group and bootstrap his shared cryptographic state
  // -- In principle, this also sends a Commit message for all group members, but as Alice is the only person in the
  // -- group, it won't really go to anyone
  val bobKeyPackage = alice.getKeyPackageFor(Config.cipherSuite, "Bob")
  val addBob = aliceGroup.addMember(bobKeyPackage).getOrThrow()
  DeliveryService.sendMessageToGroup(addBob, aliceGroup.groupId).getOrThrow()

  // Process the proposal
  alice.processNextMessage().getOrThrow()
  println("next")

  // Alice now commits the proposal
  val addBobCommit = aliceGroup.commit().getOrThrow()
  DeliveryService.sendMessageToGroup(addBobCommit, aliceGroup.groupId).getOrThrow()

  // Process the commit, Alice now sends the Welcome to Bob
  alice.processNextMessage().getOrThrow()

  // Bob processes the Welcome and bootstraps his state
  val bobGroup = bob.processNextMessage().getOrThrow()!! as ActiveGroupClient<String>

  printGroup(aliceGroup, "ALICE", 2)
  printGroup(bobGroup, "BOB", 2)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

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
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  // Updating alice's keys
  val updateAlice = aliceGroup.update().getOrThrow()
  DeliveryService.sendMessageToGroup(updateAlice, aliceGroup.groupId).getOrThrow()

  alice.processNextMessage().getOrThrow()
  bob.processNextMessage().getOrThrow()
  charlie.processNextMessage().getOrThrow()

  val commitBob = bobGroup.commit().getOrThrow()
  DeliveryService.sendMessageToGroup(commitBob, bobGroup.groupId).getOrThrow()

  alice.processNextMessage().getOrThrow()
  bob.processNextMessage().getOrThrow()
  charlie.processNextMessage().getOrThrow()

  printGroup(aliceGroup, "ALICE", 3)
  printGroup(bobGroup, "BOB", 3)
  printGroup(charlieGroup, "CHARLIE", 3)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()

  // Updating charlie's keys
  val updateCharlie = charlieGroup.update().getOrThrow()
  DeliveryService.sendMessageToGroup(updateCharlie, charlieGroup.groupId).getOrThrow()

  alice.processNextMessage().getOrThrow()
  bob.processNextMessage().getOrThrow()
  charlie.processNextMessage().getOrThrow()

  val commitAlice = aliceGroup.commit().getOrThrow()
  DeliveryService.sendMessageToGroup(commitAlice, aliceGroup.groupId).getOrThrow()

  alice.processNextMessage().getOrThrow()
  bob.processNextMessage().getOrThrow()
  charlie.processNextMessage().getOrThrow()

  printGroup(aliceGroup, "ALICE", 3)
  printGroup(bobGroup, "BOB", 3)
  printGroup(charlieGroup, "CHARLIE", 3)
  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
  println()


  ////////////////////////////////////////////////////////////////////////////
  // Sending messages
  println()
  println("Sending messages:")

  println("Alice: ")
  alice.sendMessage(aliceGroup.groupId, "Hello, this is Alice!")
  print("  [Bob] ")
  bob.processNextMessage()
  print("  [Charlie] ")
  charlie.processNextMessage()

  println("Bob: ")
  bob.sendMessage(bobGroup.groupId, "Hello, this is Bob!")
  print("  [Alice] ")
  alice.processNextMessage()
  print("  [Charlie] ")
  charlie.processNextMessage()

  println("Charlie: ")
  charlie.sendMessage(charlieGroup.groupId, "How are you, Bob?")
  print("  [Alice] ")
  alice.processNextMessage()
  print("  [Bob] ")
  bob.processNextMessage()

//  val bobGroup = bob.joinPublicGroup(aliceGroup.state.groupId).getOrThrow()
//
//  alice.processNextMessage().getOrThrow()
//  printGroup(aliceGroup, "ALICE", 2)
//  printGroup(bobGroup, "BOB", 2)
//  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
//  println()
//
//  bobGroup.makePublic()
//
//  val charlieGroup = charlie.joinPublicGroup(aliceGroup.state.groupId).getOrThrow()
//
//  alice.processNextMessage().getOrThrow()
//  bob.processNextMessage().getOrThrow()
//  charlie.processNextMessage().getOrThrow()
//
//  printGroup(aliceGroup, "ALICE", 3)
//  printGroup(bobGroup, "BOB", 3)
//  printGroup(charlieGroup, "CHARLIE", 3)
//  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
//  println()
//
//  val updateAlice = aliceGroup.update().getOrThrow()
//  DeliveryService.sendMessageToGroup(updateAlice, aliceGroup.groupId).getOrThrow()
//
//  alice.processNextMessage().getOrThrow()
//  bob.processNextMessage().getOrThrow()
//  charlie.processNextMessage().getOrThrow()
//
//  println()
//  println()
//
//  val commitBob = bobGroup.commit().getOrThrow()
//  DeliveryService.sendMessageToGroup(commitBob, bobGroup.groupId).getOrThrow()
//
//  //alice.processNextMessage().getOrThrow()
//  bob.processNextMessage().getOrThrow()
//  charlie.processNextMessage().getOrThrow()
//
//  println()
//  println()
//
//  //printGroup(aliceGroup, "ALICE", 3)
//  printGroup(bobGroup, "BOB", 3)
//  printGroup(charlieGroup, "CHARLIE", 3)
//  println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
//  println()


}
