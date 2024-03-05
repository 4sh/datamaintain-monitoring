import execution.batch.BatchExecutionCreationRequest
import execution.batch.BatchExecutionService
import execution.batch.Origin
import execution.batch.Type
import proto.BatchExecutionApi
import proto.BatchExecutionServiceGrpcKt
import proto.batchExecutionCreationResponse
import java.util.*

class BatchExecutionGrpcServiceImpl(private val batchExecutionService: BatchExecutionService) :
    BatchExecutionServiceGrpcKt.BatchExecutionServiceCoroutineImplBase() {
    override suspend fun createBatchExecution(
        request: BatchExecutionApi.BatchExecutionCreationRequest
    ): BatchExecutionApi.BatchExecutionCreationResponse =
        batchExecutionCreationResponse {
            id = batchExecutionService.insert(request.toDomain()).id.toString()
        }
}

private fun BatchExecutionApi.BatchExecutionCreationRequest.toDomain(): BatchExecutionCreationRequest =
    BatchExecutionCreationRequest(
        startDate = startDate.toInstant(),
        origin = origin.toDomain(),
        type = type.toDomain(),
        fkModuleRef = UUID.fromString(moduleRef),
        fkEnvironmentRef = UUID.fromString(environmentRef)
    )

private fun BatchExecutionApi.BatchExecutionCreationRequest.Type.toDomain(): Type = when (this) {
    BatchExecutionApi.BatchExecutionCreationRequest.Type.ON_DEMAND -> Type.ON_DEMAND
    BatchExecutionApi.BatchExecutionCreationRequest.Type.PLANNED -> Type.PLANNED
    BatchExecutionApi.BatchExecutionCreationRequest.Type.UNRECOGNIZED -> throw UnrecognizedTypeException
}

object UnrecognizedTypeException : IllegalArgumentException("")

private fun BatchExecutionApi.BatchExecutionCreationRequest.Origin.toDomain(): Origin = when (this) {
    BatchExecutionApi.BatchExecutionCreationRequest.Origin.CLIENT -> Origin.CLIENT
    BatchExecutionApi.BatchExecutionCreationRequest.Origin.SERVER -> Origin.SERVER
    BatchExecutionApi.BatchExecutionCreationRequest.Origin.TIER -> Origin.TIER
    BatchExecutionApi.BatchExecutionCreationRequest.Origin.UNRECOGNIZED -> throw UnrecognizedOriginException
}

object UnrecognizedOriginException: IllegalArgumentException("")