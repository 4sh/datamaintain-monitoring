package execution.batch

import environment.Environment
import execution.Status
import execution.script.ScriptExecutionDetail
import module.Module
import project.Project
import java.time.OffsetDateTime
import java.util.*

data class BatchExecutionDetail(
    val id: UUID,
    val startDate: OffsetDateTime? = null,
    val endDate: OffsetDateTime? = null,
    val durationInMs: Int? = null,
    val origin: Origin,
    val status: Status,
    val type: Type,
    val project: Project,
    val module: Module,
    val environment: Environment,
    val scriptsExecutions: List<ScriptExecutionDetail>
)