package com.github.traderjoe95.mls.interop.proto;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.net.SocketAddress;
import io.vertx.grpc.client.GrpcClient;
import io.vertx.core.streams.ReadStream;
import io.vertx.core.streams.WriteStream;
import io.vertx.grpc.common.GrpcStatus;

public class VertxMLSClientGrpcClient {
  private final GrpcClient client;
  private final SocketAddress socketAddress;

  public VertxMLSClientGrpcClient(GrpcClient client, SocketAddress socketAddress) {
    this.client = client;
    this.socketAddress = socketAddress;
  }

  public Future<com.github.traderjoe95.mls.interop.proto.NameResponse> name(com.github.traderjoe95.mls.interop.proto.NameRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getNameMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> supportedCiphersuites(com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getSupportedCiphersuitesMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> createGroup(com.github.traderjoe95.mls.interop.proto.CreateGroupRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getCreateGroupMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> createKeyPackage(com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getCreateKeyPackageMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> joinGroup(com.github.traderjoe95.mls.interop.proto.JoinGroupRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getJoinGroupMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> externalJoin(com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getExternalJoinMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> groupInfo(com.github.traderjoe95.mls.interop.proto.GroupInfoRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getGroupInfoMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.StateAuthResponse> stateAuth(com.github.traderjoe95.mls.interop.proto.StateAuthRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getStateAuthMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ExportResponse> export(com.github.traderjoe95.mls.interop.proto.ExportRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getExportMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProtectResponse> protect(com.github.traderjoe95.mls.interop.proto.ProtectRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getProtectMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.UnprotectResponse> unprotect(com.github.traderjoe95.mls.interop.proto.UnprotectRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getUnprotectMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.StorePSKResponse> storePSK(com.github.traderjoe95.mls.interop.proto.StorePSKRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getStorePSKMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> addProposal(com.github.traderjoe95.mls.interop.proto.AddProposalRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getAddProposalMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> updateProposal(com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getUpdateProposalMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> removeProposal(com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getRemoveProposalMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> externalPSKProposal(com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getExternalPSKProposalMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> resumptionPSKProposal(com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getResumptionPSKProposalMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> groupContextExtensionsProposal(com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getGroupContextExtensionsProposalMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.CommitResponse> commit(com.github.traderjoe95.mls.interop.proto.CommitRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getCommitMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> handleCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getHandleCommitMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> handlePendingCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getHandlePendingCommitMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> reInitProposal(com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getReInitProposalMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.CommitResponse> reInitCommit(com.github.traderjoe95.mls.interop.proto.CommitRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getReInitCommitMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> handlePendingReInitCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getHandlePendingReInitCommitMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> handleReInitCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getHandleReInitCommitMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> reInitWelcome(com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getReInitWelcomeMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> handleReInitWelcome(com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getHandleReInitWelcomeMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> createBranch(com.github.traderjoe95.mls.interop.proto.CreateBranchRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getCreateBranchMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> handleBranch(com.github.traderjoe95.mls.interop.proto.HandleBranchRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getHandleBranchMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> newMemberAddProposal(com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getNewMemberAddProposalMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> createExternalSigner(com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getCreateExternalSignerMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> addExternalSigner(com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getAddExternalSignerMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> externalSignerProposal(com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getExternalSignerProposalMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

  public Future<com.github.traderjoe95.mls.interop.proto.FreeResponse> free(com.github.traderjoe95.mls.interop.proto.FreeRequest request) {
    return client.request(socketAddress, MLSClientGrpc.getFreeMethod()).compose(req -> {
      req.end(request);
      return req.response().compose(resp -> resp.last());
    });
  }

}
