package batch.execution

import java.util.*

data class BatchExecution(
    val id: UUID,
    val fkEnvironmentRef: UUID,
    val fkModuleRef: UUID
)