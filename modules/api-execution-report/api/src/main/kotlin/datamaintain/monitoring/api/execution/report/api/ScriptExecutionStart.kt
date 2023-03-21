package datamaintain.monitoring.api.execution.report.api

import java.time.Instant

data class ScriptExecutionStart(
    val name: String,
    val checksum: String,
    val content: String,
    val startDate: Instant,
    val tags: List<String>
)