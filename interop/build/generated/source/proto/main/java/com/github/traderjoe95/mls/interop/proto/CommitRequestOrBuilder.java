// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mls_client.proto

// Protobuf Java Version: 3.25.3
package com.github.traderjoe95.mls.interop.proto;

public interface CommitRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:mls_client.CommitRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint32 state_id = 1;</code>
   * @return The stateId.
   */
  int getStateId();

  /**
   * <code>repeated bytes by_reference = 2;</code>
   * @return A list containing the byReference.
   */
  java.util.List<com.google.protobuf.ByteString> getByReferenceList();
  /**
   * <code>repeated bytes by_reference = 2;</code>
   * @return The count of byReference.
   */
  int getByReferenceCount();
  /**
   * <code>repeated bytes by_reference = 2;</code>
   * @param index The index of the element to return.
   * @return The byReference at the given index.
   */
  com.google.protobuf.ByteString getByReference(int index);

  /**
   * <code>repeated .mls_client.ProposalDescription by_value = 3;</code>
   */
  java.util.List<com.github.traderjoe95.mls.interop.proto.ProposalDescription> 
      getByValueList();
  /**
   * <code>repeated .mls_client.ProposalDescription by_value = 3;</code>
   */
  com.github.traderjoe95.mls.interop.proto.ProposalDescription getByValue(int index);
  /**
   * <code>repeated .mls_client.ProposalDescription by_value = 3;</code>
   */
  int getByValueCount();
  /**
   * <code>repeated .mls_client.ProposalDescription by_value = 3;</code>
   */
  java.util.List<? extends com.github.traderjoe95.mls.interop.proto.ProposalDescriptionOrBuilder> 
      getByValueOrBuilderList();
  /**
   * <code>repeated .mls_client.ProposalDescription by_value = 3;</code>
   */
  com.github.traderjoe95.mls.interop.proto.ProposalDescriptionOrBuilder getByValueOrBuilder(
      int index);

  /**
   * <code>bool force_path = 4;</code>
   * @return The forcePath.
   */
  boolean getForcePath();

  /**
   * <code>bool external_tree = 5;</code>
   * @return The externalTree.
   */
  boolean getExternalTree();
}
