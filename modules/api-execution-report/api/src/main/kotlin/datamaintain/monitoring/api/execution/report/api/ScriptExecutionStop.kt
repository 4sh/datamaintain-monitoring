package datamaintain.monitoring.api.execution.report.api

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ScriptExecutionStop(
    val executionStatus: ExecutionStatus,
    val executionOutput: String?,
    @Serializable(with = InstantSerializer::class)
    val executionEndDate: Instant
)

@Serializable
enum class ExecutionStatus {
    OK,
    KO
}