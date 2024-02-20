package execution.batch

import java.time.Instant

data class BatchExecutionStartUpdateRequest(
    val startDate: Instant,
)