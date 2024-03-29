// Generated by the protocol buffer compiler. DO NOT EDIT!
// source: mls_client.proto

// Generated files should ignore deprecation warnings
@file:Suppress("DEPRECATION")
package com.github.traderjoe95.mls.interop.proto;

@kotlin.jvm.JvmName("-initializeexternalJoinRequest")
public inline fun externalJoinRequest(block: com.github.traderjoe95.mls.interop.proto.ExternalJoinRequestKt.Dsl.() -> kotlin.Unit): com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest =
  com.github.traderjoe95.mls.interop.proto.ExternalJoinRequestKt.Dsl._create(com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest.newBuilder()).apply { block() }._build()
/**
 * Protobuf type `mls_client.ExternalJoinRequest`
 */
public object ExternalJoinRequestKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest = _builder.build()

    /**
     * `bytes group_info = 1;`
     */
    public var groupInfo: com.google.protobuf.ByteString
      @JvmName("getGroupInfo")
      get() = _builder.getGroupInfo()
      @JvmName("setGroupInfo")
      set(value) {
        _builder.setGroupInfo(value)
      }
    /**
     * `bytes group_info = 1;`
     */
    public fun clearGroupInfo() {
      _builder.clearGroupInfo()
    }

    /**
     * `bytes ratchet_tree = 2;`
     */
    public var ratchetTree: com.google.protobuf.ByteString
      @JvmName("getRatchetTree")
      get() = _builder.getRatchetTree()
      @JvmName("setRatchetTree")
      set(value) {
        _builder.setRatchetTree(value)
      }
    /**
     * `bytes ratchet_tree = 2;`
     */
    public fun clearRatchetTree() {
      _builder.clearRatchetTree()
    }

    /**
     * `bool encrypt_handshake = 3;`
     */
    public var encryptHandshake: kotlin.Boolean
      @JvmName("getEncryptHandshake")
      get() = _builder.getEncryptHandshake()
      @JvmName("setEncryptHandshake")
      set(value) {
        _builder.setEncryptHandshake(value)
      }
    /**
     * `bool encrypt_handshake = 3;`
     */
    public fun clearEncryptHandshake() {
      _builder.clearEncryptHandshake()
    }

    /**
     * `bytes identity = 4;`
     */
    public var identity: com.google.protobuf.ByteString
      @JvmName("getIdentity")
      get() = _builder.getIdentity()
      @JvmName("setIdentity")
      set(value) {
        _builder.setIdentity(value)
      }
    /**
     * `bytes identity = 4;`
     */
    public fun clearIdentity() {
      _builder.clearIdentity()
    }

    /**
     * `bool remove_prior = 5;`
     */
    public var removePrior: kotlin.Boolean
      @JvmName("getRemovePrior")
      get() = _builder.getRemovePrior()
      @JvmName("setRemovePrior")
      set(value) {
        _builder.setRemovePrior(value)
      }
    /**
     * `bool remove_prior = 5;`
     */
    public fun clearRemovePrior() {
      _builder.clearRemovePrior()
    }

    /**
     * An uninstantiable, behaviorless type to represent the field in
     * generics.
     */
    @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
    public class PsksProxy private constructor() : com.google.protobuf.kotlin.DslProxy()
    /**
     * `repeated .mls_client.PreSharedKey psks = 6;`
     */
     public val psks: com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.PreSharedKey, PsksProxy>
      @kotlin.jvm.JvmSynthetic
      get() = com.google.protobuf.kotlin.DslList(
        _builder.getPsksList()
      )
    /**
     * `repeated .mls_client.PreSharedKey psks = 6;`
     * @param value The psks to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addPsks")
    public fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.PreSharedKey, PsksProxy>.add(value: com.github.traderjoe95.mls.interop.proto.PreSharedKey) {
      _builder.addPsks(value)
    }
    /**
     * `repeated .mls_client.PreSharedKey psks = 6;`
     * @param value The psks to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignPsks")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.PreSharedKey, PsksProxy>.plusAssign(value: com.github.traderjoe95.mls.interop.proto.PreSharedKey) {
      add(value)
    }
    /**
     * `repeated .mls_client.PreSharedKey psks = 6;`
     * @param values The psks to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addAllPsks")
    public fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.PreSharedKey, PsksProxy>.addAll(values: kotlin.collections.Iterable<com.github.traderjoe95.mls.interop.proto.PreSharedKey>) {
      _builder.addAllPsks(values)
    }
    /**
     * `repeated .mls_client.PreSharedKey psks = 6;`
     * @param values The psks to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignAllPsks")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.PreSharedKey, PsksProxy>.plusAssign(values: kotlin.collections.Iterable<com.github.traderjoe95.mls.interop.proto.PreSharedKey>) {
      addAll(values)
    }
    /**
     * `repeated .mls_client.PreSharedKey psks = 6;`
     * @param index The index to set the value at.
     * @param value The psks to set.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("setPsks")
    public operator fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.PreSharedKey, PsksProxy>.set(index: kotlin.Int, value: com.github.traderjoe95.mls.interop.proto.PreSharedKey) {
      _builder.setPsks(index, value)
    }
    /**
     * `repeated .mls_client.PreSharedKey psks = 6;`
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("clearPsks")
    public fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.PreSharedKey, PsksProxy>.clear() {
      _builder.clearPsks()
    }

  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest.copy(block: `com.github.traderjoe95.mls.interop.proto`.ExternalJoinRequestKt.Dsl.() -> kotlin.Unit): com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest =
  `com.github.traderjoe95.mls.interop.proto`.ExternalJoinRequestKt.Dsl._create(this.toBuilder()).apply { block() }._build()

