package execution.batch

import execution.Status
import java.time.OffsetDateTime

data class BatchExecutionEndUpdateRequest(
    val endDate: OffsetDateTime,
    val status: Status
)