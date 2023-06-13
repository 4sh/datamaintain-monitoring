package datamaintain.monitoring.api.execution.report.api

import kotlinx.serialization.Serializable

@Serializable
data class ScriptExecutionStop(
    val checksum: String,
    val executionDurationInMillis: Long?,
    val executionStatus: ExecutionStatus,
    val executionOutput: String?
)

@Serializable
enum class ExecutionStatus {
    OK,
    KO
}