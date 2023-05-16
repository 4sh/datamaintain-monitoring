package dao.batch.execution

import batch.execution.BatchExecutionCreationRequest
import generated.domain.tables.pojos.DmBatchExecution
import java.util.*

fun buildDmBatchExecution(
    id: UUID? = null,
    fkEnvironmentRef: UUID,
    fkModuleRef: UUID
) = DmBatchExecution(
    id = id, fkEnvironmentRef = fkEnvironmentRef, fkModuleRef = fkModuleRef
)

fun buildBatchExecutionCreationRequest(
    fkEnvironmentRef: UUID,
    fkModuleRef: UUID
) = BatchExecutionCreationRequest(
    fkEnvironmentRef = fkEnvironmentRef, fkModuleRef = fkModuleRef
)