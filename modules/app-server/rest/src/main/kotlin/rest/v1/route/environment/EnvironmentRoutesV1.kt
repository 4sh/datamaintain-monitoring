package rest.v1.route.environment

import environment.EnvironmentService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

private const val environmentId = "environmentId"

private fun ApplicationCall.environmentId() = UUID.fromString(this.parameters[environmentId])

internal fun Route.environmentV1Routes(environmentService: EnvironmentService) {
    route("/environments") {
        get("{$environmentId}") {
            call.respondNullable(environmentService.findOneById(call.environmentId())?.toDtoV1())
        }

        post {
            val environmentCreationRequest = call.receive<EnvironmentCreationRequestDtoV1>().toDomain()
            call.respond(environmentService.insert(environmentCreationRequest).toDtoV1())
        }

        put("{$environmentId}/name") {
            val environmentNameUpdateRequest = call.receive<EnvironmentNameUpdateRequestDtoV1>().toDomain()
            call.respondNullable(environmentService.updateEnvironmentName(call.environmentId(), environmentNameUpdateRequest)?.toDtoV1())
        }
    }
}