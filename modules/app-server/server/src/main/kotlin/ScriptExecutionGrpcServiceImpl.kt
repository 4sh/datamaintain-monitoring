import execution.Status
import execution.script.ScriptExecutionCreationRequest
import execution.script.ScriptExecutionEndUpdateRequest
import execution.script.ScriptExecutionService
import proto.*
import java.util.*

class ScriptExecutionGrpcServiceImpl(private val scriptExecutionService: ScriptExecutionService) :
    ScriptExecutionServiceGrpcKt.ScriptExecutionServiceCoroutineImplBase() {
    override suspend fun createScriptExecution(request: ScriptExecutionApi.ScriptExecutionCreationRequest): ScriptExecutionApi.ScriptExecutionCreationResponse {
        return scriptExecutionCreationResponse { scriptExecutionId = scriptExecutionService.insert(request.toScriptExecutionCreationRequest()).id.toString() }
    }

    override suspend fun stopScriptExecution(request: ScriptExecutionApi.ScriptExecutionStopRequest): ScriptExecutionApi.ScriptExecutionStopResponse {
        scriptExecutionService.updateScriptExecutionEndData(
            scriptExecutionId = UUID.fromString(request.executionId),
            executionEndData = request.toScriptExecutionEndUpdateRequest()
        )
        return scriptExecutionStopResponse {  }
    }
}

private fun ScriptExecutionApi.ScriptExecutionStopRequest.toScriptExecutionEndUpdateRequest(): ScriptExecutionEndUpdateRequest = ScriptExecutionEndUpdateRequest(
    endDate = endDate.toInstant(),
    output = output,
    status = status.toDomain()
    )

private fun ScriptExecutionApi.ScriptExecutionStopRequest.ExecutionStatus.toDomain(): Status =
    when(this) {
        ScriptExecutionApi.ScriptExecutionStopRequest.ExecutionStatus.OK -> Status.COMPLETED
        ScriptExecutionApi.ScriptExecutionStopRequest.ExecutionStatus.KO -> Status.ERROR
        ScriptExecutionApi.ScriptExecutionStopRequest.ExecutionStatus.UNRECOGNIZED -> throw UnrecognizedStatusException
    }

object UnrecognizedStatusException : IllegalArgumentException("Given status was not recognized")


private fun ScriptExecutionApi.ScriptExecutionCreationRequest.toScriptExecutionCreationRequest(): ScriptExecutionCreationRequest {
    return ScriptExecutionCreationRequest(
        startDate = startDate.toInstant(),
        executionOrderIndex = executionOrderIndex,
        fkBatchExecutionRef = UUID.fromString(batchExecutionId),
        fkScriptRef = scriptChecksum
    )
}
