package com.github.traderjoe95.mls.interop.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * A wrapper around an MLS client implementation
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: mls_client.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MLSClientGrpc {

  private MLSClientGrpc() {}

  public static final java.lang.String SERVICE_NAME = "mls_client.MLSClient";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.NameRequest,
      com.github.traderjoe95.mls.interop.proto.NameResponse> getNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Name",
      requestType = com.github.traderjoe95.mls.interop.proto.NameRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.NameResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.NameRequest,
      com.github.traderjoe95.mls.interop.proto.NameResponse> getNameMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.NameRequest, com.github.traderjoe95.mls.interop.proto.NameResponse> getNameMethod;
    if ((getNameMethod = MLSClientGrpc.getNameMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getNameMethod = MLSClientGrpc.getNameMethod) == null) {
          MLSClientGrpc.getNameMethod = getNameMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.NameRequest, com.github.traderjoe95.mls.interop.proto.NameResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Name"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.NameRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.NameResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("Name"))
              .build();
        }
      }
    }
    return getNameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest,
      com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> getSupportedCiphersuitesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SupportedCiphersuites",
      requestType = com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest,
      com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> getSupportedCiphersuitesMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest, com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> getSupportedCiphersuitesMethod;
    if ((getSupportedCiphersuitesMethod = MLSClientGrpc.getSupportedCiphersuitesMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getSupportedCiphersuitesMethod = MLSClientGrpc.getSupportedCiphersuitesMethod) == null) {
          MLSClientGrpc.getSupportedCiphersuitesMethod = getSupportedCiphersuitesMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest, com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SupportedCiphersuites"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("SupportedCiphersuites"))
              .build();
        }
      }
    }
    return getSupportedCiphersuitesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateGroupRequest,
      com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> getCreateGroupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateGroup",
      requestType = com.github.traderjoe95.mls.interop.proto.CreateGroupRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.CreateGroupResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateGroupRequest,
      com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> getCreateGroupMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateGroupRequest, com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> getCreateGroupMethod;
    if ((getCreateGroupMethod = MLSClientGrpc.getCreateGroupMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getCreateGroupMethod = MLSClientGrpc.getCreateGroupMethod) == null) {
          MLSClientGrpc.getCreateGroupMethod = getCreateGroupMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.CreateGroupRequest, com.github.traderjoe95.mls.interop.proto.CreateGroupResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateGroup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CreateGroupRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CreateGroupResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("CreateGroup"))
              .build();
        }
      }
    }
    return getCreateGroupMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest,
      com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> getCreateKeyPackageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateKeyPackage",
      requestType = com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest,
      com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> getCreateKeyPackageMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest, com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> getCreateKeyPackageMethod;
    if ((getCreateKeyPackageMethod = MLSClientGrpc.getCreateKeyPackageMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getCreateKeyPackageMethod = MLSClientGrpc.getCreateKeyPackageMethod) == null) {
          MLSClientGrpc.getCreateKeyPackageMethod = getCreateKeyPackageMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest, com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateKeyPackage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("CreateKeyPackage"))
              .build();
        }
      }
    }
    return getCreateKeyPackageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.JoinGroupRequest,
      com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> getJoinGroupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "JoinGroup",
      requestType = com.github.traderjoe95.mls.interop.proto.JoinGroupRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.JoinGroupResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.JoinGroupRequest,
      com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> getJoinGroupMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.JoinGroupRequest, com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> getJoinGroupMethod;
    if ((getJoinGroupMethod = MLSClientGrpc.getJoinGroupMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getJoinGroupMethod = MLSClientGrpc.getJoinGroupMethod) == null) {
          MLSClientGrpc.getJoinGroupMethod = getJoinGroupMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.JoinGroupRequest, com.github.traderjoe95.mls.interop.proto.JoinGroupResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "JoinGroup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.JoinGroupRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.JoinGroupResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("JoinGroup"))
              .build();
        }
      }
    }
    return getJoinGroupMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest,
      com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> getExternalJoinMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExternalJoin",
      requestType = com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest,
      com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> getExternalJoinMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest, com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> getExternalJoinMethod;
    if ((getExternalJoinMethod = MLSClientGrpc.getExternalJoinMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getExternalJoinMethod = MLSClientGrpc.getExternalJoinMethod) == null) {
          MLSClientGrpc.getExternalJoinMethod = getExternalJoinMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest, com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExternalJoin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("ExternalJoin"))
              .build();
        }
      }
    }
    return getExternalJoinMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.GroupInfoRequest,
      com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> getGroupInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GroupInfo",
      requestType = com.github.traderjoe95.mls.interop.proto.GroupInfoRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.GroupInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.GroupInfoRequest,
      com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> getGroupInfoMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.GroupInfoRequest, com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> getGroupInfoMethod;
    if ((getGroupInfoMethod = MLSClientGrpc.getGroupInfoMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getGroupInfoMethod = MLSClientGrpc.getGroupInfoMethod) == null) {
          MLSClientGrpc.getGroupInfoMethod = getGroupInfoMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.GroupInfoRequest, com.github.traderjoe95.mls.interop.proto.GroupInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GroupInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.GroupInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.GroupInfoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("GroupInfo"))
              .build();
        }
      }
    }
    return getGroupInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.StateAuthRequest,
      com.github.traderjoe95.mls.interop.proto.StateAuthResponse> getStateAuthMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StateAuth",
      requestType = com.github.traderjoe95.mls.interop.proto.StateAuthRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.StateAuthResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.StateAuthRequest,
      com.github.traderjoe95.mls.interop.proto.StateAuthResponse> getStateAuthMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.StateAuthRequest, com.github.traderjoe95.mls.interop.proto.StateAuthResponse> getStateAuthMethod;
    if ((getStateAuthMethod = MLSClientGrpc.getStateAuthMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getStateAuthMethod = MLSClientGrpc.getStateAuthMethod) == null) {
          MLSClientGrpc.getStateAuthMethod = getStateAuthMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.StateAuthRequest, com.github.traderjoe95.mls.interop.proto.StateAuthResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StateAuth"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.StateAuthRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.StateAuthResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("StateAuth"))
              .build();
        }
      }
    }
    return getStateAuthMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExportRequest,
      com.github.traderjoe95.mls.interop.proto.ExportResponse> getExportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Export",
      requestType = com.github.traderjoe95.mls.interop.proto.ExportRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ExportResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExportRequest,
      com.github.traderjoe95.mls.interop.proto.ExportResponse> getExportMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExportRequest, com.github.traderjoe95.mls.interop.proto.ExportResponse> getExportMethod;
    if ((getExportMethod = MLSClientGrpc.getExportMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getExportMethod = MLSClientGrpc.getExportMethod) == null) {
          MLSClientGrpc.getExportMethod = getExportMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.ExportRequest, com.github.traderjoe95.mls.interop.proto.ExportResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Export"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ExportRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ExportResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("Export"))
              .build();
        }
      }
    }
    return getExportMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ProtectRequest,
      com.github.traderjoe95.mls.interop.proto.ProtectResponse> getProtectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Protect",
      requestType = com.github.traderjoe95.mls.interop.proto.ProtectRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProtectResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ProtectRequest,
      com.github.traderjoe95.mls.interop.proto.ProtectResponse> getProtectMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ProtectRequest, com.github.traderjoe95.mls.interop.proto.ProtectResponse> getProtectMethod;
    if ((getProtectMethod = MLSClientGrpc.getProtectMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getProtectMethod = MLSClientGrpc.getProtectMethod) == null) {
          MLSClientGrpc.getProtectMethod = getProtectMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.ProtectRequest, com.github.traderjoe95.mls.interop.proto.ProtectResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Protect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProtectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProtectResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("Protect"))
              .build();
        }
      }
    }
    return getProtectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.UnprotectRequest,
      com.github.traderjoe95.mls.interop.proto.UnprotectResponse> getUnprotectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Unprotect",
      requestType = com.github.traderjoe95.mls.interop.proto.UnprotectRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.UnprotectResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.UnprotectRequest,
      com.github.traderjoe95.mls.interop.proto.UnprotectResponse> getUnprotectMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.UnprotectRequest, com.github.traderjoe95.mls.interop.proto.UnprotectResponse> getUnprotectMethod;
    if ((getUnprotectMethod = MLSClientGrpc.getUnprotectMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getUnprotectMethod = MLSClientGrpc.getUnprotectMethod) == null) {
          MLSClientGrpc.getUnprotectMethod = getUnprotectMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.UnprotectRequest, com.github.traderjoe95.mls.interop.proto.UnprotectResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Unprotect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.UnprotectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.UnprotectResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("Unprotect"))
              .build();
        }
      }
    }
    return getUnprotectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.StorePSKRequest,
      com.github.traderjoe95.mls.interop.proto.StorePSKResponse> getStorePSKMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StorePSK",
      requestType = com.github.traderjoe95.mls.interop.proto.StorePSKRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.StorePSKResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.StorePSKRequest,
      com.github.traderjoe95.mls.interop.proto.StorePSKResponse> getStorePSKMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.StorePSKRequest, com.github.traderjoe95.mls.interop.proto.StorePSKResponse> getStorePSKMethod;
    if ((getStorePSKMethod = MLSClientGrpc.getStorePSKMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getStorePSKMethod = MLSClientGrpc.getStorePSKMethod) == null) {
          MLSClientGrpc.getStorePSKMethod = getStorePSKMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.StorePSKRequest, com.github.traderjoe95.mls.interop.proto.StorePSKResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StorePSK"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.StorePSKRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.StorePSKResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("StorePSK"))
              .build();
        }
      }
    }
    return getStorePSKMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.AddProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getAddProposalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddProposal",
      requestType = com.github.traderjoe95.mls.interop.proto.AddProposalRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.AddProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getAddProposalMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.AddProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse> getAddProposalMethod;
    if ((getAddProposalMethod = MLSClientGrpc.getAddProposalMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getAddProposalMethod = MLSClientGrpc.getAddProposalMethod) == null) {
          MLSClientGrpc.getAddProposalMethod = getAddProposalMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.AddProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddProposal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.AddProposalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("AddProposal"))
              .build();
        }
      }
    }
    return getAddProposalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getUpdateProposalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateProposal",
      requestType = com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getUpdateProposalMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse> getUpdateProposalMethod;
    if ((getUpdateProposalMethod = MLSClientGrpc.getUpdateProposalMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getUpdateProposalMethod = MLSClientGrpc.getUpdateProposalMethod) == null) {
          MLSClientGrpc.getUpdateProposalMethod = getUpdateProposalMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateProposal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("UpdateProposal"))
              .build();
        }
      }
    }
    return getUpdateProposalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getRemoveProposalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RemoveProposal",
      requestType = com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getRemoveProposalMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse> getRemoveProposalMethod;
    if ((getRemoveProposalMethod = MLSClientGrpc.getRemoveProposalMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getRemoveProposalMethod = MLSClientGrpc.getRemoveProposalMethod) == null) {
          MLSClientGrpc.getRemoveProposalMethod = getRemoveProposalMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RemoveProposal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("RemoveProposal"))
              .build();
        }
      }
    }
    return getRemoveProposalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getExternalPSKProposalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExternalPSKProposal",
      requestType = com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getExternalPSKProposalMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse> getExternalPSKProposalMethod;
    if ((getExternalPSKProposalMethod = MLSClientGrpc.getExternalPSKProposalMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getExternalPSKProposalMethod = MLSClientGrpc.getExternalPSKProposalMethod) == null) {
          MLSClientGrpc.getExternalPSKProposalMethod = getExternalPSKProposalMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExternalPSKProposal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("ExternalPSKProposal"))
              .build();
        }
      }
    }
    return getExternalPSKProposalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getResumptionPSKProposalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ResumptionPSKProposal",
      requestType = com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getResumptionPSKProposalMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse> getResumptionPSKProposalMethod;
    if ((getResumptionPSKProposalMethod = MLSClientGrpc.getResumptionPSKProposalMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getResumptionPSKProposalMethod = MLSClientGrpc.getResumptionPSKProposalMethod) == null) {
          MLSClientGrpc.getResumptionPSKProposalMethod = getResumptionPSKProposalMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ResumptionPSKProposal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("ResumptionPSKProposal"))
              .build();
        }
      }
    }
    return getResumptionPSKProposalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getGroupContextExtensionsProposalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GroupContextExtensionsProposal",
      requestType = com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getGroupContextExtensionsProposalMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse> getGroupContextExtensionsProposalMethod;
    if ((getGroupContextExtensionsProposalMethod = MLSClientGrpc.getGroupContextExtensionsProposalMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getGroupContextExtensionsProposalMethod = MLSClientGrpc.getGroupContextExtensionsProposalMethod) == null) {
          MLSClientGrpc.getGroupContextExtensionsProposalMethod = getGroupContextExtensionsProposalMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GroupContextExtensionsProposal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("GroupContextExtensionsProposal"))
              .build();
        }
      }
    }
    return getGroupContextExtensionsProposalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CommitRequest,
      com.github.traderjoe95.mls.interop.proto.CommitResponse> getCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Commit",
      requestType = com.github.traderjoe95.mls.interop.proto.CommitRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.CommitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CommitRequest,
      com.github.traderjoe95.mls.interop.proto.CommitResponse> getCommitMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CommitRequest, com.github.traderjoe95.mls.interop.proto.CommitResponse> getCommitMethod;
    if ((getCommitMethod = MLSClientGrpc.getCommitMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getCommitMethod = MLSClientGrpc.getCommitMethod) == null) {
          MLSClientGrpc.getCommitMethod = getCommitMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.CommitRequest, com.github.traderjoe95.mls.interop.proto.CommitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Commit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CommitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CommitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("Commit"))
              .build();
        }
      }
    }
    return getCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleCommitRequest,
      com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> getHandleCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleCommit",
      requestType = com.github.traderjoe95.mls.interop.proto.HandleCommitRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.HandleCommitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleCommitRequest,
      com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> getHandleCommitMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleCommitRequest, com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> getHandleCommitMethod;
    if ((getHandleCommitMethod = MLSClientGrpc.getHandleCommitMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getHandleCommitMethod = MLSClientGrpc.getHandleCommitMethod) == null) {
          MLSClientGrpc.getHandleCommitMethod = getHandleCommitMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.HandleCommitRequest, com.github.traderjoe95.mls.interop.proto.HandleCommitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleCommit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandleCommitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandleCommitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("HandleCommit"))
              .build();
        }
      }
    }
    return getHandleCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest,
      com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> getHandlePendingCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandlePendingCommit",
      requestType = com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.HandleCommitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest,
      com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> getHandlePendingCommitMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest, com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> getHandlePendingCommitMethod;
    if ((getHandlePendingCommitMethod = MLSClientGrpc.getHandlePendingCommitMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getHandlePendingCommitMethod = MLSClientGrpc.getHandlePendingCommitMethod) == null) {
          MLSClientGrpc.getHandlePendingCommitMethod = getHandlePendingCommitMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest, com.github.traderjoe95.mls.interop.proto.HandleCommitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandlePendingCommit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandleCommitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("HandlePendingCommit"))
              .build();
        }
      }
    }
    return getHandlePendingCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getReInitProposalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReInitProposal",
      requestType = com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getReInitProposalMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse> getReInitProposalMethod;
    if ((getReInitProposalMethod = MLSClientGrpc.getReInitProposalMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getReInitProposalMethod = MLSClientGrpc.getReInitProposalMethod) == null) {
          MLSClientGrpc.getReInitProposalMethod = getReInitProposalMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReInitProposal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("ReInitProposal"))
              .build();
        }
      }
    }
    return getReInitProposalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CommitRequest,
      com.github.traderjoe95.mls.interop.proto.CommitResponse> getReInitCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReInitCommit",
      requestType = com.github.traderjoe95.mls.interop.proto.CommitRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.CommitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CommitRequest,
      com.github.traderjoe95.mls.interop.proto.CommitResponse> getReInitCommitMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CommitRequest, com.github.traderjoe95.mls.interop.proto.CommitResponse> getReInitCommitMethod;
    if ((getReInitCommitMethod = MLSClientGrpc.getReInitCommitMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getReInitCommitMethod = MLSClientGrpc.getReInitCommitMethod) == null) {
          MLSClientGrpc.getReInitCommitMethod = getReInitCommitMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.CommitRequest, com.github.traderjoe95.mls.interop.proto.CommitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReInitCommit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CommitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CommitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("ReInitCommit"))
              .build();
        }
      }
    }
    return getReInitCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest,
      com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> getHandlePendingReInitCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandlePendingReInitCommit",
      requestType = com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest,
      com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> getHandlePendingReInitCommitMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest, com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> getHandlePendingReInitCommitMethod;
    if ((getHandlePendingReInitCommitMethod = MLSClientGrpc.getHandlePendingReInitCommitMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getHandlePendingReInitCommitMethod = MLSClientGrpc.getHandlePendingReInitCommitMethod) == null) {
          MLSClientGrpc.getHandlePendingReInitCommitMethod = getHandlePendingReInitCommitMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest, com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandlePendingReInitCommit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("HandlePendingReInitCommit"))
              .build();
        }
      }
    }
    return getHandlePendingReInitCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleCommitRequest,
      com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> getHandleReInitCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleReInitCommit",
      requestType = com.github.traderjoe95.mls.interop.proto.HandleCommitRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleCommitRequest,
      com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> getHandleReInitCommitMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleCommitRequest, com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> getHandleReInitCommitMethod;
    if ((getHandleReInitCommitMethod = MLSClientGrpc.getHandleReInitCommitMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getHandleReInitCommitMethod = MLSClientGrpc.getHandleReInitCommitMethod) == null) {
          MLSClientGrpc.getHandleReInitCommitMethod = getHandleReInitCommitMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.HandleCommitRequest, com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleReInitCommit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandleCommitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("HandleReInitCommit"))
              .build();
        }
      }
    }
    return getHandleReInitCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest,
      com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> getReInitWelcomeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReInitWelcome",
      requestType = com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest,
      com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> getReInitWelcomeMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest, com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> getReInitWelcomeMethod;
    if ((getReInitWelcomeMethod = MLSClientGrpc.getReInitWelcomeMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getReInitWelcomeMethod = MLSClientGrpc.getReInitWelcomeMethod) == null) {
          MLSClientGrpc.getReInitWelcomeMethod = getReInitWelcomeMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest, com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReInitWelcome"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("ReInitWelcome"))
              .build();
        }
      }
    }
    return getReInitWelcomeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest,
      com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> getHandleReInitWelcomeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleReInitWelcome",
      requestType = com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.JoinGroupResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest,
      com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> getHandleReInitWelcomeMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest, com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> getHandleReInitWelcomeMethod;
    if ((getHandleReInitWelcomeMethod = MLSClientGrpc.getHandleReInitWelcomeMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getHandleReInitWelcomeMethod = MLSClientGrpc.getHandleReInitWelcomeMethod) == null) {
          MLSClientGrpc.getHandleReInitWelcomeMethod = getHandleReInitWelcomeMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest, com.github.traderjoe95.mls.interop.proto.JoinGroupResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleReInitWelcome"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.JoinGroupResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("HandleReInitWelcome"))
              .build();
        }
      }
    }
    return getHandleReInitWelcomeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateBranchRequest,
      com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> getCreateBranchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateBranch",
      requestType = com.github.traderjoe95.mls.interop.proto.CreateBranchRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateBranchRequest,
      com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> getCreateBranchMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateBranchRequest, com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> getCreateBranchMethod;
    if ((getCreateBranchMethod = MLSClientGrpc.getCreateBranchMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getCreateBranchMethod = MLSClientGrpc.getCreateBranchMethod) == null) {
          MLSClientGrpc.getCreateBranchMethod = getCreateBranchMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.CreateBranchRequest, com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateBranch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CreateBranchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("CreateBranch"))
              .build();
        }
      }
    }
    return getCreateBranchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleBranchRequest,
      com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> getHandleBranchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleBranch",
      requestType = com.github.traderjoe95.mls.interop.proto.HandleBranchRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.HandleBranchResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleBranchRequest,
      com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> getHandleBranchMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.HandleBranchRequest, com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> getHandleBranchMethod;
    if ((getHandleBranchMethod = MLSClientGrpc.getHandleBranchMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getHandleBranchMethod = MLSClientGrpc.getHandleBranchMethod) == null) {
          MLSClientGrpc.getHandleBranchMethod = getHandleBranchMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.HandleBranchRequest, com.github.traderjoe95.mls.interop.proto.HandleBranchResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleBranch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandleBranchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.HandleBranchResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("HandleBranch"))
              .build();
        }
      }
    }
    return getHandleBranchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest,
      com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> getNewMemberAddProposalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "NewMemberAddProposal",
      requestType = com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest,
      com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> getNewMemberAddProposalMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest, com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> getNewMemberAddProposalMethod;
    if ((getNewMemberAddProposalMethod = MLSClientGrpc.getNewMemberAddProposalMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getNewMemberAddProposalMethod = MLSClientGrpc.getNewMemberAddProposalMethod) == null) {
          MLSClientGrpc.getNewMemberAddProposalMethod = getNewMemberAddProposalMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest, com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "NewMemberAddProposal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("NewMemberAddProposal"))
              .build();
        }
      }
    }
    return getNewMemberAddProposalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest,
      com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> getCreateExternalSignerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateExternalSigner",
      requestType = com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest,
      com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> getCreateExternalSignerMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest, com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> getCreateExternalSignerMethod;
    if ((getCreateExternalSignerMethod = MLSClientGrpc.getCreateExternalSignerMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getCreateExternalSignerMethod = MLSClientGrpc.getCreateExternalSignerMethod) == null) {
          MLSClientGrpc.getCreateExternalSignerMethod = getCreateExternalSignerMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest, com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateExternalSigner"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("CreateExternalSigner"))
              .build();
        }
      }
    }
    return getCreateExternalSignerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getAddExternalSignerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddExternalSigner",
      requestType = com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getAddExternalSignerMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse> getAddExternalSignerMethod;
    if ((getAddExternalSignerMethod = MLSClientGrpc.getAddExternalSignerMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getAddExternalSignerMethod = MLSClientGrpc.getAddExternalSignerMethod) == null) {
          MLSClientGrpc.getAddExternalSignerMethod = getAddExternalSignerMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddExternalSigner"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("AddExternalSigner"))
              .build();
        }
      }
    }
    return getAddExternalSignerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getExternalSignerProposalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExternalSignerProposal",
      requestType = com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.ProposalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest,
      com.github.traderjoe95.mls.interop.proto.ProposalResponse> getExternalSignerProposalMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse> getExternalSignerProposalMethod;
    if ((getExternalSignerProposalMethod = MLSClientGrpc.getExternalSignerProposalMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getExternalSignerProposalMethod = MLSClientGrpc.getExternalSignerProposalMethod) == null) {
          MLSClientGrpc.getExternalSignerProposalMethod = getExternalSignerProposalMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest, com.github.traderjoe95.mls.interop.proto.ProposalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExternalSignerProposal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.ProposalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("ExternalSignerProposal"))
              .build();
        }
      }
    }
    return getExternalSignerProposalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.FreeRequest,
      com.github.traderjoe95.mls.interop.proto.FreeResponse> getFreeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Free",
      requestType = com.github.traderjoe95.mls.interop.proto.FreeRequest.class,
      responseType = com.github.traderjoe95.mls.interop.proto.FreeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.FreeRequest,
      com.github.traderjoe95.mls.interop.proto.FreeResponse> getFreeMethod() {
    io.grpc.MethodDescriptor<com.github.traderjoe95.mls.interop.proto.FreeRequest, com.github.traderjoe95.mls.interop.proto.FreeResponse> getFreeMethod;
    if ((getFreeMethod = MLSClientGrpc.getFreeMethod) == null) {
      synchronized (MLSClientGrpc.class) {
        if ((getFreeMethod = MLSClientGrpc.getFreeMethod) == null) {
          MLSClientGrpc.getFreeMethod = getFreeMethod =
              io.grpc.MethodDescriptor.<com.github.traderjoe95.mls.interop.proto.FreeRequest, com.github.traderjoe95.mls.interop.proto.FreeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Free"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.FreeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.github.traderjoe95.mls.interop.proto.FreeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MLSClientMethodDescriptorSupplier("Free"))
              .build();
        }
      }
    }
    return getFreeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MLSClientStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MLSClientStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MLSClientStub>() {
        @java.lang.Override
        public MLSClientStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MLSClientStub(channel, callOptions);
        }
      };
    return MLSClientStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MLSClientBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MLSClientBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MLSClientBlockingStub>() {
        @java.lang.Override
        public MLSClientBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MLSClientBlockingStub(channel, callOptions);
        }
      };
    return MLSClientBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MLSClientFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MLSClientFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MLSClientFutureStub>() {
        @java.lang.Override
        public MLSClientFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MLSClientFutureStub(channel, callOptions);
        }
      };
    return MLSClientFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * A wrapper around an MLS client implementation
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * The human-readable name of the stack
     * </pre>
     */
    default void name(com.github.traderjoe95.mls.interop.proto.NameRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.NameResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNameMethod(), responseObserver);
    }

    /**
     * <pre>
     * List of supported ciphersuites
     * </pre>
     */
    default void supportedCiphersuites(com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSupportedCiphersuitesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Ways to become a member of a group
     * </pre>
     */
    default void createGroup(com.github.traderjoe95.mls.interop.proto.CreateGroupRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateGroupMethod(), responseObserver);
    }

    /**
     */
    default void createKeyPackage(com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateKeyPackageMethod(), responseObserver);
    }

    /**
     */
    default void joinGroup(com.github.traderjoe95.mls.interop.proto.JoinGroupRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinGroupMethod(), responseObserver);
    }

    /**
     */
    default void externalJoin(com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExternalJoinMethod(), responseObserver);
    }

    /**
     * <pre>
     * Operations using a group state
     * </pre>
     */
    default void groupInfo(com.github.traderjoe95.mls.interop.proto.GroupInfoRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGroupInfoMethod(), responseObserver);
    }

    /**
     */
    default void stateAuth(com.github.traderjoe95.mls.interop.proto.StateAuthRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.StateAuthResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStateAuthMethod(), responseObserver);
    }

    /**
     */
    default void export(com.github.traderjoe95.mls.interop.proto.ExportRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ExportResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExportMethod(), responseObserver);
    }

    /**
     */
    default void protect(com.github.traderjoe95.mls.interop.proto.ProtectRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProtectResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getProtectMethod(), responseObserver);
    }

    /**
     */
    default void unprotect(com.github.traderjoe95.mls.interop.proto.UnprotectRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.UnprotectResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnprotectMethod(), responseObserver);
    }

    /**
     */
    default void storePSK(com.github.traderjoe95.mls.interop.proto.StorePSKRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.StorePSKResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStorePSKMethod(), responseObserver);
    }

    /**
     */
    default void addProposal(com.github.traderjoe95.mls.interop.proto.AddProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddProposalMethod(), responseObserver);
    }

    /**
     */
    default void updateProposal(com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateProposalMethod(), responseObserver);
    }

    /**
     */
    default void removeProposal(com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveProposalMethod(), responseObserver);
    }

    /**
     */
    default void externalPSKProposal(com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExternalPSKProposalMethod(), responseObserver);
    }

    /**
     */
    default void resumptionPSKProposal(com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getResumptionPSKProposalMethod(), responseObserver);
    }

    /**
     */
    default void groupContextExtensionsProposal(com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGroupContextExtensionsProposalMethod(), responseObserver);
    }

    /**
     */
    default void commit(com.github.traderjoe95.mls.interop.proto.CommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CommitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCommitMethod(), responseObserver);
    }

    /**
     */
    default void handleCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleCommitMethod(), responseObserver);
    }

    /**
     */
    default void handlePendingCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandlePendingCommitMethod(), responseObserver);
    }

    /**
     * <pre>
     * Reinitialization
     * </pre>
     */
    default void reInitProposal(com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReInitProposalMethod(), responseObserver);
    }

    /**
     */
    default void reInitCommit(com.github.traderjoe95.mls.interop.proto.CommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CommitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReInitCommitMethod(), responseObserver);
    }

    /**
     */
    default void handlePendingReInitCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandlePendingReInitCommitMethod(), responseObserver);
    }

    /**
     */
    default void handleReInitCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleReInitCommitMethod(), responseObserver);
    }

    /**
     */
    default void reInitWelcome(com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReInitWelcomeMethod(), responseObserver);
    }

    /**
     */
    default void handleReInitWelcome(com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleReInitWelcomeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Subgroup Branching
     * </pre>
     */
    default void createBranch(com.github.traderjoe95.mls.interop.proto.CreateBranchRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateBranchMethod(), responseObserver);
    }

    /**
     */
    default void handleBranch(com.github.traderjoe95.mls.interop.proto.HandleBranchRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleBranchMethod(), responseObserver);
    }

    /**
     * <pre>
     * External proposals
     * </pre>
     */
    default void newMemberAddProposal(com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNewMemberAddProposalMethod(), responseObserver);
    }

    /**
     */
    default void createExternalSigner(com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateExternalSignerMethod(), responseObserver);
    }

    /**
     */
    default void addExternalSigner(com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddExternalSignerMethod(), responseObserver);
    }

    /**
     */
    default void externalSignerProposal(com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExternalSignerProposalMethod(), responseObserver);
    }

    /**
     * <pre>
     * Cleanup
     * </pre>
     */
    default void free(com.github.traderjoe95.mls.interop.proto.FreeRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.FreeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFreeMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service MLSClient.
   * <pre>
   * A wrapper around an MLS client implementation
   * </pre>
   */
  public static abstract class MLSClientImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return MLSClientGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service MLSClient.
   * <pre>
   * A wrapper around an MLS client implementation
   * </pre>
   */
  public static final class MLSClientStub
      extends io.grpc.stub.AbstractAsyncStub<MLSClientStub> {
    private MLSClientStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MLSClientStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MLSClientStub(channel, callOptions);
    }

    /**
     * <pre>
     * The human-readable name of the stack
     * </pre>
     */
    public void name(com.github.traderjoe95.mls.interop.proto.NameRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.NameResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * List of supported ciphersuites
     * </pre>
     */
    public void supportedCiphersuites(com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSupportedCiphersuitesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Ways to become a member of a group
     * </pre>
     */
    public void createGroup(com.github.traderjoe95.mls.interop.proto.CreateGroupRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateGroupMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createKeyPackage(com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateKeyPackageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void joinGroup(com.github.traderjoe95.mls.interop.proto.JoinGroupRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJoinGroupMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void externalJoin(com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExternalJoinMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Operations using a group state
     * </pre>
     */
    public void groupInfo(com.github.traderjoe95.mls.interop.proto.GroupInfoRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGroupInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void stateAuth(com.github.traderjoe95.mls.interop.proto.StateAuthRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.StateAuthResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStateAuthMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void export(com.github.traderjoe95.mls.interop.proto.ExportRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ExportResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExportMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void protect(com.github.traderjoe95.mls.interop.proto.ProtectRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProtectResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getProtectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unprotect(com.github.traderjoe95.mls.interop.proto.UnprotectRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.UnprotectResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnprotectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void storePSK(com.github.traderjoe95.mls.interop.proto.StorePSKRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.StorePSKResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStorePSKMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addProposal(com.github.traderjoe95.mls.interop.proto.AddProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddProposalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateProposal(com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateProposalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void removeProposal(com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveProposalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void externalPSKProposal(com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExternalPSKProposalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void resumptionPSKProposal(com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getResumptionPSKProposalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void groupContextExtensionsProposal(com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGroupContextExtensionsProposalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void commit(com.github.traderjoe95.mls.interop.proto.CommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CommitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void handleCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void handlePendingCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandlePendingCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Reinitialization
     * </pre>
     */
    public void reInitProposal(com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReInitProposalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reInitCommit(com.github.traderjoe95.mls.interop.proto.CommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CommitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReInitCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void handlePendingReInitCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandlePendingReInitCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void handleReInitCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleReInitCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reInitWelcome(com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReInitWelcomeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void handleReInitWelcome(com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleReInitWelcomeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Subgroup Branching
     * </pre>
     */
    public void createBranch(com.github.traderjoe95.mls.interop.proto.CreateBranchRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateBranchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void handleBranch(com.github.traderjoe95.mls.interop.proto.HandleBranchRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleBranchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * External proposals
     * </pre>
     */
    public void newMemberAddProposal(com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getNewMemberAddProposalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createExternalSigner(com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateExternalSignerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addExternalSigner(com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddExternalSignerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void externalSignerProposal(com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExternalSignerProposalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Cleanup
     * </pre>
     */
    public void free(com.github.traderjoe95.mls.interop.proto.FreeRequest request,
        io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.FreeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFreeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service MLSClient.
   * <pre>
   * A wrapper around an MLS client implementation
   * </pre>
   */
  public static final class MLSClientBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<MLSClientBlockingStub> {
    private MLSClientBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MLSClientBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MLSClientBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * The human-readable name of the stack
     * </pre>
     */
    public com.github.traderjoe95.mls.interop.proto.NameResponse name(com.github.traderjoe95.mls.interop.proto.NameRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getNameMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * List of supported ciphersuites
     * </pre>
     */
    public com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse supportedCiphersuites(com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSupportedCiphersuitesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Ways to become a member of a group
     * </pre>
     */
    public com.github.traderjoe95.mls.interop.proto.CreateGroupResponse createGroup(com.github.traderjoe95.mls.interop.proto.CreateGroupRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateGroupMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse createKeyPackage(com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateKeyPackageMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.JoinGroupResponse joinGroup(com.github.traderjoe95.mls.interop.proto.JoinGroupRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJoinGroupMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse externalJoin(com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExternalJoinMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Operations using a group state
     * </pre>
     */
    public com.github.traderjoe95.mls.interop.proto.GroupInfoResponse groupInfo(com.github.traderjoe95.mls.interop.proto.GroupInfoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGroupInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.StateAuthResponse stateAuth(com.github.traderjoe95.mls.interop.proto.StateAuthRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStateAuthMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ExportResponse export(com.github.traderjoe95.mls.interop.proto.ExportRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExportMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ProtectResponse protect(com.github.traderjoe95.mls.interop.proto.ProtectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getProtectMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.UnprotectResponse unprotect(com.github.traderjoe95.mls.interop.proto.UnprotectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnprotectMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.StorePSKResponse storePSK(com.github.traderjoe95.mls.interop.proto.StorePSKRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStorePSKMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ProposalResponse addProposal(com.github.traderjoe95.mls.interop.proto.AddProposalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddProposalMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ProposalResponse updateProposal(com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateProposalMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ProposalResponse removeProposal(com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveProposalMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ProposalResponse externalPSKProposal(com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExternalPSKProposalMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ProposalResponse resumptionPSKProposal(com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getResumptionPSKProposalMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ProposalResponse groupContextExtensionsProposal(com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGroupContextExtensionsProposalMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.CommitResponse commit(com.github.traderjoe95.mls.interop.proto.CommitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCommitMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.HandleCommitResponse handleCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleCommitMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.HandleCommitResponse handlePendingCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandlePendingCommitMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Reinitialization
     * </pre>
     */
    public com.github.traderjoe95.mls.interop.proto.ProposalResponse reInitProposal(com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReInitProposalMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.CommitResponse reInitCommit(com.github.traderjoe95.mls.interop.proto.CommitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReInitCommitMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse handlePendingReInitCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandlePendingReInitCommitMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse handleReInitCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleReInitCommitMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse reInitWelcome(com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReInitWelcomeMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.JoinGroupResponse handleReInitWelcome(com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleReInitWelcomeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Subgroup Branching
     * </pre>
     */
    public com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse createBranch(com.github.traderjoe95.mls.interop.proto.CreateBranchRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateBranchMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.HandleBranchResponse handleBranch(com.github.traderjoe95.mls.interop.proto.HandleBranchRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleBranchMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * External proposals
     * </pre>
     */
    public com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse newMemberAddProposal(com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getNewMemberAddProposalMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse createExternalSigner(com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateExternalSignerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ProposalResponse addExternalSigner(com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddExternalSignerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.github.traderjoe95.mls.interop.proto.ProposalResponse externalSignerProposal(com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExternalSignerProposalMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Cleanup
     * </pre>
     */
    public com.github.traderjoe95.mls.interop.proto.FreeResponse free(com.github.traderjoe95.mls.interop.proto.FreeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFreeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service MLSClient.
   * <pre>
   * A wrapper around an MLS client implementation
   * </pre>
   */
  public static final class MLSClientFutureStub
      extends io.grpc.stub.AbstractFutureStub<MLSClientFutureStub> {
    private MLSClientFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MLSClientFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MLSClientFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * The human-readable name of the stack
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.NameResponse> name(
        com.github.traderjoe95.mls.interop.proto.NameRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getNameMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * List of supported ciphersuites
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> supportedCiphersuites(
        com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSupportedCiphersuitesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Ways to become a member of a group
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> createGroup(
        com.github.traderjoe95.mls.interop.proto.CreateGroupRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateGroupMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> createKeyPackage(
        com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateKeyPackageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> joinGroup(
        com.github.traderjoe95.mls.interop.proto.JoinGroupRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJoinGroupMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> externalJoin(
        com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExternalJoinMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Operations using a group state
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> groupInfo(
        com.github.traderjoe95.mls.interop.proto.GroupInfoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGroupInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.StateAuthResponse> stateAuth(
        com.github.traderjoe95.mls.interop.proto.StateAuthRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStateAuthMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ExportResponse> export(
        com.github.traderjoe95.mls.interop.proto.ExportRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExportMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProtectResponse> protect(
        com.github.traderjoe95.mls.interop.proto.ProtectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getProtectMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.UnprotectResponse> unprotect(
        com.github.traderjoe95.mls.interop.proto.UnprotectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnprotectMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.StorePSKResponse> storePSK(
        com.github.traderjoe95.mls.interop.proto.StorePSKRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStorePSKMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProposalResponse> addProposal(
        com.github.traderjoe95.mls.interop.proto.AddProposalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddProposalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProposalResponse> updateProposal(
        com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateProposalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProposalResponse> removeProposal(
        com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveProposalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProposalResponse> externalPSKProposal(
        com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExternalPSKProposalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProposalResponse> resumptionPSKProposal(
        com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getResumptionPSKProposalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProposalResponse> groupContextExtensionsProposal(
        com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGroupContextExtensionsProposalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.CommitResponse> commit(
        com.github.traderjoe95.mls.interop.proto.CommitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCommitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> handleCommit(
        com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleCommitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> handlePendingCommit(
        com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandlePendingCommitMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Reinitialization
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProposalResponse> reInitProposal(
        com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReInitProposalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.CommitResponse> reInitCommit(
        com.github.traderjoe95.mls.interop.proto.CommitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReInitCommitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> handlePendingReInitCommit(
        com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandlePendingReInitCommitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> handleReInitCommit(
        com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleReInitCommitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> reInitWelcome(
        com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReInitWelcomeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> handleReInitWelcome(
        com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleReInitWelcomeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Subgroup Branching
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> createBranch(
        com.github.traderjoe95.mls.interop.proto.CreateBranchRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateBranchMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> handleBranch(
        com.github.traderjoe95.mls.interop.proto.HandleBranchRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleBranchMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * External proposals
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> newMemberAddProposal(
        com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getNewMemberAddProposalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> createExternalSigner(
        com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateExternalSignerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProposalResponse> addExternalSigner(
        com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddExternalSignerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.ProposalResponse> externalSignerProposal(
        com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExternalSignerProposalMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Cleanup
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.github.traderjoe95.mls.interop.proto.FreeResponse> free(
        com.github.traderjoe95.mls.interop.proto.FreeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFreeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_NAME = 0;
  private static final int METHODID_SUPPORTED_CIPHERSUITES = 1;
  private static final int METHODID_CREATE_GROUP = 2;
  private static final int METHODID_CREATE_KEY_PACKAGE = 3;
  private static final int METHODID_JOIN_GROUP = 4;
  private static final int METHODID_EXTERNAL_JOIN = 5;
  private static final int METHODID_GROUP_INFO = 6;
  private static final int METHODID_STATE_AUTH = 7;
  private static final int METHODID_EXPORT = 8;
  private static final int METHODID_PROTECT = 9;
  private static final int METHODID_UNPROTECT = 10;
  private static final int METHODID_STORE_PSK = 11;
  private static final int METHODID_ADD_PROPOSAL = 12;
  private static final int METHODID_UPDATE_PROPOSAL = 13;
  private static final int METHODID_REMOVE_PROPOSAL = 14;
  private static final int METHODID_EXTERNAL_PSKPROPOSAL = 15;
  private static final int METHODID_RESUMPTION_PSKPROPOSAL = 16;
  private static final int METHODID_GROUP_CONTEXT_EXTENSIONS_PROPOSAL = 17;
  private static final int METHODID_COMMIT = 18;
  private static final int METHODID_HANDLE_COMMIT = 19;
  private static final int METHODID_HANDLE_PENDING_COMMIT = 20;
  private static final int METHODID_RE_INIT_PROPOSAL = 21;
  private static final int METHODID_RE_INIT_COMMIT = 22;
  private static final int METHODID_HANDLE_PENDING_RE_INIT_COMMIT = 23;
  private static final int METHODID_HANDLE_RE_INIT_COMMIT = 24;
  private static final int METHODID_RE_INIT_WELCOME = 25;
  private static final int METHODID_HANDLE_RE_INIT_WELCOME = 26;
  private static final int METHODID_CREATE_BRANCH = 27;
  private static final int METHODID_HANDLE_BRANCH = 28;
  private static final int METHODID_NEW_MEMBER_ADD_PROPOSAL = 29;
  private static final int METHODID_CREATE_EXTERNAL_SIGNER = 30;
  private static final int METHODID_ADD_EXTERNAL_SIGNER = 31;
  private static final int METHODID_EXTERNAL_SIGNER_PROPOSAL = 32;
  private static final int METHODID_FREE = 33;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_NAME:
          serviceImpl.name((com.github.traderjoe95.mls.interop.proto.NameRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.NameResponse>) responseObserver);
          break;
        case METHODID_SUPPORTED_CIPHERSUITES:
          serviceImpl.supportedCiphersuites((com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse>) responseObserver);
          break;
        case METHODID_CREATE_GROUP:
          serviceImpl.createGroup((com.github.traderjoe95.mls.interop.proto.CreateGroupRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateGroupResponse>) responseObserver);
          break;
        case METHODID_CREATE_KEY_PACKAGE:
          serviceImpl.createKeyPackage((com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse>) responseObserver);
          break;
        case METHODID_JOIN_GROUP:
          serviceImpl.joinGroup((com.github.traderjoe95.mls.interop.proto.JoinGroupRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse>) responseObserver);
          break;
        case METHODID_EXTERNAL_JOIN:
          serviceImpl.externalJoin((com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse>) responseObserver);
          break;
        case METHODID_GROUP_INFO:
          serviceImpl.groupInfo((com.github.traderjoe95.mls.interop.proto.GroupInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.GroupInfoResponse>) responseObserver);
          break;
        case METHODID_STATE_AUTH:
          serviceImpl.stateAuth((com.github.traderjoe95.mls.interop.proto.StateAuthRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.StateAuthResponse>) responseObserver);
          break;
        case METHODID_EXPORT:
          serviceImpl.export((com.github.traderjoe95.mls.interop.proto.ExportRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ExportResponse>) responseObserver);
          break;
        case METHODID_PROTECT:
          serviceImpl.protect((com.github.traderjoe95.mls.interop.proto.ProtectRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProtectResponse>) responseObserver);
          break;
        case METHODID_UNPROTECT:
          serviceImpl.unprotect((com.github.traderjoe95.mls.interop.proto.UnprotectRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.UnprotectResponse>) responseObserver);
          break;
        case METHODID_STORE_PSK:
          serviceImpl.storePSK((com.github.traderjoe95.mls.interop.proto.StorePSKRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.StorePSKResponse>) responseObserver);
          break;
        case METHODID_ADD_PROPOSAL:
          serviceImpl.addProposal((com.github.traderjoe95.mls.interop.proto.AddProposalRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse>) responseObserver);
          break;
        case METHODID_UPDATE_PROPOSAL:
          serviceImpl.updateProposal((com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse>) responseObserver);
          break;
        case METHODID_REMOVE_PROPOSAL:
          serviceImpl.removeProposal((com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse>) responseObserver);
          break;
        case METHODID_EXTERNAL_PSKPROPOSAL:
          serviceImpl.externalPSKProposal((com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse>) responseObserver);
          break;
        case METHODID_RESUMPTION_PSKPROPOSAL:
          serviceImpl.resumptionPSKProposal((com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse>) responseObserver);
          break;
        case METHODID_GROUP_CONTEXT_EXTENSIONS_PROPOSAL:
          serviceImpl.groupContextExtensionsProposal((com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse>) responseObserver);
          break;
        case METHODID_COMMIT:
          serviceImpl.commit((com.github.traderjoe95.mls.interop.proto.CommitRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CommitResponse>) responseObserver);
          break;
        case METHODID_HANDLE_COMMIT:
          serviceImpl.handleCommit((com.github.traderjoe95.mls.interop.proto.HandleCommitRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse>) responseObserver);
          break;
        case METHODID_HANDLE_PENDING_COMMIT:
          serviceImpl.handlePendingCommit((com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse>) responseObserver);
          break;
        case METHODID_RE_INIT_PROPOSAL:
          serviceImpl.reInitProposal((com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse>) responseObserver);
          break;
        case METHODID_RE_INIT_COMMIT:
          serviceImpl.reInitCommit((com.github.traderjoe95.mls.interop.proto.CommitRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CommitResponse>) responseObserver);
          break;
        case METHODID_HANDLE_PENDING_RE_INIT_COMMIT:
          serviceImpl.handlePendingReInitCommit((com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse>) responseObserver);
          break;
        case METHODID_HANDLE_RE_INIT_COMMIT:
          serviceImpl.handleReInitCommit((com.github.traderjoe95.mls.interop.proto.HandleCommitRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse>) responseObserver);
          break;
        case METHODID_RE_INIT_WELCOME:
          serviceImpl.reInitWelcome((com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse>) responseObserver);
          break;
        case METHODID_HANDLE_RE_INIT_WELCOME:
          serviceImpl.handleReInitWelcome((com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse>) responseObserver);
          break;
        case METHODID_CREATE_BRANCH:
          serviceImpl.createBranch((com.github.traderjoe95.mls.interop.proto.CreateBranchRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse>) responseObserver);
          break;
        case METHODID_HANDLE_BRANCH:
          serviceImpl.handleBranch((com.github.traderjoe95.mls.interop.proto.HandleBranchRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.HandleBranchResponse>) responseObserver);
          break;
        case METHODID_NEW_MEMBER_ADD_PROPOSAL:
          serviceImpl.newMemberAddProposal((com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse>) responseObserver);
          break;
        case METHODID_CREATE_EXTERNAL_SIGNER:
          serviceImpl.createExternalSigner((com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse>) responseObserver);
          break;
        case METHODID_ADD_EXTERNAL_SIGNER:
          serviceImpl.addExternalSigner((com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse>) responseObserver);
          break;
        case METHODID_EXTERNAL_SIGNER_PROPOSAL:
          serviceImpl.externalSignerProposal((com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.ProposalResponse>) responseObserver);
          break;
        case METHODID_FREE:
          serviceImpl.free((com.github.traderjoe95.mls.interop.proto.FreeRequest) request,
              (io.grpc.stub.StreamObserver<com.github.traderjoe95.mls.interop.proto.FreeResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getNameMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.NameRequest,
              com.github.traderjoe95.mls.interop.proto.NameResponse>(
                service, METHODID_NAME)))
        .addMethod(
          getSupportedCiphersuitesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest,
              com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse>(
                service, METHODID_SUPPORTED_CIPHERSUITES)))
        .addMethod(
          getCreateGroupMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.CreateGroupRequest,
              com.github.traderjoe95.mls.interop.proto.CreateGroupResponse>(
                service, METHODID_CREATE_GROUP)))
        .addMethod(
          getCreateKeyPackageMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest,
              com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse>(
                service, METHODID_CREATE_KEY_PACKAGE)))
        .addMethod(
          getJoinGroupMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.JoinGroupRequest,
              com.github.traderjoe95.mls.interop.proto.JoinGroupResponse>(
                service, METHODID_JOIN_GROUP)))
        .addMethod(
          getExternalJoinMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest,
              com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse>(
                service, METHODID_EXTERNAL_JOIN)))
        .addMethod(
          getGroupInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.GroupInfoRequest,
              com.github.traderjoe95.mls.interop.proto.GroupInfoResponse>(
                service, METHODID_GROUP_INFO)))
        .addMethod(
          getStateAuthMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.StateAuthRequest,
              com.github.traderjoe95.mls.interop.proto.StateAuthResponse>(
                service, METHODID_STATE_AUTH)))
        .addMethod(
          getExportMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.ExportRequest,
              com.github.traderjoe95.mls.interop.proto.ExportResponse>(
                service, METHODID_EXPORT)))
        .addMethod(
          getProtectMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.ProtectRequest,
              com.github.traderjoe95.mls.interop.proto.ProtectResponse>(
                service, METHODID_PROTECT)))
        .addMethod(
          getUnprotectMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.UnprotectRequest,
              com.github.traderjoe95.mls.interop.proto.UnprotectResponse>(
                service, METHODID_UNPROTECT)))
        .addMethod(
          getStorePSKMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.StorePSKRequest,
              com.github.traderjoe95.mls.interop.proto.StorePSKResponse>(
                service, METHODID_STORE_PSK)))
        .addMethod(
          getAddProposalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.AddProposalRequest,
              com.github.traderjoe95.mls.interop.proto.ProposalResponse>(
                service, METHODID_ADD_PROPOSAL)))
        .addMethod(
          getUpdateProposalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest,
              com.github.traderjoe95.mls.interop.proto.ProposalResponse>(
                service, METHODID_UPDATE_PROPOSAL)))
        .addMethod(
          getRemoveProposalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest,
              com.github.traderjoe95.mls.interop.proto.ProposalResponse>(
                service, METHODID_REMOVE_PROPOSAL)))
        .addMethod(
          getExternalPSKProposalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest,
              com.github.traderjoe95.mls.interop.proto.ProposalResponse>(
                service, METHODID_EXTERNAL_PSKPROPOSAL)))
        .addMethod(
          getResumptionPSKProposalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest,
              com.github.traderjoe95.mls.interop.proto.ProposalResponse>(
                service, METHODID_RESUMPTION_PSKPROPOSAL)))
        .addMethod(
          getGroupContextExtensionsProposalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest,
              com.github.traderjoe95.mls.interop.proto.ProposalResponse>(
                service, METHODID_GROUP_CONTEXT_EXTENSIONS_PROPOSAL)))
        .addMethod(
          getCommitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.CommitRequest,
              com.github.traderjoe95.mls.interop.proto.CommitResponse>(
                service, METHODID_COMMIT)))
        .addMethod(
          getHandleCommitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.HandleCommitRequest,
              com.github.traderjoe95.mls.interop.proto.HandleCommitResponse>(
                service, METHODID_HANDLE_COMMIT)))
        .addMethod(
          getHandlePendingCommitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest,
              com.github.traderjoe95.mls.interop.proto.HandleCommitResponse>(
                service, METHODID_HANDLE_PENDING_COMMIT)))
        .addMethod(
          getReInitProposalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest,
              com.github.traderjoe95.mls.interop.proto.ProposalResponse>(
                service, METHODID_RE_INIT_PROPOSAL)))
        .addMethod(
          getReInitCommitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.CommitRequest,
              com.github.traderjoe95.mls.interop.proto.CommitResponse>(
                service, METHODID_RE_INIT_COMMIT)))
        .addMethod(
          getHandlePendingReInitCommitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest,
              com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse>(
                service, METHODID_HANDLE_PENDING_RE_INIT_COMMIT)))
        .addMethod(
          getHandleReInitCommitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.HandleCommitRequest,
              com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse>(
                service, METHODID_HANDLE_RE_INIT_COMMIT)))
        .addMethod(
          getReInitWelcomeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest,
              com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse>(
                service, METHODID_RE_INIT_WELCOME)))
        .addMethod(
          getHandleReInitWelcomeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest,
              com.github.traderjoe95.mls.interop.proto.JoinGroupResponse>(
                service, METHODID_HANDLE_RE_INIT_WELCOME)))
        .addMethod(
          getCreateBranchMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.CreateBranchRequest,
              com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse>(
                service, METHODID_CREATE_BRANCH)))
        .addMethod(
          getHandleBranchMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.HandleBranchRequest,
              com.github.traderjoe95.mls.interop.proto.HandleBranchResponse>(
                service, METHODID_HANDLE_BRANCH)))
        .addMethod(
          getNewMemberAddProposalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest,
              com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse>(
                service, METHODID_NEW_MEMBER_ADD_PROPOSAL)))
        .addMethod(
          getCreateExternalSignerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest,
              com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse>(
                service, METHODID_CREATE_EXTERNAL_SIGNER)))
        .addMethod(
          getAddExternalSignerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest,
              com.github.traderjoe95.mls.interop.proto.ProposalResponse>(
                service, METHODID_ADD_EXTERNAL_SIGNER)))
        .addMethod(
          getExternalSignerProposalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest,
              com.github.traderjoe95.mls.interop.proto.ProposalResponse>(
                service, METHODID_EXTERNAL_SIGNER_PROPOSAL)))
        .addMethod(
          getFreeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.github.traderjoe95.mls.interop.proto.FreeRequest,
              com.github.traderjoe95.mls.interop.proto.FreeResponse>(
                service, METHODID_FREE)))
        .build();
  }

  private static abstract class MLSClientBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MLSClientBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.github.traderjoe95.mls.interop.proto.MlsClientProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MLSClient");
    }
  }

  private static final class MLSClientFileDescriptorSupplier
      extends MLSClientBaseDescriptorSupplier {
    MLSClientFileDescriptorSupplier() {}
  }

  private static final class MLSClientMethodDescriptorSupplier
      extends MLSClientBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    MLSClientMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MLSClientGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MLSClientFileDescriptorSupplier())
              .addMethod(getNameMethod())
              .addMethod(getSupportedCiphersuitesMethod())
              .addMethod(getCreateGroupMethod())
              .addMethod(getCreateKeyPackageMethod())
              .addMethod(getJoinGroupMethod())
              .addMethod(getExternalJoinMethod())
              .addMethod(getGroupInfoMethod())
              .addMethod(getStateAuthMethod())
              .addMethod(getExportMethod())
              .addMethod(getProtectMethod())
              .addMethod(getUnprotectMethod())
              .addMethod(getStorePSKMethod())
              .addMethod(getAddProposalMethod())
              .addMethod(getUpdateProposalMethod())
              .addMethod(getRemoveProposalMethod())
              .addMethod(getExternalPSKProposalMethod())
              .addMethod(getResumptionPSKProposalMethod())
              .addMethod(getGroupContextExtensionsProposalMethod())
              .addMethod(getCommitMethod())
              .addMethod(getHandleCommitMethod())
              .addMethod(getHandlePendingCommitMethod())
              .addMethod(getReInitProposalMethod())
              .addMethod(getReInitCommitMethod())
              .addMethod(getHandlePendingReInitCommitMethod())
              .addMethod(getHandleReInitCommitMethod())
              .addMethod(getReInitWelcomeMethod())
              .addMethod(getHandleReInitWelcomeMethod())
              .addMethod(getCreateBranchMethod())
              .addMethod(getHandleBranchMethod())
              .addMethod(getNewMemberAddProposalMethod())
              .addMethod(getCreateExternalSignerMethod())
              .addMethod(getAddExternalSignerMethod())
              .addMethod(getExternalSignerProposalMethod())
              .addMethod(getFreeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
