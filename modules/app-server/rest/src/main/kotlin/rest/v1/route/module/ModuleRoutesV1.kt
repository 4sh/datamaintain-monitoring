package rest.v1.route.module

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import module.ModuleService
import rest.v1.route.module.dto.ModuleCreationRequestDtoV1
import rest.v1.route.module.dto.ModuleNameUpdateRequestDtoV1
import rest.v1.route.module.dto.toDomain
import rest.v1.route.module.dto.toDtoV1
import java.util.*

internal const val moduleId = "moduleId"

internal fun ApplicationCall.moduleId() = UUID.fromString(this.parameters[moduleId])

internal fun Route.moduleV1Routes(moduleService: ModuleService) {
    route("/modules") {
        get("{$moduleId}") {
            call.respondNullable(moduleService.findOneById(call.moduleId())?.toDtoV1())
        }

        post {
            val moduleCreationRequest = call.receive<ModuleCreationRequestDtoV1>().toDomain()
            call.respond(moduleService.insert(moduleCreationRequest).toDtoV1())
        }

        put("{$moduleId}/name") {
            val moduleNameUpdateRequest = call.receive<ModuleNameUpdateRequestDtoV1>().toDomain()
            call.respondNullable(moduleService.updateModuleName(call.moduleId(), moduleNameUpdateRequest)?.toDtoV1())
        }
    }
}