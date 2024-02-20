package execution.script

import java.time.Instant

data class ScriptExecutionStartUpdateRequest(
    val startDate: Instant
)