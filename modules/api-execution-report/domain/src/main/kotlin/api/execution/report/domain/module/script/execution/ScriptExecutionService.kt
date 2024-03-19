package api.execution.report.domain.module.script.execution

import java.util.UUID

class ScriptExecutionService(
    private val scriptExecutionRepository: ScriptExecutionRepository,
    private val scriptRepository: ScriptRepository
) {
    suspend fun createScriptExecution(scriptExecutionStart: ScriptExecutionStart, batchExecutionId: UUID): UUID {
        val scriptChecksum = scriptRepository.createScript(
            scriptContent = scriptExecutionStart.content,
            scriptName = scriptExecutionStart.name
        )

        return scriptExecutionRepository.createScriptExecution(
            scriptStartDate = scriptExecutionStart.startDate,
            scriptTags = scriptExecutionStart.tags,
            scriptBatchExecutionId = batchExecutionId,
            scriptExecutionOrderIndex = scriptExecutionStart.executionOrderIndex,
            executedScriptChecksum = scriptChecksum
        )
    }

    suspend fun endScriptExecution(scriptExecutionId: UUID, scriptExecutionStopRequest: ScriptExecutionStopRequest) {
        return scriptExecutionRepository.endScriptExecution(
            scriptExecutionId,
            scriptExecutionStopRequest
        )
    }
}