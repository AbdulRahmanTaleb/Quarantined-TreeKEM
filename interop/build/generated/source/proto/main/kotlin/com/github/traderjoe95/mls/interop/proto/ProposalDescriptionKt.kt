// Generated by the protocol buffer compiler. DO NOT EDIT!
// source: mls_client.proto

// Generated files should ignore deprecation warnings
@file:Suppress("DEPRECATION")
package com.github.traderjoe95.mls.interop.proto;

@kotlin.jvm.JvmName("-initializeproposalDescription")
public inline fun proposalDescription(block: com.github.traderjoe95.mls.interop.proto.ProposalDescriptionKt.Dsl.() -> kotlin.Unit): com.github.traderjoe95.mls.interop.proto.ProposalDescription =
  com.github.traderjoe95.mls.interop.proto.ProposalDescriptionKt.Dsl._create(com.github.traderjoe95.mls.interop.proto.ProposalDescription.newBuilder()).apply { block() }._build()
/**
 * ```
 * `proposal_type` is one of "add", "remove", "externalPSK", "resumptionPSK",
 * "groupContextExtensions", "reinit". The type "reinit" can only be used in
 * rpc ExternalSignerProposal.
 * ```
 *
 * Protobuf type `mls_client.ProposalDescription`
 */
public object ProposalDescriptionKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.github.traderjoe95.mls.interop.proto.ProposalDescription.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.github.traderjoe95.mls.interop.proto.ProposalDescription.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.github.traderjoe95.mls.interop.proto.ProposalDescription = _builder.build()

    /**
     * `bytes proposal_type = 1;`
     */
    public var proposalType: com.google.protobuf.ByteString
      @JvmName("getProposalType")
      get() = _builder.getProposalType()
      @JvmName("setProposalType")
      set(value) {
        _builder.setProposalType(value)
      }
    /**
     * `bytes proposal_type = 1;`
     */
    public fun clearProposalType() {
      _builder.clearProposalType()
    }

    /**
     * ```
     * Required if proposal_type is "add"
     * ```
     *
     * `bytes key_package = 2;`
     */
    public var keyPackage: com.google.protobuf.ByteString
      @JvmName("getKeyPackage")
      get() = _builder.getKeyPackage()
      @JvmName("setKeyPackage")
      set(value) {
        _builder.setKeyPackage(value)
      }
    /**
     * ```
     * Required if proposal_type is "add"
     * ```
     *
     * `bytes key_package = 2;`
     */
    public fun clearKeyPackage() {
      _builder.clearKeyPackage()
    }

    /**
     * ```
     * Required if proposal_type is "remove"
     * ```
     *
     * `bytes removed_id = 3;`
     */
    public var removedId: com.google.protobuf.ByteString
      @JvmName("getRemovedId")
      get() = _builder.getRemovedId()
      @JvmName("setRemovedId")
      set(value) {
        _builder.setRemovedId(value)
      }
    /**
     * ```
     * Required if proposal_type is "remove"
     * ```
     *
     * `bytes removed_id = 3;`
     */
    public fun clearRemovedId() {
      _builder.clearRemovedId()
    }

    /**
     * ```
     * Required if proposal_type is "externalPSK"
     * ```
     *
     * `bytes psk_id = 4;`
     */
    public var pskId: com.google.protobuf.ByteString
      @JvmName("getPskId")
      get() = _builder.getPskId()
      @JvmName("setPskId")
      set(value) {
        _builder.setPskId(value)
      }
    /**
     * ```
     * Required if proposal_type is "externalPSK"
     * ```
     *
     * `bytes psk_id = 4;`
     */
    public fun clearPskId() {
      _builder.clearPskId()
    }

    /**
     * ```
     * Required if proposal_type is "resumptionPSK"
     * ```
     *
     * `uint64 epoch_id = 5;`
     */
    public var epochId: kotlin.Long
      @JvmName("getEpochId")
      get() = _builder.getEpochId()
      @JvmName("setEpochId")
      set(value) {
        _builder.setEpochId(value)
      }
    /**
     * ```
     * Required if proposal_type is "resumptionPSK"
     * ```
     *
     * `uint64 epoch_id = 5;`
     */
    public fun clearEpochId() {
      _builder.clearEpochId()
    }

    /**
     * An uninstantiable, behaviorless type to represent the field in
     * generics.
     */
    @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
    public class ExtensionsProxy private constructor() : com.google.protobuf.kotlin.DslProxy()
    /**
     * ```
     * Required if proposal_type is "groupContextExtensions" or "reinit"
     * ```
     *
     * `repeated .mls_client.Extension extensions = 6;`
     */
     public val extensions: com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.Extension, ExtensionsProxy>
      @kotlin.jvm.JvmSynthetic
      get() = com.google.protobuf.kotlin.DslList(
        _builder.getExtensionsList()
      )
    /**
     * ```
     * Required if proposal_type is "groupContextExtensions" or "reinit"
     * ```
     *
     * `repeated .mls_client.Extension extensions = 6;`
     * @param value The extensions to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addExtensions")
    public fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.Extension, ExtensionsProxy>.add(value: com.github.traderjoe95.mls.interop.proto.Extension) {
      _builder.addExtensions(value)
    }
    /**
     * ```
     * Required if proposal_type is "groupContextExtensions" or "reinit"
     * ```
     *
     * `repeated .mls_client.Extension extensions = 6;`
     * @param value The extensions to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignExtensions")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.Extension, ExtensionsProxy>.plusAssign(value: com.github.traderjoe95.mls.interop.proto.Extension) {
      add(value)
    }
    /**
     * ```
     * Required if proposal_type is "groupContextExtensions" or "reinit"
     * ```
     *
     * `repeated .mls_client.Extension extensions = 6;`
     * @param values The extensions to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("addAllExtensions")
    public fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.Extension, ExtensionsProxy>.addAll(values: kotlin.collections.Iterable<com.github.traderjoe95.mls.interop.proto.Extension>) {
      _builder.addAllExtensions(values)
    }
    /**
     * ```
     * Required if proposal_type is "groupContextExtensions" or "reinit"
     * ```
     *
     * `repeated .mls_client.Extension extensions = 6;`
     * @param values The extensions to add.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("plusAssignAllExtensions")
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.Extension, ExtensionsProxy>.plusAssign(values: kotlin.collections.Iterable<com.github.traderjoe95.mls.interop.proto.Extension>) {
      addAll(values)
    }
    /**
     * ```
     * Required if proposal_type is "groupContextExtensions" or "reinit"
     * ```
     *
     * `repeated .mls_client.Extension extensions = 6;`
     * @param index The index to set the value at.
     * @param value The extensions to set.
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("setExtensions")
    public operator fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.Extension, ExtensionsProxy>.set(index: kotlin.Int, value: com.github.traderjoe95.mls.interop.proto.Extension) {
      _builder.setExtensions(index, value)
    }
    /**
     * ```
     * Required if proposal_type is "groupContextExtensions" or "reinit"
     * ```
     *
     * `repeated .mls_client.Extension extensions = 6;`
     */
    @kotlin.jvm.JvmSynthetic
    @kotlin.jvm.JvmName("clearExtensions")
    public fun com.google.protobuf.kotlin.DslList<com.github.traderjoe95.mls.interop.proto.Extension, ExtensionsProxy>.clear() {
      _builder.clearExtensions()
    }


    /**
     * ```
     * Required if proposal_type is "reinit"
     * ```
     *
     * `bytes group_id = 7;`
     */
    public var groupId: com.google.protobuf.ByteString
      @JvmName("getGroupId")
      get() = _builder.getGroupId()
      @JvmName("setGroupId")
      set(value) {
        _builder.setGroupId(value)
      }
    /**
     * ```
     * Required if proposal_type is "reinit"
     * ```
     *
     * `bytes group_id = 7;`
     */
    public fun clearGroupId() {
      _builder.clearGroupId()
    }

    /**
     * ```
     * Required if proposal_type is "reinit"
     * ```
     *
     * `uint32 cipher_suite = 8;`
     */
    public var cipherSuite: kotlin.Int
      @JvmName("getCipherSuite")
      get() = _builder.getCipherSuite()
      @JvmName("setCipherSuite")
      set(value) {
        _builder.setCipherSuite(value)
      }
    /**
     * ```
     * Required if proposal_type is "reinit"
     * ```
     *
     * `uint32 cipher_suite = 8;`
     */
    public fun clearCipherSuite() {
      _builder.clearCipherSuite()
    }
  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.github.traderjoe95.mls.interop.proto.ProposalDescription.copy(block: `com.github.traderjoe95.mls.interop.proto`.ProposalDescriptionKt.Dsl.() -> kotlin.Unit): com.github.traderjoe95.mls.interop.proto.ProposalDescription =
  `com.github.traderjoe95.mls.interop.proto`.ProposalDescriptionKt.Dsl._create(this.toBuilder()).apply { block() }._build()

