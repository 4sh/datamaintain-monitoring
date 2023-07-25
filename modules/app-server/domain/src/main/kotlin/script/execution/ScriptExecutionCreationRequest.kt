package script.execution

import execution.Status
import java.time.OffsetDateTime
import java.util.*

data class ScriptExecutionCreationRequest(
    val startDate: OffsetDateTime?,
    val status: Status,
    val fkScriptRef: String,
    val fkBatchExecutionRef: UUID
)