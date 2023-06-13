package script.execution

import generated.domain.enums.ScriptExecutionStatus
import java.time.OffsetDateTime

data class ScriptExecutionEndUpdateRequest(
    val endDate: OffsetDateTime,
    val durationInMs: Int,
    val output: String,
    val status: ScriptExecutionStatus,
)