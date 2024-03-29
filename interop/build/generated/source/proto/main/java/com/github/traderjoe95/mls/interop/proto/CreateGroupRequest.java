// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mls_client.proto

// Protobuf Java Version: 3.25.3
package com.github.traderjoe95.mls.interop.proto;

/**
 * <pre>
 * rpc CreateGroup
 * XXX(RLB): Credential type is omitted; let's just use Basic for these tests
 * </pre>
 *
 * Protobuf type {@code mls_client.CreateGroupRequest}
 */
public final class CreateGroupRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:mls_client.CreateGroupRequest)
    CreateGroupRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use CreateGroupRequest.newBuilder() to construct.
  private CreateGroupRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private CreateGroupRequest() {
    groupId_ = com.google.protobuf.ByteString.EMPTY;
    identity_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new CreateGroupRequest();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_CreateGroupRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_CreateGroupRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.github.traderjoe95.mls.interop.proto.CreateGroupRequest.class, com.github.traderjoe95.mls.interop.proto.CreateGroupRequest.Builder.class);
  }

  public static final int GROUP_ID_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString groupId_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>bytes group_id = 1;</code>
   * @return The groupId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getGroupId() {
    return groupId_;
  }

  public static final int CIPHER_SUITE_FIELD_NUMBER = 2;
  private int cipherSuite_ = 0;
  /**
   * <pre>
   * Actually uint16
   * </pre>
   *
   * <code>uint32 cipher_suite = 2;</code>
   * @return The cipherSuite.
   */
  @java.lang.Override
  public int getCipherSuite() {
    return cipherSuite_;
  }

  public static final int ENCRYPT_HANDSHAKE_FIELD_NUMBER = 3;
  private boolean encryptHandshake_ = false;
  /**
   * <code>bool encrypt_handshake = 3;</code>
   * @return The encryptHandshake.
   */
  @java.lang.Override
  public boolean getEncryptHandshake() {
    return encryptHandshake_;
  }

  public static final int IDENTITY_FIELD_NUMBER = 4;
  private com.google.protobuf.ByteString identity_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>bytes identity = 4;</code>
   * @return The identity.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getIdentity() {
    return identity_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!groupId_.isEmpty()) {
      output.writeBytes(1, groupId_);
    }
    if (cipherSuite_ != 0) {
      output.writeUInt32(2, cipherSuite_);
    }
    if (encryptHandshake_ != false) {
      output.writeBool(3, encryptHandshake_);
    }
    if (!identity_.isEmpty()) {
      output.writeBytes(4, identity_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!groupId_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, groupId_);
    }
    if (cipherSuite_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(2, cipherSuite_);
    }
    if (encryptHandshake_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(3, encryptHandshake_);
    }
    if (!identity_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(4, identity_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.github.traderjoe95.mls.interop.proto.CreateGroupRequest)) {
      return super.equals(obj);
    }
    com.github.traderjoe95.mls.interop.proto.CreateGroupRequest other = (com.github.traderjoe95.mls.interop.proto.CreateGroupRequest) obj;

    if (!getGroupId()
        .equals(other.getGroupId())) return false;
    if (getCipherSuite()
        != other.getCipherSuite()) return false;
    if (getEncryptHandshake()
        != other.getEncryptHandshake()) return false;
    if (!getIdentity()
        .equals(other.getIdentity())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + GROUP_ID_FIELD_NUMBER;
    hash = (53 * hash) + getGroupId().hashCode();
    hash = (37 * hash) + CIPHER_SUITE_FIELD_NUMBER;
    hash = (53 * hash) + getCipherSuite();
    hash = (37 * hash) + ENCRYPT_HANDSHAKE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getEncryptHandshake());
    hash = (37 * hash) + IDENTITY_FIELD_NUMBER;
    hash = (53 * hash) + getIdentity().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.github.traderjoe95.mls.interop.proto.CreateGroupRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * rpc CreateGroup
   * XXX(RLB): Credential type is omitted; let's just use Basic for these tests
   * </pre>
   *
   * Protobuf type {@code mls_client.CreateGroupRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:mls_client.CreateGroupRequest)
      com.github.traderjoe95.mls.interop.proto.CreateGroupRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_CreateGroupRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_CreateGroupRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.traderjoe95.mls.interop.proto.CreateGroupRequest.class, com.github.traderjoe95.mls.interop.proto.CreateGroupRequest.Builder.class);
    }

    // Construct using com.github.traderjoe95.mls.interop.proto.CreateGroupRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      groupId_ = com.google.protobuf.ByteString.EMPTY;
      cipherSuite_ = 0;
      encryptHandshake_ = false;
      identity_ = com.google.protobuf.ByteString.EMPTY;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_CreateGroupRequest_descriptor;
    }

    @java.lang.Override
    public com.github.traderjoe95.mls.interop.proto.CreateGroupRequest getDefaultInstanceForType() {
      return com.github.traderjoe95.mls.interop.proto.CreateGroupRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.github.traderjoe95.mls.interop.proto.CreateGroupRequest build() {
      com.github.traderjoe95.mls.interop.proto.CreateGroupRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.github.traderjoe95.mls.interop.proto.CreateGroupRequest buildPartial() {
      com.github.traderjoe95.mls.interop.proto.CreateGroupRequest result = new com.github.traderjoe95.mls.interop.proto.CreateGroupRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.github.traderjoe95.mls.interop.proto.CreateGroupRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.groupId_ = groupId_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.cipherSuite_ = cipherSuite_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.encryptHandshake_ = encryptHandshake_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.identity_ = identity_;
      }
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.github.traderjoe95.mls.interop.proto.CreateGroupRequest) {
        return mergeFrom((com.github.traderjoe95.mls.interop.proto.CreateGroupRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.github.traderjoe95.mls.interop.proto.CreateGroupRequest other) {
      if (other == com.github.traderjoe95.mls.interop.proto.CreateGroupRequest.getDefaultInstance()) return this;
      if (other.getGroupId() != com.google.protobuf.ByteString.EMPTY) {
        setGroupId(other.getGroupId());
      }
      if (other.getCipherSuite() != 0) {
        setCipherSuite(other.getCipherSuite());
      }
      if (other.getEncryptHandshake() != false) {
        setEncryptHandshake(other.getEncryptHandshake());
      }
      if (other.getIdentity() != com.google.protobuf.ByteString.EMPTY) {
        setIdentity(other.getIdentity());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              groupId_ = input.readBytes();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 16: {
              cipherSuite_ = input.readUInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 24: {
              encryptHandshake_ = input.readBool();
              bitField0_ |= 0x00000004;
              break;
            } // case 24
            case 34: {
              identity_ = input.readBytes();
              bitField0_ |= 0x00000008;
              break;
            } // case 34
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.ByteString groupId_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes group_id = 1;</code>
     * @return The groupId.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getGroupId() {
      return groupId_;
    }
    /**
     * <code>bytes group_id = 1;</code>
     * @param value The groupId to set.
     * @return This builder for chaining.
     */
    public Builder setGroupId(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      groupId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>bytes group_id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearGroupId() {
      bitField0_ = (bitField0_ & ~0x00000001);
      groupId_ = getDefaultInstance().getGroupId();
      onChanged();
      return this;
    }

    private int cipherSuite_ ;
    /**
     * <pre>
     * Actually uint16
     * </pre>
     *
     * <code>uint32 cipher_suite = 2;</code>
     * @return The cipherSuite.
     */
    @java.lang.Override
    public int getCipherSuite() {
      return cipherSuite_;
    }
    /**
     * <pre>
     * Actually uint16
     * </pre>
     *
     * <code>uint32 cipher_suite = 2;</code>
     * @param value The cipherSuite to set.
     * @return This builder for chaining.
     */
    public Builder setCipherSuite(int value) {

      cipherSuite_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Actually uint16
     * </pre>
     *
     * <code>uint32 cipher_suite = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearCipherSuite() {
      bitField0_ = (bitField0_ & ~0x00000002);
      cipherSuite_ = 0;
      onChanged();
      return this;
    }

    private boolean encryptHandshake_ ;
    /**
     * <code>bool encrypt_handshake = 3;</code>
     * @return The encryptHandshake.
     */
    @java.lang.Override
    public boolean getEncryptHandshake() {
      return encryptHandshake_;
    }
    /**
     * <code>bool encrypt_handshake = 3;</code>
     * @param value The encryptHandshake to set.
     * @return This builder for chaining.
     */
    public Builder setEncryptHandshake(boolean value) {

      encryptHandshake_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>bool encrypt_handshake = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearEncryptHandshake() {
      bitField0_ = (bitField0_ & ~0x00000004);
      encryptHandshake_ = false;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString identity_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes identity = 4;</code>
     * @return The identity.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getIdentity() {
      return identity_;
    }
    /**
     * <code>bytes identity = 4;</code>
     * @param value The identity to set.
     * @return This builder for chaining.
     */
    public Builder setIdentity(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      identity_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <code>bytes identity = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearIdentity() {
      bitField0_ = (bitField0_ & ~0x00000008);
      identity_ = getDefaultInstance().getIdentity();
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:mls_client.CreateGroupRequest)
  }

  // @@protoc_insertion_point(class_scope:mls_client.CreateGroupRequest)
  private static final com.github.traderjoe95.mls.interop.proto.CreateGroupRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.github.traderjoe95.mls.interop.proto.CreateGroupRequest();
  }

  public static com.github.traderjoe95.mls.interop.proto.CreateGroupRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CreateGroupRequest>
      PARSER = new com.google.protobuf.AbstractParser<CreateGroupRequest>() {
    @java.lang.Override
    public CreateGroupRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<CreateGroupRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CreateGroupRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.github.traderjoe95.mls.interop.proto.CreateGroupRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

