package com.github.traderjoe95.mls.demo

import arrow.core.Either
import arrow.core.getOrElse
import com.github.traderjoe95.mls.codec.Encodable
import com.github.traderjoe95.mls.codec.type.DataType
import com.github.traderjoe95.mls.codec.type.V
import com.github.traderjoe95.mls.codec.type.get
import com.github.traderjoe95.mls.codec.type.struct.Struct1T
import com.github.traderjoe95.mls.codec.type.struct.lift
import com.github.traderjoe95.mls.codec.type.struct.struct
import com.github.traderjoe95.mls.codec.type.uint32
import com.github.traderjoe95.mls.codec.type.uint64
import com.github.traderjoe95.mls.demo.ShamirSharingList.Companion.encode
import com.github.traderjoe95.mls.demo.ShamirSharingList.Companion.encodeUnsafe
import com.github.traderjoe95.mls.demo.client.Client
import com.github.traderjoe95.mls.demo.service.DeliveryService
import com.github.traderjoe95.mls.protocol.client.ActiveGroupClient
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing
import com.github.traderjoe95.mls.protocol.crypto.secret_sharing.ShamirSecretSharing.SecretShare.Companion.encodeUnsafe
import com.github.traderjoe95.mls.protocol.group.GhostShareHolder
import com.github.traderjoe95.mls.protocol.tree.LeafIndex
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import com.github.traderjoe95.mls.protocol.types.crypto.Secret
import com.github.traderjoe95.mls.protocol.types.crypto.Secret.Companion.asSecret
import com.github.traderjoe95.mls.protocol.types.framing.content.Add
import com.github.traderjoe95.mls.protocol.util.debug
import com.github.traderjoe95.mls.protocol.util.nextBytes
import java.math.BigInteger
import java.security.SecureRandom
import kotlin.time.measureTime


private data class ShamirSharingList(val listSecrets: List<ShamirSecretSharing.SecretShare>) : Struct1T.Shape<List<ShamirSecretSharing.SecretShare>>{

    companion object : Encodable<ShamirSharingList> {
        @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
        override val T: DataType<ShamirSharingList> =
            struct("ShamirSharingList") {
                it.field("list_secrets", ShamirSecretSharing.SecretShare.T[V])
            }.lift(::ShamirSharingList)
    }
}

suspend fun main() {

    val nbIters = 50
    var totalGen = 0.0
    var totalRecover = 0.0

    for(i in 1..nbIters){

        val secret = SecureRandom().nextBytes(64)

        val shares:  List<ShamirSecretSharing.SecretShare>

        totalGen += measureTime {  shares = ShamirSecretSharing.generateShares(secret, 8,16) }.inWholeMilliseconds

        val newShares = shares.map{
            ShamirSecretSharing.SecretShare.decodeUnsafe(it.encodeUnsafe())
        }

        val sb: ByteArray

        totalRecover += measureTime { sb = ShamirSecretSharing.retrieveSecret(newShares) }.inWholeMilliseconds

        assert(BigInteger(1, sb) == BigInteger(1, secret))
    }

    println("Generating shares took " + (totalGen / nbIters) + " ms")
    println("Recovering secret took " + (totalRecover / nbIters) + " ms")

}
