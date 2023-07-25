package dao.batch.execution

import execution.Status
import execution.batch.BatchExecution
import execution.batch.BatchExecutionCreationRequest
import execution.batch.BatchExecutionEndUpdateRequest
import execution.batch.BatchExecutionStartUpdateRequest
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

fun buildBatchExecutionCreationRequest(
    startDate: OffsetDateTime? = null,
    origin: BatchExecution.Origin = BatchExecution.Origin.TIER,
    type: BatchExecution.Type = BatchExecution.Type.ON_DEMAND,
    status: Status = Status.IN_PROGRESS,
    fkEnvironmentRef: UUID,
    fkModuleRef: UUID
) = BatchExecutionCreationRequest(
    startDate = startDate,
    origin = origin,
    type = type,
    status = status,
    fkEnvironmentRef = fkEnvironmentRef, fkModuleRef = fkModuleRef
)

fun buildBatchExecutionStartUpdateRequest(
    startDate: OffsetDateTime = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
) = BatchExecutionStartUpdateRequest(
    startDate = startDate
)

fun buildBatchExecutionEndUpdateRequest(
    endDate: OffsetDateTime = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
    status: Status = Status.COMPLETED
) = BatchExecutionEndUpdateRequest(
    endDate = endDate,
    status = status
)