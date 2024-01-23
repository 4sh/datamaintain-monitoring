package script.execution

import execution.Status
import java.util.UUID

data class ScriptExecutionSearchRequest(
    val status: Status? = null,
    val batchExecutionRef: UUID? = null
)