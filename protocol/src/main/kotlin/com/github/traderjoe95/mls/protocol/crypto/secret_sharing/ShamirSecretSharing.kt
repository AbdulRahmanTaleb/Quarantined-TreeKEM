package com.github.traderjoe95.mls.protocol.crypto.secret_sharing

import java.math.BigInteger
import java.security.SecureRandom

class ShamirSecretSharing(
  val t: Int,
  val m: Int,
  private val rnd: SecureRandom = SecureRandom()
) {
  val p = BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA237327FFFFFFFFFFFFFFFF",16)

  fun generateShares(secret: ByteArray) : List<Pair<ByteArray, ByteArray>>{

    var coefficients = mutableListOf<ByteArray>()
    for (i in 1..t-1){
      coefficients += generateFieldElem()
    }
    coefficients += secret

    val shares  =  mutableListOf<Pair<ByteArray, ByteArray>>()

    for (i in 1..m) {
      val x = generateFieldElem()
      shares += Pair(x, evaluatePolynomial(coefficients, x))
    }

    for(i in 0..t) {
      coefficients[0].fill(0, 0)
    }

    return shares
  }

  fun retrieveSecret(shares: List<Pair<ByteArray, ByteArray>>): ByteArray{
    var res = BigInteger("0")

    for (j in 0..shares.size-1) {

      var tmp = BigInteger(1, shares[j].second)

      for (m in 0..shares.size - 1) {
        if (m != j) {

          var inter =
            BigInteger(1, shares[m].first).subtract(BigInteger(1, shares[j].first)).modInverse(p)
          inter = inter.multiply(BigInteger(1, shares[m].first)).mod(p)

          tmp = tmp.multiply(inter).mod(p)
        }
      }

      res = res.add(tmp).mod(p)
    }

    return res.toByteArray()
  }


  fun evaluatePolynomial(coefficients: List<ByteArray>, x: ByteArray) : ByteArray {
    var res = BigInteger(1, coefficients[0])

    val xVal = BigInteger(1, x)

    for (i in 1..<coefficients.size){
      res = res.multiply(xVal).add(BigInteger(1, coefficients[i])).mod(p)
    }

    return res.toByteArray()
  }

  private fun generateFieldElem(): ByteArray{
    return ByteArray(256).also(rnd::nextBytes)
  }




}
