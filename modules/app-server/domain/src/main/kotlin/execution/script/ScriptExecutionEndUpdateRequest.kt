package execution.script

import execution.Status
import java.time.Instant

data class ScriptExecutionEndUpdateRequest(
    val endDate: Instant,
    val output: String,
    val status: Status,
)