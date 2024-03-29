package com.github.traderjoe95.mls.interop.proto

import com.github.traderjoe95.mls.interop.proto.MLSClientGrpc.getServiceDescriptor
import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Holder for Kotlin coroutine-based client and server APIs for mls_client.MLSClient.
 */
public object MLSClientGrpcKt {
  public const val SERVICE_NAME: String = MLSClientGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = getServiceDescriptor()

  public val nameMethod: MethodDescriptor<NameRequest, NameResponse>
    @JvmStatic
    get() = MLSClientGrpc.getNameMethod()

  public val supportedCiphersuitesMethod:
      MethodDescriptor<SupportedCiphersuitesRequest, SupportedCiphersuitesResponse>
    @JvmStatic
    get() = MLSClientGrpc.getSupportedCiphersuitesMethod()

  public val createGroupMethod: MethodDescriptor<CreateGroupRequest, CreateGroupResponse>
    @JvmStatic
    get() = MLSClientGrpc.getCreateGroupMethod()

  public val createKeyPackageMethod:
      MethodDescriptor<CreateKeyPackageRequest, CreateKeyPackageResponse>
    @JvmStatic
    get() = MLSClientGrpc.getCreateKeyPackageMethod()

  public val joinGroupMethod: MethodDescriptor<JoinGroupRequest, JoinGroupResponse>
    @JvmStatic
    get() = MLSClientGrpc.getJoinGroupMethod()

  public val externalJoinMethod: MethodDescriptor<ExternalJoinRequest, ExternalJoinResponse>
    @JvmStatic
    get() = MLSClientGrpc.getExternalJoinMethod()

  public val groupInfoMethod: MethodDescriptor<GroupInfoRequest, GroupInfoResponse>
    @JvmStatic
    get() = MLSClientGrpc.getGroupInfoMethod()

  public val stateAuthMethod: MethodDescriptor<StateAuthRequest, StateAuthResponse>
    @JvmStatic
    get() = MLSClientGrpc.getStateAuthMethod()

  public val exportMethod: MethodDescriptor<ExportRequest, ExportResponse>
    @JvmStatic
    get() = MLSClientGrpc.getExportMethod()

  public val protectMethod: MethodDescriptor<ProtectRequest, ProtectResponse>
    @JvmStatic
    get() = MLSClientGrpc.getProtectMethod()

  public val unprotectMethod: MethodDescriptor<UnprotectRequest, UnprotectResponse>
    @JvmStatic
    get() = MLSClientGrpc.getUnprotectMethod()

  public val storePSKMethod: MethodDescriptor<StorePSKRequest, StorePSKResponse>
    @JvmStatic
    get() = MLSClientGrpc.getStorePSKMethod()

  public val addProposalMethod: MethodDescriptor<AddProposalRequest, ProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getAddProposalMethod()

  public val updateProposalMethod: MethodDescriptor<UpdateProposalRequest, ProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getUpdateProposalMethod()

  public val removeProposalMethod: MethodDescriptor<RemoveProposalRequest, ProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getRemoveProposalMethod()

  public val externalPSKProposalMethod:
      MethodDescriptor<ExternalPSKProposalRequest, ProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getExternalPSKProposalMethod()

  public val resumptionPSKProposalMethod:
      MethodDescriptor<ResumptionPSKProposalRequest, ProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getResumptionPSKProposalMethod()

  public val groupContextExtensionsProposalMethod:
      MethodDescriptor<GroupContextExtensionsProposalRequest, ProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getGroupContextExtensionsProposalMethod()

  public val commitMethod: MethodDescriptor<CommitRequest, CommitResponse>
    @JvmStatic
    get() = MLSClientGrpc.getCommitMethod()

  public val handleCommitMethod: MethodDescriptor<HandleCommitRequest, HandleCommitResponse>
    @JvmStatic
    get() = MLSClientGrpc.getHandleCommitMethod()

  public val handlePendingCommitMethod:
      MethodDescriptor<HandlePendingCommitRequest, HandleCommitResponse>
    @JvmStatic
    get() = MLSClientGrpc.getHandlePendingCommitMethod()

  public val reInitProposalMethod: MethodDescriptor<ReInitProposalRequest, ProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getReInitProposalMethod()

  public val reInitCommitMethod: MethodDescriptor<CommitRequest, CommitResponse>
    @JvmStatic
    get() = MLSClientGrpc.getReInitCommitMethod()

  public val handlePendingReInitCommitMethod:
      MethodDescriptor<HandlePendingCommitRequest, HandleReInitCommitResponse>
    @JvmStatic
    get() = MLSClientGrpc.getHandlePendingReInitCommitMethod()

  public val handleReInitCommitMethod:
      MethodDescriptor<HandleCommitRequest, HandleReInitCommitResponse>
    @JvmStatic
    get() = MLSClientGrpc.getHandleReInitCommitMethod()

  public val reInitWelcomeMethod: MethodDescriptor<ReInitWelcomeRequest, CreateSubgroupResponse>
    @JvmStatic
    get() = MLSClientGrpc.getReInitWelcomeMethod()

  public val handleReInitWelcomeMethod:
      MethodDescriptor<HandleReInitWelcomeRequest, JoinGroupResponse>
    @JvmStatic
    get() = MLSClientGrpc.getHandleReInitWelcomeMethod()

  public val createBranchMethod: MethodDescriptor<CreateBranchRequest, CreateSubgroupResponse>
    @JvmStatic
    get() = MLSClientGrpc.getCreateBranchMethod()

  public val handleBranchMethod: MethodDescriptor<HandleBranchRequest, HandleBranchResponse>
    @JvmStatic
    get() = MLSClientGrpc.getHandleBranchMethod()

  public val newMemberAddProposalMethod:
      MethodDescriptor<NewMemberAddProposalRequest, NewMemberAddProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getNewMemberAddProposalMethod()

  public val createExternalSignerMethod:
      MethodDescriptor<CreateExternalSignerRequest, CreateExternalSignerResponse>
    @JvmStatic
    get() = MLSClientGrpc.getCreateExternalSignerMethod()

  public val addExternalSignerMethod: MethodDescriptor<AddExternalSignerRequest, ProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getAddExternalSignerMethod()

  public val externalSignerProposalMethod:
      MethodDescriptor<ExternalSignerProposalRequest, ProposalResponse>
    @JvmStatic
    get() = MLSClientGrpc.getExternalSignerProposalMethod()

  public val freeMethod: MethodDescriptor<FreeRequest, FreeResponse>
    @JvmStatic
    get() = MLSClientGrpc.getFreeMethod()

  /**
   * A stub for issuing RPCs to a(n) mls_client.MLSClient service as suspending coroutines.
   */
  @StubFor(MLSClientGrpc::class)
  public class MLSClientCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<MLSClientCoroutineStub>(channel, callOptions) {
    override fun build(channel: Channel, callOptions: CallOptions): MLSClientCoroutineStub =
        MLSClientCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun name(request: NameRequest, headers: Metadata = Metadata()): NameResponse =
        unaryRpc(
      channel,
      MLSClientGrpc.getNameMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun supportedCiphersuites(request: SupportedCiphersuitesRequest,
        headers: Metadata = Metadata()): SupportedCiphersuitesResponse = unaryRpc(
      channel,
      MLSClientGrpc.getSupportedCiphersuitesMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun createGroup(request: CreateGroupRequest, headers: Metadata = Metadata()):
        CreateGroupResponse = unaryRpc(
      channel,
      MLSClientGrpc.getCreateGroupMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun createKeyPackage(request: CreateKeyPackageRequest, headers: Metadata =
        Metadata()): CreateKeyPackageResponse = unaryRpc(
      channel,
      MLSClientGrpc.getCreateKeyPackageMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun joinGroup(request: JoinGroupRequest, headers: Metadata = Metadata()):
        JoinGroupResponse = unaryRpc(
      channel,
      MLSClientGrpc.getJoinGroupMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun externalJoin(request: ExternalJoinRequest, headers: Metadata = Metadata()):
        ExternalJoinResponse = unaryRpc(
      channel,
      MLSClientGrpc.getExternalJoinMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun groupInfo(request: GroupInfoRequest, headers: Metadata = Metadata()):
        GroupInfoResponse = unaryRpc(
      channel,
      MLSClientGrpc.getGroupInfoMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun stateAuth(request: StateAuthRequest, headers: Metadata = Metadata()):
        StateAuthResponse = unaryRpc(
      channel,
      MLSClientGrpc.getStateAuthMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun export(request: ExportRequest, headers: Metadata = Metadata()):
        ExportResponse = unaryRpc(
      channel,
      MLSClientGrpc.getExportMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun protect(request: ProtectRequest, headers: Metadata = Metadata()):
        ProtectResponse = unaryRpc(
      channel,
      MLSClientGrpc.getProtectMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun unprotect(request: UnprotectRequest, headers: Metadata = Metadata()):
        UnprotectResponse = unaryRpc(
      channel,
      MLSClientGrpc.getUnprotectMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun storePSK(request: StorePSKRequest, headers: Metadata = Metadata()):
        StorePSKResponse = unaryRpc(
      channel,
      MLSClientGrpc.getStorePSKMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun addProposal(request: AddProposalRequest, headers: Metadata = Metadata()):
        ProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getAddProposalMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun updateProposal(request: UpdateProposalRequest, headers: Metadata =
        Metadata()): ProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getUpdateProposalMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun removeProposal(request: RemoveProposalRequest, headers: Metadata =
        Metadata()): ProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getRemoveProposalMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun externalPSKProposal(request: ExternalPSKProposalRequest, headers: Metadata =
        Metadata()): ProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getExternalPSKProposalMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun resumptionPSKProposal(request: ResumptionPSKProposalRequest,
        headers: Metadata = Metadata()): ProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getResumptionPSKProposalMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend
        fun groupContextExtensionsProposal(request: GroupContextExtensionsProposalRequest,
        headers: Metadata = Metadata()): ProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getGroupContextExtensionsProposalMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun commit(request: CommitRequest, headers: Metadata = Metadata()):
        CommitResponse = unaryRpc(
      channel,
      MLSClientGrpc.getCommitMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun handleCommit(request: HandleCommitRequest, headers: Metadata = Metadata()):
        HandleCommitResponse = unaryRpc(
      channel,
      MLSClientGrpc.getHandleCommitMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun handlePendingCommit(request: HandlePendingCommitRequest, headers: Metadata =
        Metadata()): HandleCommitResponse = unaryRpc(
      channel,
      MLSClientGrpc.getHandlePendingCommitMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun reInitProposal(request: ReInitProposalRequest, headers: Metadata =
        Metadata()): ProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getReInitProposalMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun reInitCommit(request: CommitRequest, headers: Metadata = Metadata()):
        CommitResponse = unaryRpc(
      channel,
      MLSClientGrpc.getReInitCommitMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun handlePendingReInitCommit(request: HandlePendingCommitRequest,
        headers: Metadata = Metadata()): HandleReInitCommitResponse = unaryRpc(
      channel,
      MLSClientGrpc.getHandlePendingReInitCommitMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun handleReInitCommit(request: HandleCommitRequest, headers: Metadata =
        Metadata()): HandleReInitCommitResponse = unaryRpc(
      channel,
      MLSClientGrpc.getHandleReInitCommitMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun reInitWelcome(request: ReInitWelcomeRequest, headers: Metadata = Metadata()):
        CreateSubgroupResponse = unaryRpc(
      channel,
      MLSClientGrpc.getReInitWelcomeMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun handleReInitWelcome(request: HandleReInitWelcomeRequest, headers: Metadata =
        Metadata()): JoinGroupResponse = unaryRpc(
      channel,
      MLSClientGrpc.getHandleReInitWelcomeMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun createBranch(request: CreateBranchRequest, headers: Metadata = Metadata()):
        CreateSubgroupResponse = unaryRpc(
      channel,
      MLSClientGrpc.getCreateBranchMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun handleBranch(request: HandleBranchRequest, headers: Metadata = Metadata()):
        HandleBranchResponse = unaryRpc(
      channel,
      MLSClientGrpc.getHandleBranchMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun newMemberAddProposal(request: NewMemberAddProposalRequest, headers: Metadata
        = Metadata()): NewMemberAddProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getNewMemberAddProposalMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun createExternalSigner(request: CreateExternalSignerRequest, headers: Metadata
        = Metadata()): CreateExternalSignerResponse = unaryRpc(
      channel,
      MLSClientGrpc.getCreateExternalSignerMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun addExternalSigner(request: AddExternalSignerRequest, headers: Metadata =
        Metadata()): ProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getAddExternalSignerMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun externalSignerProposal(request: ExternalSignerProposalRequest,
        headers: Metadata = Metadata()): ProposalResponse = unaryRpc(
      channel,
      MLSClientGrpc.getExternalSignerProposalMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun free(request: FreeRequest, headers: Metadata = Metadata()): FreeResponse =
        unaryRpc(
      channel,
      MLSClientGrpc.getFreeMethod(),
      request,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the mls_client.MLSClient service based on Kotlin coroutines.
   */
  public abstract class MLSClientCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for mls_client.MLSClient.Name.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun name(request: NameRequest): NameResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.Name is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.SupportedCiphersuites.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun supportedCiphersuites(request: SupportedCiphersuitesRequest):
        SupportedCiphersuitesResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.SupportedCiphersuites is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.CreateGroup.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun createGroup(request: CreateGroupRequest): CreateGroupResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.CreateGroup is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.CreateKeyPackage.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun createKeyPackage(request: CreateKeyPackageRequest):
        CreateKeyPackageResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.CreateKeyPackage is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.JoinGroup.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun joinGroup(request: JoinGroupRequest): JoinGroupResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.JoinGroup is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.ExternalJoin.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun externalJoin(request: ExternalJoinRequest): ExternalJoinResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.ExternalJoin is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.GroupInfo.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun groupInfo(request: GroupInfoRequest): GroupInfoResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.GroupInfo is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.StateAuth.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun stateAuth(request: StateAuthRequest): StateAuthResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.StateAuth is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.Export.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun export(request: ExportRequest): ExportResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.Export is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.Protect.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun protect(request: ProtectRequest): ProtectResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.Protect is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.Unprotect.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun unprotect(request: UnprotectRequest): UnprotectResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.Unprotect is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.StorePSK.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun storePSK(request: StorePSKRequest): StorePSKResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.StorePSK is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.AddProposal.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun addProposal(request: AddProposalRequest): ProposalResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.AddProposal is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.UpdateProposal.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun updateProposal(request: UpdateProposalRequest): ProposalResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.UpdateProposal is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.RemoveProposal.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun removeProposal(request: RemoveProposalRequest): ProposalResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.RemoveProposal is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.ExternalPSKProposal.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun externalPSKProposal(request: ExternalPSKProposalRequest):
        ProposalResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.ExternalPSKProposal is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.ResumptionPSKProposal.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun resumptionPSKProposal(request: ResumptionPSKProposalRequest):
        ProposalResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.ResumptionPSKProposal is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.GroupContextExtensionsProposal.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend
        fun groupContextExtensionsProposal(request: GroupContextExtensionsProposalRequest):
        ProposalResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.GroupContextExtensionsProposal is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.Commit.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun commit(request: CommitRequest): CommitResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.Commit is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.HandleCommit.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun handleCommit(request: HandleCommitRequest): HandleCommitResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.HandleCommit is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.HandlePendingCommit.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun handlePendingCommit(request: HandlePendingCommitRequest):
        HandleCommitResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.HandlePendingCommit is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.ReInitProposal.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun reInitProposal(request: ReInitProposalRequest): ProposalResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.ReInitProposal is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.ReInitCommit.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun reInitCommit(request: CommitRequest): CommitResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.ReInitCommit is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.HandlePendingReInitCommit.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun handlePendingReInitCommit(request: HandlePendingCommitRequest):
        HandleReInitCommitResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.HandlePendingReInitCommit is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.HandleReInitCommit.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun handleReInitCommit(request: HandleCommitRequest):
        HandleReInitCommitResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.HandleReInitCommit is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.ReInitWelcome.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun reInitWelcome(request: ReInitWelcomeRequest): CreateSubgroupResponse =
        throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.ReInitWelcome is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.HandleReInitWelcome.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun handleReInitWelcome(request: HandleReInitWelcomeRequest):
        JoinGroupResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.HandleReInitWelcome is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.CreateBranch.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun createBranch(request: CreateBranchRequest): CreateSubgroupResponse =
        throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.CreateBranch is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.HandleBranch.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun handleBranch(request: HandleBranchRequest): HandleBranchResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.HandleBranch is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.NewMemberAddProposal.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun newMemberAddProposal(request: NewMemberAddProposalRequest):
        NewMemberAddProposalResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.NewMemberAddProposal is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.CreateExternalSigner.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun createExternalSigner(request: CreateExternalSignerRequest):
        CreateExternalSignerResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.CreateExternalSigner is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.AddExternalSigner.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun addExternalSigner(request: AddExternalSignerRequest): ProposalResponse =
        throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.AddExternalSigner is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.ExternalSignerProposal.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun externalSignerProposal(request: ExternalSignerProposalRequest):
        ProposalResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.ExternalSignerProposal is unimplemented"))

    /**
     * Returns the response to an RPC for mls_client.MLSClient.Free.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun free(request: FreeRequest): FreeResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method mls_client.MLSClient.Free is unimplemented"))

    final override fun bindService(): ServerServiceDefinition = builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getNameMethod(),
      implementation = ::name
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getSupportedCiphersuitesMethod(),
      implementation = ::supportedCiphersuites
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getCreateGroupMethod(),
      implementation = ::createGroup
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getCreateKeyPackageMethod(),
      implementation = ::createKeyPackage
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getJoinGroupMethod(),
      implementation = ::joinGroup
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getExternalJoinMethod(),
      implementation = ::externalJoin
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getGroupInfoMethod(),
      implementation = ::groupInfo
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getStateAuthMethod(),
      implementation = ::stateAuth
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getExportMethod(),
      implementation = ::export
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getProtectMethod(),
      implementation = ::protect
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getUnprotectMethod(),
      implementation = ::unprotect
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getStorePSKMethod(),
      implementation = ::storePSK
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getAddProposalMethod(),
      implementation = ::addProposal
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getUpdateProposalMethod(),
      implementation = ::updateProposal
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getRemoveProposalMethod(),
      implementation = ::removeProposal
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getExternalPSKProposalMethod(),
      implementation = ::externalPSKProposal
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getResumptionPSKProposalMethod(),
      implementation = ::resumptionPSKProposal
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getGroupContextExtensionsProposalMethod(),
      implementation = ::groupContextExtensionsProposal
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getCommitMethod(),
      implementation = ::commit
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getHandleCommitMethod(),
      implementation = ::handleCommit
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getHandlePendingCommitMethod(),
      implementation = ::handlePendingCommit
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getReInitProposalMethod(),
      implementation = ::reInitProposal
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getReInitCommitMethod(),
      implementation = ::reInitCommit
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getHandlePendingReInitCommitMethod(),
      implementation = ::handlePendingReInitCommit
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getHandleReInitCommitMethod(),
      implementation = ::handleReInitCommit
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getReInitWelcomeMethod(),
      implementation = ::reInitWelcome
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getHandleReInitWelcomeMethod(),
      implementation = ::handleReInitWelcome
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getCreateBranchMethod(),
      implementation = ::createBranch
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getHandleBranchMethod(),
      implementation = ::handleBranch
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getNewMemberAddProposalMethod(),
      implementation = ::newMemberAddProposal
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getCreateExternalSignerMethod(),
      implementation = ::createExternalSigner
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getAddExternalSignerMethod(),
      implementation = ::addExternalSigner
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getExternalSignerProposalMethod(),
      implementation = ::externalSignerProposal
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = MLSClientGrpc.getFreeMethod(),
      implementation = ::free
    )).build()
  }
}
