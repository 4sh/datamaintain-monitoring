package api.execution.report.rest.v1.route

import api.execution.report.domain.module.batch.execution.BatchExecutionService
import api.execution.report.domain.module.script.execution.ExecutionStatus
import api.execution.report.domain.module.script.execution.ScriptExecutionService
import datamaintain.monitoring.api.execution.report.api.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import java.util.*

internal fun Route.executionsV1Routes(batchExecutionService: BatchExecutionService, scriptExecutionService: ScriptExecutionService) {
    route("/executions") {
        post("/start") {
            val executionStartRequest = call.receive<ExecutionStart>()
            call.respond(
                ExecutionStartResponse(
                    batchExecutionService.createBatchExecution(
                        moduleEnvironmentTokenValue = executionStartRequest.moduleEnvironmentToken,
                        startDate = executionStartRequest.startDate,
                    )
                )
            )
        }
        put("/stop/{executionId}") {
            call.respond(HttpStatusCode.OK).also {
                val executionId = UUID.fromString(call.parameters.getOrFail("executionId"))
                val request = call.receive<ExecutionStopRequest>()
                batchExecutionService.stopBatchExecution(
                    executionId,
                    request.endDate,
                    request.batchEndStatus.toDomain()
                )
            }
        }
        post("/{executionId}/scripts/start") {
            val scriptExecutionStart = call.receive<ScriptExecutionStart>()
            val executionId = UUID.fromString(call.parameters.getOrFail("executionId"))
            val scriptExecutionId = scriptExecutionService.createScriptExecution(
                scriptExecutionStart = scriptExecutionStart.toDomain(),
                batchExecutionId = executionId
            )
            call.respond(ScriptExecutionStartResponse(
                scriptExecutionId
            ))
        }
        put("/scripts/{scriptExecutionId}/stop") {
            val scriptExecutionStop = call.receive<ScriptExecutionStop>()
            val scriptExecutionId = UUID.fromString(call.parameters.getOrFail("scriptExecutionId"))
            scriptExecutionService.endScriptExecution(
                scriptExecutionId = scriptExecutionId,
                scriptExecutionStopRequest = scriptExecutionStop.toDomain()
            )
            call.respond(HttpStatusCode.OK)
        }
    }
}

private fun ScriptExecutionStop.toDomain(): api.execution.report.domain.module.script.execution.ScriptExecutionStopRequest =
    api.execution.report.domain.module.script.execution.ScriptExecutionStopRequest(
        executionStatus = executionStatus.toDomain(),
        executionOutput = executionOutput,
        executionEndDate = executionEndDate
    )

private fun datamaintain.monitoring.api.execution.report.api.ExecutionStatus.toDomain(): ExecutionStatus =
    when (this) {
        datamaintain.monitoring.api.execution.report.api.ExecutionStatus.OK -> ExecutionStatus.OK
        datamaintain.monitoring.api.execution.report.api.ExecutionStatus.KO -> ExecutionStatus.KO
    }

private fun ScriptExecutionStart.toDomain(): api.execution.report.domain.module.script.execution.ScriptExecutionStart =
    api.execution.report.domain.module.script.execution.ScriptExecutionStart(
        name = name,
        content = content,
        executionOrderIndex = executionOrderIndex,
        startDate = startDate,
        tags = tags
    )

private fun BatchEndStatus.toDomain(): api.execution.report.domain.module.batch.execution.BatchEndStatus =
    when(this) {
        BatchEndStatus.COMPLETED -> api.execution.report.domain.module.batch.execution.BatchEndStatus.COMPLETED
        BatchEndStatus.ERROR -> api.execution.report.domain.module.batch.execution.BatchEndStatus.ERROR
    }