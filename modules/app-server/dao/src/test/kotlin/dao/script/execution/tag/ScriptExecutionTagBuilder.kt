package dao.script.execution.tag

import generated.domain.tables.pojos.DmScriptExecutionDmTag
import script.execution.tag.ScriptExecutionTagCreationRequest
import java.util.UUID

fun buildScriptExecutionTag(
    scriptExecutionRef: UUID,
    tagRef: String
) = DmScriptExecutionDmTag(
    fkScriptExecutionRef = scriptExecutionRef,
    fkTagRef = tagRef
)

fun buildScriptExecutionTagCreationRequest(
    scriptExecutionRef: UUID,
    tagRef: String
) = ScriptExecutionTagCreationRequest(
    fkScriptExecutionRef = scriptExecutionRef,
    fkTagRef = tagRef
)