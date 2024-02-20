package execution.script

import java.time.Instant
import java.util.*

data class ScriptExecutionCreationRequest(
    val startDate: Instant?,
    val executionOrderIndex: Int,
    val fkScriptRef: String,
    val fkBatchExecutionRef: UUID
)