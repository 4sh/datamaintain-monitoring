import execution.Status
import execution.batch.*
import proto.BatchExecutionApi
import proto.BatchExecutionServiceGrpcKt
import proto.batchExecutionCreationResponse
import proto.batchExecutionStopResponse
import java.util.*

class BatchExecutionGrpcServiceImpl(private val batchExecutionService: BatchExecutionService) :
    BatchExecutionServiceGrpcKt.BatchExecutionServiceCoroutineImplBase() {
    override suspend fun createBatchExecution(
        request: BatchExecutionApi.BatchExecutionCreationRequest
    ): BatchExecutionApi.BatchExecutionCreationResponse =
        batchExecutionCreationResponse {
            id = batchExecutionService.insert(request.toDomain()).id.toString()
        }

    override suspend fun stopBatchExecution(request: BatchExecutionApi.BatchExecutionStopRequest): BatchExecutionApi.BatchExecutionStopResponse {
        batchExecutionService.updateBatchExecutionEndData(
            UUID.fromString(request.batchExecutionId),
            BatchExecutionEndUpdateRequest(
                request.endDate.toInstant(),
                request.endStatus.toDomain()
            )
        )
        return batchExecutionStopResponse { }
    }
}

private fun BatchExecutionApi.BatchExecutionStopRequest.BatchExecutionEndStatus.toDomain(): Status =
    when(this) {
        BatchExecutionApi.BatchExecutionStopRequest.BatchExecutionEndStatus.ERROR -> Status.ERROR
        BatchExecutionApi.BatchExecutionStopRequest.BatchExecutionEndStatus.COMPLETED -> Status.COMPLETED
        BatchExecutionApi.BatchExecutionStopRequest.BatchExecutionEndStatus.UNRECOGNIZED -> throw UnrecognizedBatchExecutionEndStatus()
    }

class UnrecognizedBatchExecutionEndStatus: IllegalArgumentException("Given batch execution end status was not recognized")

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

object UnrecognizedTypeException : IllegalArgumentException("Given type was not recognized")

private fun BatchExecutionApi.BatchExecutionCreationRequest.Origin.toDomain(): Origin = when (this) {
    BatchExecutionApi.BatchExecutionCreationRequest.Origin.CLIENT -> Origin.CLIENT
    BatchExecutionApi.BatchExecutionCreationRequest.Origin.SERVER -> Origin.SERVER
    BatchExecutionApi.BatchExecutionCreationRequest.Origin.TIER -> Origin.TIER
    BatchExecutionApi.BatchExecutionCreationRequest.Origin.UNRECOGNIZED -> throw UnrecognizedOriginException
}

object UnrecognizedOriginException: IllegalArgumentException("Given origin was not recognized")