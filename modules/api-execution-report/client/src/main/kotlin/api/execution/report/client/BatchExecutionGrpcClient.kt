package api.execution.report.client

import api.execution.report.domain.module.batch.execution.BatchExecutionRepository
import io.grpc.ManagedChannel
import proto.BatchExecutionApi
import proto.BatchExecutionServiceGrpcKt
import proto.batchExecutionCreationRequest
import java.time.Instant
import java.util.*

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
}