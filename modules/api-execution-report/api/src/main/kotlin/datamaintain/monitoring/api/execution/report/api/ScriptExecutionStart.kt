package datamaintain.monitoring.api.execution.report.api

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ScriptExecutionStart(
    val name: String,
    val checksum: String,
    val content: String,
    @Serializable(with = InstantSerializer::class)
    val startDate: Instant,
    val tags: List<String>
)