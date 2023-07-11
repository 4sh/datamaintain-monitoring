package script.execution

import execution.Status
import java.time.OffsetDateTime

data class ScriptExecutionEndUpdateRequest(
    val endDate: OffsetDateTime,
    val durationInMs: Int,
    val output: String,
    val status: Status,
)