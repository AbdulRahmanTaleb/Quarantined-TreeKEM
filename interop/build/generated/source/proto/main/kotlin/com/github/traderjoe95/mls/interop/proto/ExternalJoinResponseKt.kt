// Generated by the protocol buffer compiler. DO NOT EDIT!
// source: mls_client.proto

// Generated files should ignore deprecation warnings
@file:Suppress("DEPRECATION")
package com.github.traderjoe95.mls.interop.proto;

@kotlin.jvm.JvmName("-initializeexternalJoinResponse")
public inline fun externalJoinResponse(block: com.github.traderjoe95.mls.interop.proto.ExternalJoinResponseKt.Dsl.() -> kotlin.Unit): com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse =
  com.github.traderjoe95.mls.interop.proto.ExternalJoinResponseKt.Dsl._create(com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse.newBuilder()).apply { block() }._build()
/**
 * Protobuf type `mls_client.ExternalJoinResponse`
 */
public object ExternalJoinResponseKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse = _builder.build()

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
     * `bytes commit = 2;`
     */
    public var commit: com.google.protobuf.ByteString
      @JvmName("getCommit")
      get() = _builder.getCommit()
      @JvmName("setCommit")
      set(value) {
        _builder.setCommit(value)
      }
    /**
     * `bytes commit = 2;`
     */
    public fun clearCommit() {
      _builder.clearCommit()
    }

    /**
     * `bytes epoch_authenticator = 3;`
     */
    public var epochAuthenticator: com.google.protobuf.ByteString
      @JvmName("getEpochAuthenticator")
      get() = _builder.getEpochAuthenticator()
      @JvmName("setEpochAuthenticator")
      set(value) {
        _builder.setEpochAuthenticator(value)
      }
    /**
     * `bytes epoch_authenticator = 3;`
     */
    public fun clearEpochAuthenticator() {
      _builder.clearEpochAuthenticator()
    }
  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse.copy(block: `com.github.traderjoe95.mls.interop.proto`.ExternalJoinResponseKt.Dsl.() -> kotlin.Unit): com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse =
  `com.github.traderjoe95.mls.interop.proto`.ExternalJoinResponseKt.Dsl._create(this.toBuilder()).apply { block() }._build()

