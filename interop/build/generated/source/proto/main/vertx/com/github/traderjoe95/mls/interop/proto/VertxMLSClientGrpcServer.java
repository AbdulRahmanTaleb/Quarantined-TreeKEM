package com.github.traderjoe95.mls.interop.proto;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.streams.ReadStream;
import io.vertx.core.streams.WriteStream;
import io.vertx.grpc.common.GrpcStatus;
import io.vertx.grpc.server.GrpcServer;
import io.vertx.grpc.server.GrpcServerResponse;

import java.util.ArrayList;
import java.util.List;

public class VertxMLSClientGrpcServer  {
  public interface MLSClientApi {
    default Future<com.github.traderjoe95.mls.interop.proto.NameResponse> name(com.github.traderjoe95.mls.interop.proto.NameRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void name(com.github.traderjoe95.mls.interop.proto.NameRequest request, Promise<com.github.traderjoe95.mls.interop.proto.NameResponse> response) {
      name(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> supportedCiphersuites(com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void supportedCiphersuites(com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesRequest request, Promise<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> response) {
      supportedCiphersuites(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> createGroup(com.github.traderjoe95.mls.interop.proto.CreateGroupRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void createGroup(com.github.traderjoe95.mls.interop.proto.CreateGroupRequest request, Promise<com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> response) {
      createGroup(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> createKeyPackage(com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void createKeyPackage(com.github.traderjoe95.mls.interop.proto.CreateKeyPackageRequest request, Promise<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> response) {
      createKeyPackage(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> joinGroup(com.github.traderjoe95.mls.interop.proto.JoinGroupRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void joinGroup(com.github.traderjoe95.mls.interop.proto.JoinGroupRequest request, Promise<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> response) {
      joinGroup(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> externalJoin(com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void externalJoin(com.github.traderjoe95.mls.interop.proto.ExternalJoinRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> response) {
      externalJoin(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> groupInfo(com.github.traderjoe95.mls.interop.proto.GroupInfoRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void groupInfo(com.github.traderjoe95.mls.interop.proto.GroupInfoRequest request, Promise<com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> response) {
      groupInfo(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.StateAuthResponse> stateAuth(com.github.traderjoe95.mls.interop.proto.StateAuthRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void stateAuth(com.github.traderjoe95.mls.interop.proto.StateAuthRequest request, Promise<com.github.traderjoe95.mls.interop.proto.StateAuthResponse> response) {
      stateAuth(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ExportResponse> export(com.github.traderjoe95.mls.interop.proto.ExportRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void export(com.github.traderjoe95.mls.interop.proto.ExportRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ExportResponse> response) {
      export(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProtectResponse> protect(com.github.traderjoe95.mls.interop.proto.ProtectRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void protect(com.github.traderjoe95.mls.interop.proto.ProtectRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProtectResponse> response) {
      protect(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.UnprotectResponse> unprotect(com.github.traderjoe95.mls.interop.proto.UnprotectRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void unprotect(com.github.traderjoe95.mls.interop.proto.UnprotectRequest request, Promise<com.github.traderjoe95.mls.interop.proto.UnprotectResponse> response) {
      unprotect(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.StorePSKResponse> storePSK(com.github.traderjoe95.mls.interop.proto.StorePSKRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void storePSK(com.github.traderjoe95.mls.interop.proto.StorePSKRequest request, Promise<com.github.traderjoe95.mls.interop.proto.StorePSKResponse> response) {
      storePSK(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> addProposal(com.github.traderjoe95.mls.interop.proto.AddProposalRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void addProposal(com.github.traderjoe95.mls.interop.proto.AddProposalRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> response) {
      addProposal(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> updateProposal(com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void updateProposal(com.github.traderjoe95.mls.interop.proto.UpdateProposalRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> response) {
      updateProposal(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> removeProposal(com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void removeProposal(com.github.traderjoe95.mls.interop.proto.RemoveProposalRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> response) {
      removeProposal(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> externalPSKProposal(com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void externalPSKProposal(com.github.traderjoe95.mls.interop.proto.ExternalPSKProposalRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> response) {
      externalPSKProposal(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> resumptionPSKProposal(com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void resumptionPSKProposal(com.github.traderjoe95.mls.interop.proto.ResumptionPSKProposalRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> response) {
      resumptionPSKProposal(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> groupContextExtensionsProposal(com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void groupContextExtensionsProposal(com.github.traderjoe95.mls.interop.proto.GroupContextExtensionsProposalRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> response) {
      groupContextExtensionsProposal(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.CommitResponse> commit(com.github.traderjoe95.mls.interop.proto.CommitRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void commit(com.github.traderjoe95.mls.interop.proto.CommitRequest request, Promise<com.github.traderjoe95.mls.interop.proto.CommitResponse> response) {
      commit(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> handleCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void handleCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request, Promise<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> response) {
      handleCommit(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> handlePendingCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void handlePendingCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request, Promise<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> response) {
      handlePendingCommit(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> reInitProposal(com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void reInitProposal(com.github.traderjoe95.mls.interop.proto.ReInitProposalRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> response) {
      reInitProposal(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.CommitResponse> reInitCommit(com.github.traderjoe95.mls.interop.proto.CommitRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void reInitCommit(com.github.traderjoe95.mls.interop.proto.CommitRequest request, Promise<com.github.traderjoe95.mls.interop.proto.CommitResponse> response) {
      reInitCommit(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> handlePendingReInitCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void handlePendingReInitCommit(com.github.traderjoe95.mls.interop.proto.HandlePendingCommitRequest request, Promise<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> response) {
      handlePendingReInitCommit(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> handleReInitCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void handleReInitCommit(com.github.traderjoe95.mls.interop.proto.HandleCommitRequest request, Promise<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> response) {
      handleReInitCommit(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> reInitWelcome(com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void reInitWelcome(com.github.traderjoe95.mls.interop.proto.ReInitWelcomeRequest request, Promise<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> response) {
      reInitWelcome(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> handleReInitWelcome(com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void handleReInitWelcome(com.github.traderjoe95.mls.interop.proto.HandleReInitWelcomeRequest request, Promise<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> response) {
      handleReInitWelcome(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> createBranch(com.github.traderjoe95.mls.interop.proto.CreateBranchRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void createBranch(com.github.traderjoe95.mls.interop.proto.CreateBranchRequest request, Promise<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> response) {
      createBranch(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> handleBranch(com.github.traderjoe95.mls.interop.proto.HandleBranchRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void handleBranch(com.github.traderjoe95.mls.interop.proto.HandleBranchRequest request, Promise<com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> response) {
      handleBranch(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> newMemberAddProposal(com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void newMemberAddProposal(com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalRequest request, Promise<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> response) {
      newMemberAddProposal(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> createExternalSigner(com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void createExternalSigner(com.github.traderjoe95.mls.interop.proto.CreateExternalSignerRequest request, Promise<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> response) {
      createExternalSigner(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> addExternalSigner(com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void addExternalSigner(com.github.traderjoe95.mls.interop.proto.AddExternalSignerRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> response) {
      addExternalSigner(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.ProposalResponse> externalSignerProposal(com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void externalSignerProposal(com.github.traderjoe95.mls.interop.proto.ExternalSignerProposalRequest request, Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> response) {
      externalSignerProposal(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }
    default Future<com.github.traderjoe95.mls.interop.proto.FreeResponse> free(com.github.traderjoe95.mls.interop.proto.FreeRequest request) {
      throw new UnsupportedOperationException("Not implemented");
    }
    default void free(com.github.traderjoe95.mls.interop.proto.FreeRequest request, Promise<com.github.traderjoe95.mls.interop.proto.FreeResponse> response) {
      free(request)
        .onSuccess(msg -> response.complete(msg))
        .onFailure(error -> response.fail(error));
    }

    default MLSClientApi bind_name(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getNameMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.NameResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            name(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_supportedCiphersuites(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getSupportedCiphersuitesMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.SupportedCiphersuitesResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            supportedCiphersuites(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_createGroup(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getCreateGroupMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.CreateGroupResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            createGroup(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_createKeyPackage(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getCreateKeyPackageMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.CreateKeyPackageResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            createKeyPackage(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_joinGroup(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getJoinGroupMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            joinGroup(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_externalJoin(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getExternalJoinMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ExternalJoinResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            externalJoin(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_groupInfo(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getGroupInfoMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.GroupInfoResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            groupInfo(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_stateAuth(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getStateAuthMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.StateAuthResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            stateAuth(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_export(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getExportMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ExportResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            export(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_protect(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getProtectMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProtectResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            protect(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_unprotect(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getUnprotectMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.UnprotectResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            unprotect(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_storePSK(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getStorePSKMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.StorePSKResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            storePSK(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_addProposal(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getAddProposalMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            addProposal(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_updateProposal(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getUpdateProposalMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            updateProposal(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_removeProposal(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getRemoveProposalMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            removeProposal(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_externalPSKProposal(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getExternalPSKProposalMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            externalPSKProposal(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_resumptionPSKProposal(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getResumptionPSKProposalMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            resumptionPSKProposal(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_groupContextExtensionsProposal(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getGroupContextExtensionsProposalMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            groupContextExtensionsProposal(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_commit(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getCommitMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.CommitResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            commit(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_handleCommit(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getHandleCommitMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            handleCommit(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_handlePendingCommit(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getHandlePendingCommitMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.HandleCommitResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            handlePendingCommit(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_reInitProposal(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getReInitProposalMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            reInitProposal(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_reInitCommit(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getReInitCommitMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.CommitResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            reInitCommit(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_handlePendingReInitCommit(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getHandlePendingReInitCommitMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            handlePendingReInitCommit(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_handleReInitCommit(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getHandleReInitCommitMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.HandleReInitCommitResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            handleReInitCommit(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_reInitWelcome(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getReInitWelcomeMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            reInitWelcome(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_handleReInitWelcome(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getHandleReInitWelcomeMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.JoinGroupResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            handleReInitWelcome(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_createBranch(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getCreateBranchMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.CreateSubgroupResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            createBranch(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_handleBranch(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getHandleBranchMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.HandleBranchResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            handleBranch(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_newMemberAddProposal(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getNewMemberAddProposalMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.NewMemberAddProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            newMemberAddProposal(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_createExternalSigner(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getCreateExternalSignerMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.CreateExternalSignerResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            createExternalSigner(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_addExternalSigner(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getAddExternalSignerMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            addExternalSigner(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_externalSignerProposal(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getExternalSignerProposalMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.ProposalResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            externalSignerProposal(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }
    default MLSClientApi bind_free(GrpcServer server) {
      server.callHandler(MLSClientGrpc.getFreeMethod(), request -> {
        Promise<com.github.traderjoe95.mls.interop.proto.FreeResponse> promise = Promise.promise();
        request.handler(req -> {
          try {
            free(req, promise);
          } catch (RuntimeException err) {
            promise.tryFail(err);
          }
        });
        promise.future()
          .onFailure(err -> request.response().status(GrpcStatus.INTERNAL).end())
          .onSuccess(resp -> request.response().end(resp));
      });
      return this;
    }

    default MLSClientApi bindAll(GrpcServer server) {
      bind_name(server);
      bind_supportedCiphersuites(server);
      bind_createGroup(server);
      bind_createKeyPackage(server);
      bind_joinGroup(server);
      bind_externalJoin(server);
      bind_groupInfo(server);
      bind_stateAuth(server);
      bind_export(server);
      bind_protect(server);
      bind_unprotect(server);
      bind_storePSK(server);
      bind_addProposal(server);
      bind_updateProposal(server);
      bind_removeProposal(server);
      bind_externalPSKProposal(server);
      bind_resumptionPSKProposal(server);
      bind_groupContextExtensionsProposal(server);
      bind_commit(server);
      bind_handleCommit(server);
      bind_handlePendingCommit(server);
      bind_reInitProposal(server);
      bind_reInitCommit(server);
      bind_handlePendingReInitCommit(server);
      bind_handleReInitCommit(server);
      bind_reInitWelcome(server);
      bind_handleReInitWelcome(server);
      bind_createBranch(server);
      bind_handleBranch(server);
      bind_newMemberAddProposal(server);
      bind_createExternalSigner(server);
      bind_addExternalSigner(server);
      bind_externalSignerProposal(server);
      bind_free(server);
      return this;
    }
  }
}
