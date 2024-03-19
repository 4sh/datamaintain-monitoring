package datamaintain.monitoring.api.execution.report.api

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ScriptExecutionStart(
    val name: String,
    val content: String,
    val executionOrderIndex: Int,
    @Serializable(with = InstantSerializer::class)
    val startDate: Instant,
    val tags: List<String>
)