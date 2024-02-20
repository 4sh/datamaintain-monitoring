package dao.execution.script

import execution.Status
import execution.script.ScriptExecutionCreationRequest
import execution.script.ScriptExecutionEndUpdateRequest
import execution.script.ScriptExecutionStartUpdateRequest
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

fun buildScriptExecutionCreationRequest(
    startDate: Instant? = null,
    executionOrderIndex: Int = 0,
    scriptRef: String,
    batchExecutionRef: UUID
) = ScriptExecutionCreationRequest(
    startDate = startDate,
    executionOrderIndex = executionOrderIndex,
    fkScriptRef = scriptRef,
    fkBatchExecutionRef = batchExecutionRef,
)

fun buildScriptExecutionStartUpdateRequest(
    startDate: Instant = OffsetDateTime.of(2023, 5, 30, 14, 26, 0, 0, ZoneOffset.UTC).toInstant()
) = ScriptExecutionStartUpdateRequest(
    startDate = startDate
)

fun buildScriptExecutionEndUpdateRequest(
    endDate: Instant = OffsetDateTime.of(2023, 5, 30, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
    output: String = "output",
    status: Status = Status.COMPLETED,
) = ScriptExecutionEndUpdateRequest(
    endDate = endDate,
    output = output,
    status = status
)