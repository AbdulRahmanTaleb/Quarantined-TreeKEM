package com.github.traderjoe95.mls.protocol.group

import arrow.core.raise.either
import arrow.core.raise.nullable
import com.github.traderjoe95.mls.protocol.client.GroupClient
import com.github.traderjoe95.mls.protocol.crypto.CipherSuite
import com.github.traderjoe95.mls.protocol.group.GroupContext
import com.github.traderjoe95.mls.protocol.interop.tree.TreeKemTestVector
import com.github.traderjoe95.mls.protocol.interop.util.nextKeyPackage
import com.github.traderjoe95.mls.protocol.message.KeyPackage
import com.github.traderjoe95.mls.protocol.testing.VertxFunSpec
import com.github.traderjoe95.mls.protocol.testing.shouldBeEq
import com.github.traderjoe95.mls.protocol.types.BasicCredential
import com.github.traderjoe95.mls.protocol.types.framing.enums.ProtocolVersion
import com.github.traderjoe95.mls.protocol.util.hex
import com.github.traderjoe95.mls.protocol.util.unsafe
import com.github.traderjoe95.mls.protocol.util.zipWithIndex
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.common.runBlocking
import io.kotest.core.factory.TestFactory
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength
import java.util.concurrent.ConcurrentHashMap
import com.github.traderjoe95.mls.protocol.interop.util.nextKeyPackage
import com.github.traderjoe95.mls.protocol.service.AuthenticationService
import kotlin.random.Random


class MyTest : FunSpec({

  test("My group test") {
    1 + 2 shouldBe 3

    /*val ciphersuite = CipherSuite.P256_AES128_SHA256_P256

    val signature_keys1 = ciphersuite.generateSignatureKeyPair()

    val key_package1 = Random.nextKeyPackage(ciphersuite, signature_keys1)*/



  }

})
