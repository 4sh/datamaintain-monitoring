package api.execution.report.domain.module.script.execution

import java.time.Instant

data class ScriptExecutionStart(
    val name: String,
    val content: String,
    val executionOrderIndex: Int,
    val startDate: Instant,
    val tags: List<String>
)