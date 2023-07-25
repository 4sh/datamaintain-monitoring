package dao.script.execution

import execution.Status
import script.execution.ScriptExecutionCreationRequest
import script.execution.ScriptExecutionEndUpdateRequest
import script.execution.ScriptExecutionStartUpdateRequest
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

fun buildScriptExecutionCreationRequest(
    startDate: OffsetDateTime? = null,
    executionOrderIndex: Int = 0,
    status: Status = Status.PLANNED,
    scriptRef: String,
    batchExecutionRef: UUID
) = ScriptExecutionCreationRequest(
    startDate = startDate,
    executionOrderIndex = executionOrderIndex,
    status = status,
    fkScriptRef = scriptRef,
    fkBatchExecutionRef = batchExecutionRef,
)

fun buildScriptExecutionStartUpdateRequest(
    startDate: OffsetDateTime = OffsetDateTime.of(2023, 5, 30, 14, 26, 0, 0, ZoneOffset.UTC)
) = ScriptExecutionStartUpdateRequest(
    startDate = startDate
)

fun buildScriptExecutionEndUpdateRequest(
    endDate: OffsetDateTime = OffsetDateTime.of(2023, 5, 30, 14, 26, 0, 0, ZoneOffset.UTC),
    durationInMs: Int = 2000,
    output: String = "output",
    status: Status = Status.COMPLETED,
) = ScriptExecutionEndUpdateRequest(
    endDate = endDate,
    durationInMs = durationInMs,
    output = output,
    status = status
)