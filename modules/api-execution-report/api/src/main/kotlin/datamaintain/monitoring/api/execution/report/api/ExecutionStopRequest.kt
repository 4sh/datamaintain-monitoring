package datamaintain.monitoring.api.execution.report.api

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ExecutionStopRequest(
    @Serializable(with = InstantSerializer::class)
    val endDate: Instant,
    val batchEndStatus: BatchEndStatus
)

enum class BatchEndStatus {
    COMPLETED,
    ERROR
}