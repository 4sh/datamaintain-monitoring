package api.execution.report.domain.module.script.execution

import java.time.Instant
import java.util.UUID

interface ScriptExecutionRepository {
    suspend fun createScriptExecution(
        scriptStartDate: Instant,
        scriptExecutionOrderIndex: Int,
        scriptTags: List<String>,
        scriptBatchExecutionId: UUID,
        executedScriptChecksum: String
    ): UUID
}