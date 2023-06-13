package script.execution

import java.time.OffsetDateTime
import java.util.*

data class ScriptExecution(
    val id: UUID,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime? = null,
    val durationInMs: Int? = null,
    val output: String? = null,
    val status: ScriptExecutionStatus? = null,
    val fkScriptRef: String,
    val fkBatchExecutionRef: UUID
)