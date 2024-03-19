package api.execution.report.client

import api.execution.report.domain.module.batch.execution.BatchEndStatus
import api.execution.report.domain.module.batch.execution.BatchExecutionRepository
import io.grpc.ManagedChannel
import proto.BatchExecutionApi
import proto.BatchExecutionServiceGrpcKt
import proto.batchExecutionCreationRequest
import proto.batchExecutionStopRequest
import java.time.Instant
import java.util.*

private fun BatchEndStatus.toApi(): BatchExecutionApi.BatchExecutionStopRequest.BatchExecutionEndStatus =
    when (this) {
        BatchEndStatus.COMPLETED -> BatchExecutionApi.BatchExecutionStopRequest.BatchExecutionEndStatus.COMPLETED
        BatchEndStatus.ERROR -> BatchExecutionApi.BatchExecutionStopRequest.BatchExecutionEndStatus.ERROR
    }

class BatchExecutionGrpcClient(channel: ManagedChannel) : AbstractGrpcClient(channel), BatchExecutionRepository{
    private val stub = BatchExecutionServiceGrpcKt.BatchExecutionServiceCoroutineStub(channel)

    override suspend fun createBatchExecution(
        batchExecutionStartDate: Instant,
        batchExecutionModuleRef: UUID,
        batchExecutionEnvironmentRef: UUID,
    ): UUID {
        val request = batchExecutionCreationRequest {
            startDate = batchExecutionStartDate.toTimestamp()
            moduleRef = batchExecutionModuleRef.toString()
            environmentRef = batchExecutionEnvironmentRef.toString()
            origin = BatchExecutionApi.BatchExecutionCreationRequest.Origin.CLIENT
            type = BatchExecutionApi.BatchExecutionCreationRequest.Type.ON_DEMAND
        }
        return UUID.fromString(stub.createBatchExecution(request).id)
    }

    override suspend fun stopBatchExecution(
        executionId: UUID,
        batchExecutionEndDate: Instant,
        endStatusValue: BatchEndStatus
    ) {
        val request = batchExecutionStopRequest {
            batchExecutionId = executionId.toString()
            endStatus = endStatusValue.toApi()
            endDate = batchExecutionEndDate.toTimestamp()
        }
        stub.stopBatchExecution(request)
    }
}