// Generated by the protocol buffer compiler. DO NOT EDIT!
// source: mls_client.proto

// Generated files should ignore deprecation warnings
@file:Suppress("DEPRECATION")
package com.github.traderjoe95.mls.interop.proto;

@kotlin.jvm.JvmName("-initializecreateSubgroupResponse")
public inline fun createSubgroupResponse(block: com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponseKt.Dsl.() -> kotlin.Unit): com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse =
  com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponseKt.Dsl._create(com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse.newBuilder()).apply { block() }._build()
/**
 * Protobuf type `mls_client.CreateSubgroupResponse`
 */
public object CreateSubgroupResponseKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse = _builder.build()

    /**
     * `uint32 state_id = 1;`
     */
    public var stateId: kotlin.Int
      @JvmName("getStateId")
      get() = _builder.getStateId()
      @JvmName("setStateId")
      set(value) {
        _builder.setStateId(value)
      }
    /**
     * `uint32 state_id = 1;`
     */
    public fun clearStateId() {
      _builder.clearStateId()
    }

    /**
     * `bytes welcome = 2;`
     */
    public var welcome: com.google.protobuf.ByteString
      @JvmName("getWelcome")
      get() = _builder.getWelcome()
      @JvmName("setWelcome")
      set(value) {
        _builder.setWelcome(value)
      }
    /**
     * `bytes welcome = 2;`
     */
    public fun clearWelcome() {
      _builder.clearWelcome()
    }

    /**
     * `bytes ratchet_tree = 3;`
     */
    public var ratchetTree: com.google.protobuf.ByteString
      @JvmName("getRatchetTree")
      get() = _builder.getRatchetTree()
      @JvmName("setRatchetTree")
      set(value) {
        _builder.setRatchetTree(value)
      }
    /**
     * `bytes ratchet_tree = 3;`
     */
    public fun clearRatchetTree() {
      _builder.clearRatchetTree()
    }

    /**
     * `bytes epoch_authenticator = 4;`
     */
    public var epochAuthenticator: com.google.protobuf.ByteString
      @JvmName("getEpochAuthenticator")
      get() = _builder.getEpochAuthenticator()
      @JvmName("setEpochAuthenticator")
      set(value) {
        _builder.setEpochAuthenticator(value)
      }
    /**
     * `bytes epoch_authenticator = 4;`
     */
    public fun clearEpochAuthenticator() {
      _builder.clearEpochAuthenticator()
    }
  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse.copy(block: `com.github.traderjoe95.mls.interop.proto`.CreateSubgroupResponseKt.Dsl.() -> kotlin.Unit): com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse =
  `com.github.traderjoe95.mls.interop.proto`.CreateSubgroupResponseKt.Dsl._create(this.toBuilder()).apply { block() }._build()

