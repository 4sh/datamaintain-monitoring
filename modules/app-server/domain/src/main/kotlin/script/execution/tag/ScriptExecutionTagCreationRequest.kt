package script.execution.tag

import java.util.*

data class ScriptExecutionTagCreationRequest(
    val fkScriptExecutionRef: UUID,
    val fkTagRef: String
)