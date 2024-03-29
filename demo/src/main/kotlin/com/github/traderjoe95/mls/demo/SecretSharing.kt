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

 for(i in 1..1000){


   val secret = SecureRandom().nextBytes(32)

   val sh = ShamirSecretSharing(3, 5)

   val shares = sh.generateShares(secret)

   val s = sh.retrieveSecret(shares)

   assert(BigInteger(1, s) == BigInteger(1, secret))

  }

}
