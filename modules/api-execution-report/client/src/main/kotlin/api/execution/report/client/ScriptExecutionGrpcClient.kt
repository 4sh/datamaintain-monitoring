package api.execution.report.client

import api.execution.report.domain.module.script.execution.ScriptExecutionRepository
import io.grpc.ManagedChannel
import proto.ScriptExecutionServiceGrpcKt
import proto.scriptExecutionCreationRequest
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
}