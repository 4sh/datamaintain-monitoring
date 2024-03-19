import moduleEnvironmentToken.ModuleEnvironmentTokenService
import proto.ModuleEnvironmentTokenApi
import proto.ModuleEnvironmentTokenServiceGrpcKt
import proto.moduleEnvironmentTokenSearchByTokenResponse
import java.util.*

class ModuleEnvironmentTokenGrpcServiceImpl(private val moduleEnvironmentTokenService: ModuleEnvironmentTokenService) :
    ModuleEnvironmentTokenServiceGrpcKt.ModuleEnvironmentTokenServiceCoroutineImplBase() {
    override suspend fun findModuleEnvironmentTokenByTokenValue(request: ModuleEnvironmentTokenApi.ModuleEnvironmentTokenSearchByTokenRequest): ModuleEnvironmentTokenApi.ModuleEnvironmentTokenSearchByTokenResponse {
        val token = moduleEnvironmentTokenService.getTokenByValueSafe(UUID.fromString(request.token))
        return moduleEnvironmentTokenSearchByTokenResponse {
            moduleRef = token.fkModuleRef.toString()
            environmentRef = token.fkEnvironmentRef.toString()
        }
    }
}