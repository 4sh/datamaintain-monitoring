package api.execution.report.domain.module.script.execution

import java.time.Instant

data class ScriptExecutionStopRequest(
    val executionStatus: ExecutionStatus,
    val executionOutput: String?,
    val executionEndDate: Instant
)

enum class ExecutionStatus {
    OK,
    KO
}