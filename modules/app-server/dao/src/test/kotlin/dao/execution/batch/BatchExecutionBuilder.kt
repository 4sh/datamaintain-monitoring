package dao.execution.batch

import execution.Status
import execution.batch.*
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

fun buildBatchExecutionCreationRequest(
    startDate: OffsetDateTime? = null,
    origin: Origin = Origin.TIER,
    type: Type = Type.ON_DEMAND,
    fkEnvironmentRef: UUID,
    fkModuleRef: UUID
) = BatchExecutionCreationRequest(
    startDate = startDate,
    origin = origin,
    type = type,
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