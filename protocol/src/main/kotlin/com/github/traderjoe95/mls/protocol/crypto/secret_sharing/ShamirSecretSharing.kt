package com.github.traderjoe95.mls.protocol.crypto.secret_sharing

import com.github.traderjoe95.mls.protocol.group.GroupContext.Companion.encodeUnsafe
import java.math.BigInteger
import java.security.SecureRandom
import com.github.traderjoe95.mls.codec.Encodable
import com.github.traderjoe95.mls.codec.type.DataType
import com.github.traderjoe95.mls.codec.type.V
import com.github.traderjoe95.mls.codec.type.opaque
import com.github.traderjoe95.mls.codec.type.struct.Struct4T
import com.github.traderjoe95.mls.codec.type.struct.lift
import com.github.traderjoe95.mls.codec.type.struct.struct
import com.github.traderjoe95.mls.codec.type.uint32
import com.github.traderjoe95.mls.codec.type.uint64
import com.github.traderjoe95.mls.codec.util.fromBytes
import com.github.traderjoe95.mls.codec.util.get
import com.github.traderjoe95.mls.codec.util.toBytes
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.error.ProcessMessageError
import com.github.traderjoe95.mls.protocol.group.GroupContext
import com.github.traderjoe95.mls.protocol.types.GroupContextExtension
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.crypto.HpkePublicKey
import com.github.traderjoe95.mls.protocol.types.crypto.Secret.Companion.asSecret
import com.github.traderjoe95.mls.protocol.types.extensionList
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion
import com.github.traderjoe95.mls.protocol.types.tree.hash.ParentHashInput
import com.github.traderjoe95.mls.protocol.types.tree.leaf.ParentHash
import kotlin.math.min

class ShamirSecretSharing() {
  companion object{
    const val FIELD_ELEM_SIZE = 192
    val p = BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA237327FFFFFFFFFFFFFFFF",16)

    fun generateShares(secret: ByteArray, t: Int, m: Int) : List<SecretShare>{

      val rnd = SecureRandom()

      val coefficients = mutableListOf<BigInteger>()
      for (i in 1..t- 1) {
        coefficients += generateFieldElem(rnd)
      }

      coefficients += BigInteger(1, secret)

      val shares  =  mutableListOf<SecretShare>()

      for (i in 1..m) {
        val x = generateFieldElem(rnd)
        val y = evaluatePolynomial(coefficients, x)
        shares += SecretShare(i.toUInt(), t.toUInt(), x.toByteArray(), y.toByteArray())
      }

      val newS = BigInteger(1, retrieveSecret(shares))
      assert(newS == BigInteger(1, secret))

      return shares
    }

    fun retrieveSecret(shares: List<SecretShare>): ByteArray{
      var res = BigInteger("0")

      val nb =  min(shares.size, shares[0].t.toInt())

      for (j in 0..< nb) {

        var tmp = BigInteger(1, shares[j].y)

        val xj = BigInteger(1, shares[j].x)

        for (i in 0..< nb) {

          if (i != j) {

            val xi = BigInteger(1, shares[i].x)

            var inter = xi.subtract(xj).modInverse(p)

            inter = inter.multiply(xi).mod(p)

            tmp = tmp.multiply(inter).mod(p)
          }
        }

        res = res.add(tmp).mod(p)
      }

      // remove leading zeros from byte array
      var bytes = res.toByteArray()
      var offset = 0
      while(bytes[offset].toInt() == 0){
        offset ++
      }
      bytes = bytes[UIntRange(offset.toUInt(), bytes.size.toUInt() - 1u)]

      return bytes
    }

    private fun evaluatePolynomial(coefficients: List<BigInteger>, x: BigInteger) : BigInteger{
      var res = coefficients[0]

      for (i in 1..<coefficients.size){

        res = res.multiply(x).add(coefficients[i]).mod(p)
      }

      return res.mod(p)
    }

    private fun generateFieldElem(rnd: SecureRandom): BigInteger{
      return BigInteger(1, ByteArray(FIELD_ELEM_SIZE).also(rnd::nextBytes)).mod(p)
    }
  }

  data class SecretShare(
    val rank: UInt,
    val t: UInt,
    val x: ByteArray,
    val y: ByteArray,
  ) : Struct4T.Shape<UInt, UInt, ByteArray, ByteArray> {
    companion object : Encodable<SecretShare> {
      @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
      override val T: DataType<SecretShare> =
        struct("SecretShare") {
          it.field("rank", uint32.asUInt)
            .field("t", uint32.asUInt)
            .field("x", opaque[V])
            .field("y", opaque[V])
        }.lift(::SecretShare)
    }

    override fun hashCode(): Int {
      var result = rank.hashCode()
      result = 31 * result + x.hashCode()
      result = 31 * result + y.hashCode()
      result = 31 * result + t.hashCode()
      return result
    }

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as SecretShare

      if (rank != other.rank) return false
      if (t != other.t) return false
      if (!x.contentEquals(other.x)) return false
      if (!y.contentEquals(other.y)) return false

      return true
    }
  }

}

