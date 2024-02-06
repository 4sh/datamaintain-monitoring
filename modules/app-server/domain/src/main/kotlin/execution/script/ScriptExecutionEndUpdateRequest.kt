package execution.script

import execution.Status
import java.time.OffsetDateTime

data class ScriptExecutionEndUpdateRequest(
    val endDate: OffsetDateTime,
    val output: String,
    val status: Status,
)