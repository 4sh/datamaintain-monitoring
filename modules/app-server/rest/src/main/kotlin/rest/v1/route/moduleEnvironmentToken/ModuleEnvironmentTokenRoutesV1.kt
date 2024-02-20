package rest.v1.route.moduleEnvironmentToken

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import moduleEnvironmentToken.ModuleEnvironmentTokenService
import rest.v1.route.moduleEnvironmentToken.dto.ModuleEnvironmentTokenCreationRequestDtoV1
import rest.v1.route.moduleEnvironmentToken.dto.toDtoV1
import java.util.*

private const val tokenValue = "tokenValue"
private const val environmentRef = "environmentRef"
private const val moduleRef = "moduleRef"
private fun ApplicationCall.tokenValue() = UUID.fromString(this.parameters[tokenValue])
private fun ApplicationCall.environmentRef() = UUID.fromString(this.parameters[environmentRef])
private fun ApplicationCall.moduleRef() = UUID.fromString(this.parameters[moduleRef])
internal fun Route.moduleEnvironmentTokenV1Routes(moduleEnvironmentTokenService: ModuleEnvironmentTokenService) {
    route("/moduleEnvironmentTokens") {
        get("byModuleAndEnvironmentRef/{$moduleRef}/{$environmentRef}") {
            call.respondNullable(
                moduleEnvironmentTokenService.getTokenByModuleAndEnvironmentRef(
                    moduleRef = call.moduleRef(),
                    environmentRef = call.environmentRef()
                )?.toDtoV1()
            )
        }

        get("{$tokenValue}") {
            call.respondNullable(
                moduleEnvironmentTokenService.getTokenByValue(tokenValue = call.tokenValue())?.toDtoV1()
            )
        }

        post("regenerateToken") {
            call.respondNullable(
                moduleEnvironmentTokenService.regenerateToken(
                    creationRequest = call.receive<ModuleEnvironmentTokenCreationRequestDtoV1>().toDomain()
                ).toDtoV1()
            )
        }
    }
}