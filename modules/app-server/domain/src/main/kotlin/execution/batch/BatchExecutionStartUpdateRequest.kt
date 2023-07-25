package execution.batch

import java.time.OffsetDateTime

data class BatchExecutionStartUpdateRequest(
    val startDate: OffsetDateTime,
)