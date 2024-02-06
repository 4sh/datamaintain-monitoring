package execution.script

import execution.Status
import java.time.OffsetDateTime
import java.util.*

data class ScriptExecutionCreationRequest(
    val startDate: OffsetDateTime?,
    val executionOrderIndex: Int,
    val fkScriptRef: String,
    val fkBatchExecutionRef: UUID
)