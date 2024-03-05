package api.execution.report.rest.v1.route

import api.execution.report.domain.module.batch.execution.BatchExecutionService
import datamaintain.monitoring.api.execution.report.api.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Route.executionsV1Routes(batchExecutionService: BatchExecutionService) {
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
                val executionId = call.parameters["executionId"]!!.toInt()
                println("Execution with id $executionId has ended, it created report ${MonitoringReport(executionId)}}")
            }
        }
        post("/{executionId}/scripts/start") {
            val scriptExecutionStart = call.receive<ScriptExecutionStart>()
            val executionId = call.parameters["executionId"]!!.toInt()
            call.respond(HttpStatusCode.OK).also {
                println("Start script execution $scriptExecutionStart for batch $executionId")
            }
        }
        post("/{executionId}/scripts/stop") {
            val scriptExecutionStop = call.receive<ScriptExecutionStop>()
            val executionId = call.parameters["executionId"]!!.toInt()
            call.respond(HttpStatusCode.OK).also {
                println("Start script execution $scriptExecutionStop for batch $executionId")
            }
        }
    }
}