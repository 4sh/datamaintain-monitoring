package script.execution.tag

import java.util.*

data class ScriptExecutionTag(val fkScriptExecutionRef: UUID,
                              val fkTagRef: String)