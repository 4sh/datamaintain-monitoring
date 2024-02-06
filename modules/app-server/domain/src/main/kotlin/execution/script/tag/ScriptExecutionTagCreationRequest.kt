package execution.script.tag

import java.util.*

data class ScriptExecutionTagCreationRequest(
    val fkScriptExecutionRef: UUID,
    val fkTagRef: String
)