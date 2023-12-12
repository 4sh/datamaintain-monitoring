package rest.v1.route.script.execution

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import script.execution.ScriptExecutionService
import java.util.UUID

internal const val scriptExecutionId = "scriptExecutionId"

internal fun ApplicationCall.scriptExecutionId() = UUID.fromString(this.parameters[scriptExecutionId])

internal fun Route.scriptExecutionV1Routes(scriptExecutionService: ScriptExecutionService) {
    get("/scriptExecutions/{$scriptExecutionId}") {
        call.respondNullable(call.scriptExecutionId()?.let { scriptExecutionService.findOneById(it)?.toDtoV1() })
    }
}