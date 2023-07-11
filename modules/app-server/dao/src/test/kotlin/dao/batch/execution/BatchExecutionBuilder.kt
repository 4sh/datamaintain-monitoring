package dao.batch.execution

import execution.Status
import execution.batch.BatchExecution
import execution.batch.BatchExecutionCreationRequest
import java.time.OffsetDateTime
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