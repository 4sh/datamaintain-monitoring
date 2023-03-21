package datamaintain.monitoring.api.execution.report.api

data class ScriptExecutionStop(
    val checksum: String,
    val executionDurationInMillis: Long?,
    val executionStatus: ExecutionStatus,
    val executionOutput: String?
)

enum class ExecutionStatus {
    OK,
    KO
}