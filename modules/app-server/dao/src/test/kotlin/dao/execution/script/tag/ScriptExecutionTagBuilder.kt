package dao.execution.script.tag

import execution.script.tag.ScriptExecutionTagCreationRequest
import java.util.*

fun buildScriptExecutionTagCreationRequest(
    scriptExecutionRef: UUID,
    tagRef: String
) = ScriptExecutionTagCreationRequest(
    fkScriptExecutionRef = scriptExecutionRef,
    fkTagRef = tagRef
)