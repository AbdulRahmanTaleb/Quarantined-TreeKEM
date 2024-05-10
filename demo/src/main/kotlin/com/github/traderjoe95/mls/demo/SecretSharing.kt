package com.github.traderjoe95.mls.demo

import arrow.core.Either
import arrow.core.getOrElse
import com.github.traderjoe95.mls.demo.client.Client
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.crypto.Secret.Companion.asSecret
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import com.github.traderjoe95.mls.protocol.util.debug
import com.github.traderjoe95.mls.protocol.util.nextBytes
import java.math.BigInteger
import java.security.SecureRandom

suspend fun main() {

    for(i in 1..100){

        val secret = SecureRandom().nextBytes(64)
        val shares = ShamirSecretSharing.generateShares(secret, 4,4)

        val newShares = shares.map{
            ShamirSecretSharing.SecretShare.decode(it.encode()).second
        }
        val s = BigInteger(1, ShamirSecretSharing.retrieveSecret(newShares))

        assert(s == BigInteger(1, secret))
    }

    for(i in 1..100){
        val secret = SecureRandom().nextBytes(64)

        val shares = ShamirSecretSharing.generateShares(secret, 4,4)

        val sharesBytes = shares.fold(ByteArray(0)) { acc, share -> acc + share.encode() }

        val newShares = ShamirSecretSharing.SecretShare.decodeListOfShares(sharesBytes, 4)

        val s = BigInteger(1, ShamirSecretSharing.retrieveSecret(newShares))

        assert(s == BigInteger(1, secret))
    }

}
