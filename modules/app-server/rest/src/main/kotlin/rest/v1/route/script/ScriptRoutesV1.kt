package rest.v1.route.script

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rest.v1.route.script.dto.toDtoV1
import script.ScriptService

internal const val scriptId = "scriptId"

internal fun ApplicationCall.scriptId() = this.parameters[scriptId]

internal fun Route.scriptV1Routes(scriptService: ScriptService) {
    get("/scripts/{$scriptId}") {
        call.respondNullable(call.scriptId()?.let { scriptService.findOneByChecksum(it) }?.toDtoV1())
    }
}