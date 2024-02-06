package execution.batch

import execution.Status
import java.util.UUID

data class BatchExecutionSearchRequest(
    val status: Status? = null,
    val projectRef: UUID? = null,
    val moduleRef: UUID? = null,
    val environmentRef: UUID? = null
)