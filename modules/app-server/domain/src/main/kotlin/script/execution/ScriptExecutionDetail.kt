package script.execution

import execution.Status
import script.Script
import java.time.OffsetDateTime
import java.util.*

data class ScriptExecutionDetail(
    val id: UUID,
    val startDate: OffsetDateTime? = null,
    val endDate: OffsetDateTime? = null,
    val durationInMs: Int? = null,
    val executionOrderIndex: Int,
    val output: String? = null,
    val status: Status? = null,
    val script: Script,
    val fkBatchExecutionRef: UUID
) {
    constructor(
        scriptExecution: ScriptExecution,
        script: Script
    ): this(
        scriptExecution.id,
        scriptExecution.startDate,
        scriptExecution.endDate,
        scriptExecution.durationInMs,
        scriptExecution.executionOrderIndex,
        scriptExecution.output,
        scriptExecution.status,
        script,
        scriptExecution.fkBatchExecutionRef
    )
}