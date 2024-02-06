package rest.v1.route.execution.script

import execution.Status
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rest.v1.route.execution.script.dto.toDtoV1
import execution.script.ScriptExecutionSearchRequest
import execution.script.ScriptExecutionService
import java.util.UUID

private const val scriptExecutionId = "scriptExecutionId"

private fun ApplicationCall.scriptExecutionId() = UUID.fromString(this.parameters[scriptExecutionId])
private fun ApplicationCall.status() = this.parameters["status"]?.let { Status.valueOf(it) }
private fun ApplicationCall.batchExecutionRef() = parameters["batchExecutionRef"]?.let { UUID.fromString(it) }

internal fun Route.scriptExecutionV1Routes(scriptExecutionService: ScriptExecutionService) {
    route("/scriptExecutions") {
        get {
            val scriptExecutionSearchRequest =
                ScriptExecutionSearchRequest(
                    status = call.status(),
                    batchExecutionRef = call.batchExecutionRef()
                )

            call.respond(scriptExecutionService.find(scriptExecutionSearchRequest).map { it.toDtoV1() })
        }

        get("/{$scriptExecutionId}") {
            call.respondNullable(call.scriptExecutionId()?.let { scriptExecutionService.findOneDetailById(it)?.toDtoV1() })
        }
    }
}