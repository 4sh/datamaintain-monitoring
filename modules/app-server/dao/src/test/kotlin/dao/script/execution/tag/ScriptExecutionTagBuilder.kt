package dao.script.execution.tag

import jooq.generated.domain.tables.pojos.DmScriptExecutionDmTag
import java.util.UUID

fun buildScriptExecutionTag(
    scriptExecutionRef: UUID,
    tagRef: String
) = DmScriptExecutionDmTag(
    fkScriptExecutionRef = scriptExecutionRef,
    fkTagRef = tagRef
)