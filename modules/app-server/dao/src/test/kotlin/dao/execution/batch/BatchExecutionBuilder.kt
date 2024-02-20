package dao.execution.batch

import execution.Status
import execution.batch.*
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

fun buildBatchExecutionCreationRequest(
    startDate: Instant? = null,
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
    startDate: Instant = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
) = BatchExecutionStartUpdateRequest(
    startDate = startDate
)

fun buildBatchExecutionEndUpdateRequest(
    endDate: Instant = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
    status: Status = Status.COMPLETED
) = BatchExecutionEndUpdateRequest(
    endDate = endDate,
    status = status
)