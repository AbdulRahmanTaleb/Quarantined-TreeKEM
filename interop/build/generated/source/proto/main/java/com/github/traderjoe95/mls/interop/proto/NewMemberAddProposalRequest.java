// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mls_client.proto

// Protobuf Java Version: 3.25.3
package com.github.traderjoe95.mls.interop.proto;

/**
 * <pre>
 * rpc NewMemberAddProposal
 * </pre>
 *
 * Protobuf type {@code mls_client.NewMemberAddProposalRequest}
 */
public final class NewMemberAddProposalRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:mls_client.NewMemberAddProposalRequest)
    NewMemberAddProposalRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use NewMemberAddProposalRequest.newBuilder() to construct.
  private NewMemberAddProposalRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private NewMemberAddProposalRequest() {
    groupInfo_ = com.google.protobuf.ByteString.EMPTY;
    identity_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new NewMemberAddProposalRequest();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_NewMemberAddProposalRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_NewMemberAddProposalRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest.class, com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest.Builder.class);
  }

  public static final int GROUP_INFO_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString groupInfo_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>bytes group_info = 1;</code>
   * @return The groupInfo.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getGroupInfo() {
    return groupInfo_;
  }

  public static final int IDENTITY_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString identity_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>bytes identity = 2;</code>
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
    if (!groupInfo_.isEmpty()) {
      output.writeBytes(1, groupInfo_);
    }
    if (!identity_.isEmpty()) {
      output.writeBytes(2, identity_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!groupInfo_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, groupInfo_);
    }
    if (!identity_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, identity_);
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
    if (!(obj instanceof com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest)) {
      return super.equals(obj);
    }
    com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest other = (com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest) obj;

    if (!getGroupInfo()
        .equals(other.getGroupInfo())) return false;
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
    hash = (37 * hash) + GROUP_INFO_FIELD_NUMBER;
    hash = (53 * hash) + getGroupInfo().hashCode();
    hash = (37 * hash) + IDENTITY_FIELD_NUMBER;
    hash = (53 * hash) + getIdentity().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest parseFrom(
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
  public static Builder newBuilder(com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest prototype) {
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
   * rpc NewMemberAddProposal
   * </pre>
   *
   * Protobuf type {@code mls_client.NewMemberAddProposalRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:mls_client.NewMemberAddProposalRequest)
      com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_NewMemberAddProposalRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_NewMemberAddProposalRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest.class, com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest.Builder.class);
    }

    // Construct using com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest.newBuilder()
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
      groupInfo_ = com.google.protobuf.ByteString.EMPTY;
      identity_ = com.google.protobuf.ByteString.EMPTY;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.internal_static_mls_client_NewMemberAddProposalRequest_descriptor;
    }

    @java.lang.Override
    public com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest getDefaultInstanceForType() {
      return com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest build() {
      com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest buildPartial() {
      com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest result = new com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.groupInfo_ = groupInfo_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
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
      if (other instanceof com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest) {
        return mergeFrom((com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest other) {
      if (other == com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest.getDefaultInstance()) return this;
      if (other.getGroupInfo() != com.google.protobuf.ByteString.EMPTY) {
        setGroupInfo(other.getGroupInfo());
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
              groupInfo_ = input.readBytes();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              identity_ = input.readBytes();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
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

    private com.google.protobuf.ByteString groupInfo_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes group_info = 1;</code>
     * @return The groupInfo.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getGroupInfo() {
      return groupInfo_;
    }
    /**
     * <code>bytes group_info = 1;</code>
     * @param value The groupInfo to set.
     * @return This builder for chaining.
     */
    public Builder setGroupInfo(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      groupInfo_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>bytes group_info = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearGroupInfo() {
      bitField0_ = (bitField0_ & ~0x00000001);
      groupInfo_ = getDefaultInstance().getGroupInfo();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString identity_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes identity = 2;</code>
     * @return The identity.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getIdentity() {
      return identity_;
    }
    /**
     * <code>bytes identity = 2;</code>
     * @param value The identity to set.
     * @return This builder for chaining.
     */
    public Builder setIdentity(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      identity_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>bytes identity = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearIdentity() {
      bitField0_ = (bitField0_ & ~0x00000002);
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


    // @@protoc_insertion_point(builder_scope:mls_client.NewMemberAddProposalRequest)
  }

  // @@protoc_insertion_point(class_scope:mls_client.NewMemberAddProposalRequest)
  private static final com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest();
  }

  public static com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<NewMemberAddProposalRequest>
      PARSER = new com.google.protobuf.AbstractParser<NewMemberAddProposalRequest>() {
    @java.lang.Override
    public NewMemberAddProposalRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<NewMemberAddProposalRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<NewMemberAddProposalRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

