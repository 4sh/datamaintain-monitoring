package execution.script

import java.time.OffsetDateTime

data class ScriptExecutionStartUpdateRequest(
    val startDate: OffsetDateTime
)