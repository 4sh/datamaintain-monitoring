package dao.batch.execution

import batch.execution.BatchExecutionCreationRequest
import java.util.*

fun buildBatchExecutionCreationRequest(
    fkEnvironmentRef: UUID,
    fkModuleRef: UUID
) = BatchExecutionCreationRequest(
    fkEnvironmentRef = fkEnvironmentRef, fkModuleRef = fkModuleRef
)