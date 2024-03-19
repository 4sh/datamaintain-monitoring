package datamaintain.monitoring.api.execution.report.api

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ExecutionStart(
    @Serializable(with = InstantSerializer::class)
    val startDate: Instant,
    val moduleEnvironmentToken: String
)