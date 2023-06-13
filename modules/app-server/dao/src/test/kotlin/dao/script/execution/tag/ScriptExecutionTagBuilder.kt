package dao.script.execution.tag

import script.execution.tag.ScriptExecutionTagCreationRequest
import java.util.*

fun buildScriptExecutionTagCreationRequest(
    scriptExecutionRef: UUID,
    tagRef: String
) = ScriptExecutionTagCreationRequest(
    fkScriptExecutionRef = scriptExecutionRef,
    fkTagRef = tagRef
)