package execution.batch

import java.time.OffsetDateTime
import java.util.*

data class BatchExecutionCreationRequest(
    val startDate: OffsetDateTime?,
    val origin: Origin,
    val type: Type,
    val fkEnvironmentRef: UUID,
    val fkModuleRef: UUID
)