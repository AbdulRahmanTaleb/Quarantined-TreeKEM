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
import com.github.traderjoe95.mls.protocol.group.GroupContext
import com.github.traderjoe95.mls.protocol.types.GroupContextExtension
import com.github.traderjoe95.mls.protocol.types.GroupId
import com.github.traderjoe95.mls.protocol.types.extensionList
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion

class ShamirSecretSharing() {
  companion object{
    const val FIELD_ELEM_SIZE = 256
    val p = BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA237327FFFFFFFFFFFFFFFF",16)

    fun generateShares(secret: ByteArray, t: Int, m: Int) : List<SecretShare>{

      val rnd = SecureRandom()

      var coefficients = mutableListOf<ByteArray>()
      for (i in 1..t- 1){
        coefficients += generateFieldElem(rnd)
      }
      coefficients += secret

      val shares  =  mutableListOf<SecretShare>()

      for (i in 1..m) {
        val x = generateFieldElem(rnd)
        shares += SecretShare(i, t, x, evaluatePolynomial(coefficients, x))
      }

      for(i in 0..t- 1) {
        coefficients[i].fill(0, 0)
      }

      return shares
    }

    fun retrieveSecret(shares: List<SecretShare>): ByteArray{
      var res = BigInteger("0")

      for (j in 0..<shares.size) {

        var tmp = BigInteger(1, shares[j].y)

        for (m in 0..<shares.size) {
          if (m != j) {

            var inter =
              BigInteger(1, shares[m].x).subtract(BigInteger(1, shares[j].x)).modInverse(p)
            inter = inter.multiply(BigInteger(1, shares[m].x)).mod(p)

            tmp = tmp.multiply(inter).mod(p)
          }
        }

        res = res.add(tmp).mod(p)
      }

      return res.toByteArray()
    }

    private fun evaluatePolynomial(coefficients: List<ByteArray>, x: ByteArray) : ByteArray {
      var res = BigInteger(1, coefficients[0])

      val xVal = BigInteger(1, x)

      for (i in 1..<coefficients.size){
        res = res.multiply(xVal).add(BigInteger(1, coefficients[i])).mod(p)
      }

      return res.toByteArray().copyOf(FIELD_ELEM_SIZE)
    }

    private fun generateFieldElem(rnd: SecureRandom): ByteArray{
      return ByteArray(FIELD_ELEM_SIZE).also(rnd::nextBytes)
    }
  }

  data class SecretShare(
    val rank: Int,
    val t: Int,
    val x: ByteArray,
    val y: ByteArray
  ) {

    fun encode(): ByteArray {
      return rank.toBytes(4u) + t.toBytes(4u) + x + y
    }

    companion object {

      val size = 2 * (4 + FIELD_ELEM_SIZE)
      fun decode(bytes: ByteArray): SecretShare {
        val rank = Int.fromBytes(bytes[UIntRange(0u, 3u)])
        val t = Int.fromBytes(bytes[UIntRange(4u, 7u)])
        val x = bytes[UIntRange(8u, 8u + FIELD_ELEM_SIZE.toUInt() - 1u)]
        val y = bytes[UIntRange(8u + FIELD_ELEM_SIZE.toUInt(), bytes.size.toUInt() - 1u)]
        return SecretShare(rank, t, x, y)
      }
    }

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as SecretShare

      if (rank != other.rank) return false
      if (!x.contentEquals(other.x)) return false
      if (!y.contentEquals(other.y)) return false
      if(t != other.t) return false


      return true
    }

    override fun hashCode(): Int {
      var result = rank.hashCode()
      result = 31 * result + x.contentHashCode()
      result = 31 * result + y.contentHashCode()
      result = 31 * result + t.hashCode()
      return result
    }
  }


}
