// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mls_client.proto

// Protobuf Java Version: 3.25.3
package com.github.traderjoe95.mls.interop.proto;

public interface CommitResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:mls_client.CommitResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bytes commit = 1;</code>
   * @return The commit.
   */
  com.google.protobuf.ByteString getCommit();

  /**
   * <code>bytes welcome = 2;</code>
   * @return The welcome.
   */
  com.google.protobuf.ByteString getWelcome();

  /**
   * <code>bytes ratchet_tree = 3;</code>
   * @return The ratchetTree.
   */
  com.google.protobuf.ByteString getRatchetTree();
}
