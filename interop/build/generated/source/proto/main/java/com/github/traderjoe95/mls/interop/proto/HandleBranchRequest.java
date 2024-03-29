// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mls_client.proto

// Protobuf Java Version: 3.25.3
package com.github.traderjoe95.mls.interop.proto;

/**
 * <pre>
 * rpc CreateBranch
 * </pre>
 *
 * Protobuf type {@code mls_client.HandleBranchRequest}
 */
public final class HandleBranchRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:mls_client.HandleBranchRequest)
    HandleBranchRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use HandleBranchRequest.newBuilder() to construct.
  private HandleBranchRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private HandleBranchRequest() {
    welcome_ = com.google.protobuf.ByteString.EMPTY;
    ratchetTree_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new HandleBranchRequest();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_HandleBranchRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_HandleBranchRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.github.traderjoe95.mls.interop.proto.HandleBranchRequest.class, com.github.traderjoe95.mls.interop.proto.HandleBranchRequest.Builder.class);
  }

  public static final int STATE_ID_FIELD_NUMBER = 1;
  private int stateId_ = 0;
  /**
   * <code>uint32 state_id = 1;</code>
   * @return The stateId.
   */
  @java.lang.Override
  public int getStateId() {
    return stateId_;
  }

  public static final int TRANSACTION_ID_FIELD_NUMBER = 2;
  private int transactionId_ = 0;
  /**
   * <code>uint32 transaction_id = 2;</code>
   * @return The transactionId.
   */
  @java.lang.Override
  public int getTransactionId() {
    return transactionId_;
  }

  public static final int WELCOME_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString welcome_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>bytes welcome = 3;</code>
   * @return The welcome.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getWelcome() {
    return welcome_;
  }

  public static final int RATCHET_TREE_FIELD_NUMBER = 4;
  private com.google.protobuf.ByteString ratchetTree_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>bytes ratchet_tree = 4;</code>
   * @return The ratchetTree.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getRatchetTree() {
    return ratchetTree_;
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
    if (stateId_ != 0) {
      output.writeUInt32(1, stateId_);
    }
    if (transactionId_ != 0) {
      output.writeUInt32(2, transactionId_);
    }
    if (!welcome_.isEmpty()) {
      output.writeBytes(3, welcome_);
    }
    if (!ratchetTree_.isEmpty()) {
      output.writeBytes(4, ratchetTree_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (stateId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(1, stateId_);
    }
    if (transactionId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(2, transactionId_);
    }
    if (!welcome_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, welcome_);
    }
    if (!ratchetTree_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(4, ratchetTree_);
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
    if (!(obj instanceof com.github.traderjoe95.mls.interop.proto.HandleBranchRequest)) {
      return super.equals(obj);
    }
    com.github.traderjoe95.mls.interop.proto.HandleBranchRequest other = (com.github.traderjoe95.mls.interop.proto.HandleBranchRequest) obj;

    if (getStateId()
        != other.getStateId()) return false;
    if (getTransactionId()
        != other.getTransactionId()) return false;
    if (!getWelcome()
        .equals(other.getWelcome())) return false;
    if (!getRatchetTree()
        .equals(other.getRatchetTree())) return false;
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
    hash = (37 * hash) + STATE_ID_FIELD_NUMBER;
    hash = (53 * hash) + getStateId();
    hash = (37 * hash) + TRANSACTION_ID_FIELD_NUMBER;
    hash = (53 * hash) + getTransactionId();
    hash = (37 * hash) + WELCOME_FIELD_NUMBER;
    hash = (53 * hash) + getWelcome().hashCode();
    hash = (37 * hash) + RATCHET_TREE_FIELD_NUMBER;
    hash = (53 * hash) + getRatchetTree().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest parseFrom(
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
  public static Builder newBuilder(com.github.traderjoe95.mls.interop.proto.HandleBranchRequest prototype) {
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
   * rpc CreateBranch
   * </pre>
   *
   * Protobuf type {@code mls_client.HandleBranchRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:mls_client.HandleBranchRequest)
      com.github.traderjoe95.mls.interop.proto.HandleBranchRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_HandleBranchRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_HandleBranchRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.traderjoe95.mls.interop.proto.HandleBranchRequest.class, com.github.traderjoe95.mls.interop.proto.HandleBranchRequest.Builder.class);
    }

    // Construct using com.github.traderjoe95.mls.interop.proto.HandleBranchRequest.newBuilder()
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
      stateId_ = 0;
      transactionId_ = 0;
      welcome_ = com.google.protobuf.ByteString.EMPTY;
      ratchetTree_ = com.google.protobuf.ByteString.EMPTY;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_HandleBranchRequest_descriptor;
    }

    @java.lang.Override
    public com.github.traderjoe95.mls.interop.proto.HandleBranchRequest getDefaultInstanceForType() {
      return com.github.traderjoe95.mls.interop.proto.HandleBranchRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.github.traderjoe95.mls.interop.proto.HandleBranchRequest build() {
      com.github.traderjoe95.mls.interop.proto.HandleBranchRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.github.traderjoe95.mls.interop.proto.HandleBranchRequest buildPartial() {
      com.github.traderjoe95.mls.interop.proto.HandleBranchRequest result = new com.github.traderjoe95.mls.interop.proto.HandleBranchRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.github.traderjoe95.mls.interop.proto.HandleBranchRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.stateId_ = stateId_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.transactionId_ = transactionId_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.welcome_ = welcome_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.ratchetTree_ = ratchetTree_;
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
      if (other instanceof com.github.traderjoe95.mls.interop.proto.HandleBranchRequest) {
        return mergeFrom((com.github.traderjoe95.mls.interop.proto.HandleBranchRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.github.traderjoe95.mls.interop.proto.HandleBranchRequest other) {
      if (other == com.github.traderjoe95.mls.interop.proto.HandleBranchRequest.getDefaultInstance()) return this;
      if (other.getStateId() != 0) {
        setStateId(other.getStateId());
      }
      if (other.getTransactionId() != 0) {
        setTransactionId(other.getTransactionId());
      }
      if (other.getWelcome() != com.google.protobuf.ByteString.EMPTY) {
        setWelcome(other.getWelcome());
      }
      if (other.getRatchetTree() != com.google.protobuf.ByteString.EMPTY) {
        setRatchetTree(other.getRatchetTree());
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
            case 8: {
              stateId_ = input.readUInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              transactionId_ = input.readUInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              welcome_ = input.readBytes();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              ratchetTree_ = input.readBytes();
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

    private int stateId_ ;
    /**
     * <code>uint32 state_id = 1;</code>
     * @return The stateId.
     */
    @java.lang.Override
    public int getStateId() {
      return stateId_;
    }
    /**
     * <code>uint32 state_id = 1;</code>
     * @param value The stateId to set.
     * @return This builder for chaining.
     */
    public Builder setStateId(int value) {

      stateId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 state_id = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearStateId() {
      bitField0_ = (bitField0_ & ~0x00000001);
      stateId_ = 0;
      onChanged();
      return this;
    }

    private int transactionId_ ;
    /**
     * <code>uint32 transaction_id = 2;</code>
     * @return The transactionId.
     */
    @java.lang.Override
    public int getTransactionId() {
      return transactionId_;
    }
    /**
     * <code>uint32 transaction_id = 2;</code>
     * @param value The transactionId to set.
     * @return This builder for chaining.
     */
    public Builder setTransactionId(int value) {

      transactionId_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 transaction_id = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearTransactionId() {
      bitField0_ = (bitField0_ & ~0x00000002);
      transactionId_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString welcome_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes welcome = 3;</code>
     * @return The welcome.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getWelcome() {
      return welcome_;
    }
    /**
     * <code>bytes welcome = 3;</code>
     * @param value The welcome to set.
     * @return This builder for chaining.
     */
    public Builder setWelcome(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      welcome_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>bytes welcome = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearWelcome() {
      bitField0_ = (bitField0_ & ~0x00000004);
      welcome_ = getDefaultInstance().getWelcome();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString ratchetTree_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes ratchet_tree = 4;</code>
     * @return The ratchetTree.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getRatchetTree() {
      return ratchetTree_;
    }
    /**
     * <code>bytes ratchet_tree = 4;</code>
     * @param value The ratchetTree to set.
     * @return This builder for chaining.
     */
    public Builder setRatchetTree(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      ratchetTree_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <code>bytes ratchet_tree = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearRatchetTree() {
      bitField0_ = (bitField0_ & ~0x00000008);
      ratchetTree_ = getDefaultInstance().getRatchetTree();
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


    // @@protoc_insertion_point(builder_scope:mls_client.HandleBranchRequest)
  }

  // @@protoc_insertion_point(class_scope:mls_client.HandleBranchRequest)
  private static final com.github.traderjoe95.mls.interop.proto.HandleBranchRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.github.traderjoe95.mls.interop.proto.HandleBranchRequest();
  }

  public static com.github.traderjoe95.mls.interop.proto.HandleBranchRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<HandleBranchRequest>
      PARSER = new com.google.protobuf.AbstractParser<HandleBranchRequest>() {
    @java.lang.Override
    public HandleBranchRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<HandleBranchRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<HandleBranchRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.github.traderjoe95.mls.interop.proto.HandleBranchRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

