package dao.batch.execution

import execution.batch.BatchExecutionCreationRequest
import java.util.*

fun buildBatchExecutionCreationRequest(
    fkEnvironmentRef: UUID,
    fkModuleRef: UUID
) = BatchExecutionCreationRequest(
    fkEnvironmentRef = fkEnvironmentRef, fkModuleRef = fkModuleRef
)