package script.execution

import execution.Status
import script.ScriptListItem
import java.time.OffsetDateTime
import java.util.*

data class ScriptExecutionListItem(
    val id: UUID,
    val startDate: OffsetDateTime? = null,
    val endDate: OffsetDateTime? = null,
    val durationInMs: Int? = null,
    val executionOrderIndex: Int,
    val status: Status? = null,
    val script: ScriptListItem
)