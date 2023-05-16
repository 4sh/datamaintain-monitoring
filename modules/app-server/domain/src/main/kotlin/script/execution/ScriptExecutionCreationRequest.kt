package script.execution

import java.time.OffsetDateTime
import java.util.*

data class ScriptExecutionCreationRequest(
    val startDate: OffsetDateTime,
    val fkScriptRef: String,
    val fkBatchExecutionRef: UUID
)