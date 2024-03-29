// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mls_client.proto

// Protobuf Java Version: 3.25.3
package com.github.traderjoe95.mls.interop.proto;

public interface ExternalSignerProposalRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:mls_client.ExternalSignerProposalRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint32 signer_id = 1;</code>
   * @return The signerId.
   */
  int getSignerId();

  /**
   * <code>uint32 signer_index = 2;</code>
   * @return The signerIndex.
   */
  int getSignerIndex();

  /**
   * <code>bytes group_info = 3;</code>
   * @return The groupInfo.
   */
  com.google.protobuf.ByteString getGroupInfo();

  /**
   * <code>bytes ratchet_tree = 4;</code>
   * @return The ratchetTree.
   */
  com.google.protobuf.ByteString getRatchetTree();

  /**
   * <code>.mls_client.ProposalDescription description = 5;</code>
   * @return Whether the description field is set.
   */
  boolean hasDescription();
  /**
   * <code>.mls_client.ProposalDescription description = 5;</code>
   * @return The description.
   */
  com.github.traderjoe95.mls.interop.proto.ProposalDescription getDescription();
  /**
   * <code>.mls_client.ProposalDescription description = 5;</code>
   */
  com.github.traderjoe95.mls.interop.proto.ProposalDescriptionOrBuilder getDescriptionOrBuilder();
}
