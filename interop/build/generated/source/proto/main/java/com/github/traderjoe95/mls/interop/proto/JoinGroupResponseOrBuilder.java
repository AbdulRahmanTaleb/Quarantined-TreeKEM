// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: mls_client.proto

// Protobuf Java Version: 3.25.3
package com.github.traderjoe95.mls.interop.proto;

public interface JoinGroupResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:mls_client.JoinGroupResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint32 state_id = 1;</code>
   * @return The stateId.
   */
  int getStateId();

  /**
   * <code>bytes epoch_authenticator = 2;</code>
   * @return The epochAuthenticator.
   */
  com.google.protobuf.ByteString getEpochAuthenticator();
}
