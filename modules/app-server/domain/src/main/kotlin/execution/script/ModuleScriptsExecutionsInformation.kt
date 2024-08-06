package execution.script

import environment.Environment
import execution.Status
import script.Script
import java.time.OffsetDateTime
import java.util.*

data class ModuleScriptsExecutionsInformation(
    val scriptsWithAllExecutions: List<ScriptWithAllExecutions>,
    val environments: List<Environment>
)

data class ScriptExecutionWithEnvironment(
    val id: UUID,
    val startDate: OffsetDateTime?,
    val durationInMs: Int?,
    val executionOrderIndex: Int,
    val output: String?,
    val status: Status?,
    val fkEnvironmentRef: UUID
)

data class ScriptWithAllExecutions(
    val script: Script,
    val executions: List<ScriptExecutionWithEnvironment>
)