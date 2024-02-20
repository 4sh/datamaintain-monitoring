package execution.batch

import execution.Status
import java.time.Instant

data class BatchExecutionEndUpdateRequest(
    val endDate: Instant,
    val status: Status
)