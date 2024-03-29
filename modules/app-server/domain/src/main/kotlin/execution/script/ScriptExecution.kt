package execution.script

import execution.Status
import java.time.OffsetDateTime
import java.util.*

data class ScriptExecution(
    val id: UUID,
    val startDate: OffsetDateTime? = null,
    val endDate: OffsetDateTime? = null,
    val durationInMs: Int? = null,
    val executionOrderIndex: Int,
    val output: String? = null,
    val status: Status? = null,
    val fkScriptRef: String,
    val fkBatchExecutionRef: UUID
)