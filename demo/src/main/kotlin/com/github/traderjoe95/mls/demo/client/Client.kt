package com.github.traderjoe95.mls.demo.client

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.raise.Raise
import arrow.core.raise.either
import com.github.traderjoe95.mls.demo.Config
import com.github.traderjoe95.mls.demo.getOrThrow
import com.github.traderjoe95.mls.demo.service.AuthenticationService
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.client.GroupClient
import com.github.traderjoe95.mls.protocol.client.MlsClient
import com.github.traderjoe95.mls.protocol.client.ProcessHandshakeResult
import com.github.traderjoe95.mls.protocol.client.ProcessMessageResult
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.error.CreateSignatureError
import com.github.traderjoe95.mls.protocol.error.ExternalJoinError
import com.github.traderjoe95.mls.protocol.error.GroupCreationError
import com.github.traderjoe95.mls.protocol.message.KeyPackage
import com.github.traderjoe95.mls.protocol.types.ApplicationId
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.Credential
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.crypto.SignatureKeyPair
import com.github.traderjoe95.mls.protocol.types.framing.content.ApplicationData
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion
import com.github.traderjoe95.mls.protocol.util.hex
import de.traderjoe.ulid.ULID
import de.traderjoe.ulid.blocking.new
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel

class Client(
  val userName: String,
  val applicationId: ULID = ULID.new(),
  private val credential: Credential = BasicCredential(userName.encodeToByteArray()),
) : com.github.traderjoe95.mls.protocol.service.AuthenticationService<String> by AuthenticationService,
  com.github.traderjoe95.mls.protocol.service.DeliveryService<String> by DeliveryService {
  private val messages: Channel<Pair<ULID, ByteArray>> = DeliveryService.registerUser(userName)

  private val cachedGhostMessages: MutableList<Pair<ULID, ByteArray>> = mutableListOf()

  private val keyPackages: MutableMap<String, KeyPackage.Private> = mutableMapOf()
  private val signatureKeyPairs: MutableMap<CipherSuite, SignatureKeyPair> = mutableMapOf()

  private val mlsClient: MlsClient<String> = MlsClient(this)

  private var ghost = false

  fun generateKeyPackages(
    amount: UInt,
    cipherSuite: CipherSuite = Config.cipherSuite,
  ) = repeat(amount.toInt()) {
    DeliveryService.addKeyPackage(
      userName,
      either { newKeyPackage(cipherSuite) }.getOrElse { error("Error creating key package: $it") }.public,
    )
  }

  fun createGroup(): Either<GroupCreationError, ActiveGroupClient<String>> =
    // As this function takes ownership of the signature private key, we need to copy it
    mlsClient.createGroup(
      Config.cipherSuite,
      signatureKeyPairs.computeIfAbsent(Config.cipherSuite, CipherSuite::generateSignatureKeyPair).copy(),
      credential,
      leafNodeExtensions =
        listOf(
          ApplicationId(applicationId.toBytes()),
        ),
    ).onRight {
      DeliveryService.registerForGroup(it.groupId, userName)
    }

  suspend fun joinPublicGroup(groupId: GroupId): Either<ExternalJoinError, ActiveGroupClient<String>> =
    mlsClient.joinFromGroupInfo(
      DeliveryService.getPublicGroupInfo(groupId).getOrThrow(),
      signatureKeyPairs.computeIfAbsent(Config.cipherSuite, CipherSuite::generateSignatureKeyPair).copy(),
      credential,
      leafNodeExtensions =
        listOf(
          ApplicationId(applicationId.toBytes()),
        ),
    ).map { (group, commit) ->
      DeliveryService.sendMessageToGroup(commit, groupId)
      DeliveryService.registerForGroup(group.groupId, userName)

      group
    }

  suspend fun sendMessage(
    to: GroupId,
    message: String,
  ) {
    DeliveryService.sendMessageToGroup(
      (mlsClient[to]!! as ActiveGroupClient<String>).seal(ApplicationData(message.encodeToByteArray())).getOrThrow(),
      to,
      fromUser = userName,
    )
  }

  suspend fun processCachedGhostMessages(): Either<Any, Unit> =
    either {
      println("Processing cached messages (total: " + cachedGhostMessages.size + ")")
      cachedGhostMessages.forEach{(messageId, encoded) ->
//        println(idx)
        processMessage(messageId, encoded, cached = true)
      }
      println("Finished processing cached messages")
    }

  @OptIn(ExperimentalCoroutinesApi::class)
  suspend fun noMessagesToProcess(): Boolean = messages.isEmpty

  suspend fun processNextMessage(): Either<Any, GroupClient<String, *>?> =
    either {
      if(ghost){
        messages.tryReceive().getOrNull()?.let{ cachedGhostMessages.add(it) }
        null
      }
      else{
        messages.tryReceive().getOrNull()?.let { (messageId, encoded) ->
          processMessage(messageId, encoded)
        }
      }
    }


  suspend fun processMessage(messageId: ULID, encoded: ByteArray, cached: Boolean = false): GroupClient<String, *>? {
//      println("[$userName] Message received: $messageId")

      when (val res = mlsClient.processMessage(encoded, cached).getOrThrow()) {

        is ProcessMessageResult.WelcomeMessageReceived -> {
          val keyPackage = res.welcome.secrets.firstNotNullOf {
            getKeyPackage(it.newMember)
          }

          return mlsClient.joinFromWelcome(res.welcome, keyPackage).getOrThrow().also {
            DeliveryService.registerForGroup(it.groupId, userName)
          }
        }

        is ProcessMessageResult.GroupInfoMessageReceived -> {
          return mlsClient.joinFromGroupInfo(
            res.groupInfo,
            signatureKeyPairs.computeIfAbsent(Config.cipherSuite, CipherSuite::generateSignatureKeyPair),
            credential,
            leafNodeExtensions =
              listOf(
                ApplicationId(applicationId.toBytes()),
              ),
          ).map { (group, commit) ->
            DeliveryService.sendMessageToGroup(commit, group.groupId)
            DeliveryService.registerForGroup(group.groupId, userName)

            group
          }.getOrThrow()
        }

        is ProcessMessageResult.ApplicationMessageReceived -> {
          println("[$userName] Conversation message received:\n--------- ${res.applicationData.framedContent.content.bytes.decodeToString()}")
          return mlsClient[res.groupId]
        }

        is ProcessMessageResult.QuarantineEndReceived -> {
          if((res.shareRecoveryMessage != null) && (!cached)){
//            println("sending ShareRecoveryMessage")
            DeliveryService.sendMessageToGroup(
              res.shareRecoveryMessage!!,
              res.groupId,
              fromUser = userName
            )
          }
          return mlsClient[res.groupId]
        }

        is ProcessMessageResult.HandshakeMessageReceived -> {
          when (val handshakeResult = res.result) {
            is ProcessHandshakeResult.ProposalReceived, is ProcessHandshakeResult.CommitProcessed -> {
//              println("[$userName] ${res.result}")
              return mlsClient[res.groupId]
            }

            is ProcessHandshakeResult.CommitProcessedWithNewMembers -> {
//              println("[$userName] CommitProcessed")
//              if(handshakeResult.welcomeMessages.isNotEmpty()){
//                println("adding new members - sending Welcome messages")
//              }

              handshakeResult.welcomeMessages.forEach { (welcome, to) ->
                DeliveryService.sendMessageToIdentities(
                  welcome.encoded,
                  authenticateCredentials(
                    to.map { it.leafNode.signaturePublicKey to it.leafNode.credential },
                  ).map{ it.getOrThrow() },
                )
              }

//              if(handshakeResult.welcomeBackGhostMessages.isNotEmpty()){
//                println("reviving ghosts - sending WelcomeBackGhost messages")
//              }
              handshakeResult.welcomeBackGhostMessages.forEach { (welcomeBack, to) ->
                DeliveryService.sendMessageToIdentities(
                  welcomeBack.encoded,
                  authenticateCredentials(
                    to.map { it.signaturePublicKey to it.credential },
                  ).map{ it.getOrThrow() },
                )
              }

              return mlsClient[res.groupId]
            }

            is ProcessHandshakeResult.ReInitProcessed -> {
//              println("[$userName] ReInit processed, returning suspended group")
              return handshakeResult.suspendedClient
            }

          }
        }

        is ProcessMessageResult.KeyPackageMessageReceived -> {
//          println("Key package received, ignoring")
          return null
        }

        is ProcessMessageResult.ShareRecoveryMessageReceived -> {
          return null
        }

        is ProcessMessageResult.RequestWelcomeBackGhostMessageReceived -> {
          return null
        }

        is ProcessMessageResult.WelcomeBackGhostMessageIgnored -> {
//          println("[$userName] $res")
          return null
        }
        is ProcessMessageResult.WelcomeBackGhostMessageProcessed -> {
//          println("[$userName] $res")
          return null
        }

        ProcessMessageResult.MessageToCachForLater -> {
//          println("Temporarily caching message because user is a ghost reconnecting")
          cachedGhostMessages.add(Pair(messageId, encoded))
          return null
        }

        is ProcessMessageResult.ShareResendReceived -> {
          if((res.shareRecoveryMessage != null) && (!cached)){
            DeliveryService.sendMessageToGroup(
              res.shareRecoveryMessage!!,
              res.groupId,
              fromUser = userName
            )
          }
          return mlsClient[res.groupId]
        }
      }
    }

  suspend fun declareAsGhost(){
    ghost = true
    var message = messages.tryReceive().getOrNull()
    while(message != null){
      cachedGhostMessages.add(message)
      message = messages.tryReceive().getOrNull()
    }
  }

  fun isGhost(): Boolean {
    return ghost
  }

  suspend fun ghostReconnect(groupId: GroupId) {
    ghost = false
    var message = messages.tryReceive().getOrNull()
    while(message != null){
      cachedGhostMessages.add(message)
      message = messages.tryReceive().getOrNull()
    }

    val endQuarantineMessage = (mlsClient[groupId]!! as ActiveGroupClient<String>).endQuarantine().getOrThrow()

    DeliveryService.sendMessageToGroup(
      endQuarantineMessage,
      groupId,
      fromUser = userName,
    )
  }

  suspend fun requestWelcomeBackGhost(groupId: GroupId) {

    val requestWelcomeBackGhostMessage = (mlsClient[groupId]!! as ActiveGroupClient<String>).requestWelcomeBackGhost().getOrThrow()

    DeliveryService.sendMessageToGroup(
      requestWelcomeBackGhostMessage,
      groupId,
      fromUser = userName,
    )
  }

  suspend fun retrievedEnoughShares(groupId: GroupId): Boolean {
    return (mlsClient[groupId]!! as ActiveGroupClient<String>).retrievedEnoughShares()
  }

  suspend fun sendShareResend(groupId: GroupId) {

    val shareResendMessage = (mlsClient[groupId]!! as ActiveGroupClient<String>).shareResend().getOrThrow()
    DeliveryService.sendMessageToGroup(
      shareResendMessage,
      groupId,
      fromUser = userName,
    )
  }

  fun getKeyPackage(ref: KeyPackage.Ref): KeyPackage.Private? = keyPackages[ref.hex]

  suspend fun getKeyPackageFor(
    cipherSuite: CipherSuite,
    user: String,
  ): KeyPackage = DeliveryService.getKeyPackage(ProtocolVersion.MLS_1_0, cipherSuite, user).getOrThrow()

  context(Raise<CreateSignatureError>)
  fun newKeyPackage(cipherSuite: CipherSuite): KeyPackage.Private =
    // As this function takes ownership of the signature private key, we need to copy it
    mlsClient.newKeyPackage(
      cipherSuite,
      signatureKeyPairs.computeIfAbsent(cipherSuite, CipherSuite::generateSignatureKeyPair).copy(),
      credential,
      leafNodeExtensions =
        listOf(
          ApplicationId(applicationId.toBytes()),
        ),
    ).bind().also {
      keyPackages[it.ref.hex] = it
    }
}
