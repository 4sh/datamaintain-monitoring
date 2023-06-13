package dao.script.execution

import generated.domain.enums.ScriptExecutionStatus
import generated.domain.tables.pojos.DmScriptExecution
import script.execution.ScriptExecutionCreationRequest
import script.execution.ScriptExecutionEndUpdateRequest
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

fun buildDmScriptExecution(
    id: UUID? = null,
    startDate: OffsetDateTime = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
    endDate: OffsetDateTime? = null,
    durationInMs: Int? = null,
    output: String? = null,
    status: ScriptExecutionStatus? = null,
    fkScriptRef: String,
    fkBatchExecutionRef: UUID
) = DmScriptExecution(
    id = id,
    startDate = startDate,
    endDate = endDate,
    durationInMs = durationInMs,
    output = output,
    status = status,
    fkScriptRef = fkScriptRef,
    fkBatchExecutionRef = fkBatchExecutionRef,
)

fun buildScriptExecutionCreationRequest(
    startDate: OffsetDateTime = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
    fkScriptRef: String,
    fkBatchExecutionRef: UUID
) = ScriptExecutionCreationRequest(
    startDate = startDate,
    fkScriptRef = fkScriptRef,
    fkBatchExecutionRef = fkBatchExecutionRef,
)

fun buildScriptExecutionEndUpdateRequest(
    endDate: OffsetDateTime = OffsetDateTime.of(2023, 5, 30, 14, 26, 0, 0, ZoneOffset.UTC),
    durationInMs: Int = 2000,
    output: String = "output",
    status: ScriptExecutionStatus = ScriptExecutionStatus.OK,
) = ScriptExecutionEndUpdateRequest(
    endDate = endDate,
    durationInMs = durationInMs,
    output = output,
    status = status
)