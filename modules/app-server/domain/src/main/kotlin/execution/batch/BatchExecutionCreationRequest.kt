package execution.batch

import java.time.Instant
import java.util.*

data class BatchExecutionCreationRequest(
    val startDate: Instant?,
    val origin: Origin,
    val type: Type,
    val fkEnvironmentRef: UUID,
    val fkModuleRef: UUID
)