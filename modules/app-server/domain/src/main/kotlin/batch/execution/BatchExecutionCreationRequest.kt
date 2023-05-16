package batch.execution

import java.util.*

data class BatchExecutionCreationRequest(
    val fkEnvironmentRef: UUID,
    val fkModuleRef: UUID
)