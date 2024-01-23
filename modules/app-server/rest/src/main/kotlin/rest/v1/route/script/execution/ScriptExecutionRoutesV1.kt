package rest.v1.route.script.execution

import execution.Status
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rest.v1.route.script.execution.dto.toDtoV1
import script.execution.ScriptExecutionSearchRequest
import script.execution.ScriptExecutionService
import java.util.UUID

internal const val scriptExecutionId = "scriptExecutionId"

internal fun ApplicationCall.scriptExecutionId() = UUID.fromString(this.parameters[scriptExecutionId])

internal fun Route.scriptExecutionV1Routes(scriptExecutionService: ScriptExecutionService) {
    route("/scriptExecutions") {
        get {
            val scriptExecutionSearchRequest =
                ScriptExecutionSearchRequest(
                    status = call.parameters["status"]?.let { Status.valueOf(it) },
                    batchExecutionRef = call.parameters["batchExecutionRef"]?.let { UUID.fromString(it) }
                )

            call.respond(scriptExecutionService.find(scriptExecutionSearchRequest).map { it.toDtoV1() })
        }

        get("/{$scriptExecutionId}") {
            call.respondNullable(call.scriptExecutionId()?.let { scriptExecutionService.findOneDetailById(it)?.toDtoV1() })
        }
    }
}