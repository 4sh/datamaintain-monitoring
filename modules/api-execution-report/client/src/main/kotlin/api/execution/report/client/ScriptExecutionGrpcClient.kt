package api.execution.report.client

import api.execution.report.domain.module.script.execution.ExecutionStatus
import api.execution.report.domain.module.script.execution.ScriptExecutionRepository
import api.execution.report.domain.module.script.execution.ScriptExecutionStopRequest
import io.grpc.ManagedChannel
import proto.ScriptExecutionApi
import proto.ScriptExecutionServiceGrpcKt
import proto.scriptExecutionCreationRequest
import proto.scriptExecutionStopRequest
import java.time.Instant
import java.util.*

class ScriptExecutionGrpcClient(channel: ManagedChannel) : AbstractGrpcClient(channel), ScriptExecutionRepository {
    private val stub = ScriptExecutionServiceGrpcKt.ScriptExecutionServiceCoroutineStub(channel)

    override suspend fun createScriptExecution(
        scriptStartDate: Instant,
        scriptExecutionOrderIndex: Int,
        scriptTags: List<String>,
        scriptBatchExecutionId: UUID,
        executedScriptChecksum: String
    ): UUID =
        UUID.fromString(stub.createScriptExecution(scriptExecutionCreationRequest {
            startDate = scriptStartDate.toTimestamp()
            executionOrderIndex = scriptExecutionOrderIndex
            tags.addAll(scriptTags)
            batchExecutionId = scriptBatchExecutionId.toString()
            scriptChecksum = executedScriptChecksum
        }).scriptExecutionId)

    override suspend fun endScriptExecution(
        scriptExecutionId: UUID,
        scriptExecutionStopRequest: ScriptExecutionStopRequest
    ) {
        stub.stopScriptExecution(
            scriptExecutionStopRequest {
                endDate = scriptExecutionStopRequest.executionEndDate.toTimestamp()
                status = scriptExecutionStopRequest.executionStatus.toApi()
                output = scriptExecutionStopRequest.executionOutput?: ""
                executionId = scriptExecutionId.toString()
            }
        )
    }
}

private fun ExecutionStatus.toApi(): ScriptExecutionApi.ScriptExecutionStopRequest.ExecutionStatus =
    when(this) {
        ExecutionStatus.OK -> ScriptExecutionApi.ScriptExecutionStopRequest.ExecutionStatus.OK
        ExecutionStatus.KO -> ScriptExecutionApi.ScriptExecutionStopRequest.ExecutionStatus.KO
    }
