package execution.batch

import execution.Status
import java.time.OffsetDateTime
import java.util.*

data class BatchExecutionCreationRequest(
    val startDate: OffsetDateTime,
    val origin: BatchExecution.Origin,
    val type: BatchExecution.Type,
    val status: Status,
    val fkEnvironmentRef: UUID,
    val fkModuleRef: UUID
)