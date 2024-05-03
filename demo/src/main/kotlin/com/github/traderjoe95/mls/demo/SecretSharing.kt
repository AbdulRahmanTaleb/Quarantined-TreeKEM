package com.github.traderjoe95.mls.demo

import arrow.core.Either
import arrow.core.getOrElse
import com.github.traderjoe95.mls.demo.client.Client
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import com.github.traderjoe95.mls.protocol.util.debug
import com.github.traderjoe95.mls.protocol.util.nextBytes
import java.math.BigInteger
import java.security.SecureRandom

suspend fun main() {

    val l = listOf<Int>(1,2)

    println(l.map{ 1 })

 for(i in 1..10){
     val secret = SecureRandom().nextBytes(32)

     val shares = ShamirSecretSharing.generateShares(secret, 3, 5)

     val newShares = shares.map{
         assert(it.encode().size == 256)
         ShamirSecretSharing.SecretShare.decode(it.encode())
     }


     val s = ShamirSecretSharing.retrieveSecret(newShares)

     assert(BigInteger(1, s) == BigInteger(1, secret))
  }

}
