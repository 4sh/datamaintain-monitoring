package rest.v1.route.execution.script

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rest.v1.route.execution.script.dto.toDtoV1
import execution.script.ScriptExecutionSearchRequest
import execution.script.ScriptExecutionService
import rest.v1.route.execution.script.dto.toScriptEnvMatrixDtoV1
import rest.v1.route.execution.status
import java.util.UUID

private const val scriptExecutionId = "scriptExecutionId"
private const val moduleRef = "moduleRef"
private const val projectRef = "projectRef"

private fun ApplicationCall.scriptExecutionId() = UUID.fromString(this.parameters[scriptExecutionId])
private fun ApplicationCall.moduleRef() = UUID.fromString(this.parameters[moduleRef])
private fun ApplicationCall.projectRef() = UUID.fromString(this.parameters[projectRef])
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

        get("/projects/{$projectRef}/modules/{$moduleRef}") {
            call.respondNullable(scriptExecutionService.findModuleScriptsExecutionsInformation(
                call.projectRef(),
                call.moduleRef()
            ).toScriptEnvMatrixDtoV1())
        }
    }
}