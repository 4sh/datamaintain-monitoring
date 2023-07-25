package script.execution

import java.time.OffsetDateTime

data class ScriptExecutionStartUpdateRequest(
    val startDate: OffsetDateTime
)