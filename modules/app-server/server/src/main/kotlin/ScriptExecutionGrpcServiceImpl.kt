import execution.script.ScriptExecutionCreationRequest
import execution.script.ScriptExecutionService
import proto.ScriptExecutionApi
import proto.ScriptExecutionServiceGrpcKt
import proto.scriptExecutionCreationResponse
import java.util.*

class ScriptExecutionGrpcServiceImpl(private val scriptExecutionService: ScriptExecutionService) :
    ScriptExecutionServiceGrpcKt.ScriptExecutionServiceCoroutineImplBase() {
    override suspend fun createScriptExecution(request: ScriptExecutionApi.ScriptExecutionCreationRequest): ScriptExecutionApi.ScriptExecutionCreationResponse {
        return scriptExecutionCreationResponse { scriptExecutionId = scriptExecutionService.insert(request.toScriptExecutionCreationRequest()).id.toString() }
    }
}

private fun ScriptExecutionApi.ScriptExecutionCreationRequest.toScriptExecutionCreationRequest(): ScriptExecutionCreationRequest {
    return ScriptExecutionCreationRequest(
        startDate = startDate.toInstant(),
        executionOrderIndex = executionOrderIndex,
        fkBatchExecutionRef = UUID.fromString(batchExecutionId),
        fkScriptRef = scriptChecksum
    )
}
