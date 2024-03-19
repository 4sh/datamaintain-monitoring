package api.execution.report.client

import api.execution.report.domain.module.environment.token.ModuleEnvironmentToken
import api.execution.report.domain.module.environment.token.ModuleEnvironmentTokenRepository
import io.grpc.ManagedChannel
import proto.*
import java.util.*

class ModuleEnvironmentTokenGrpcClient(channel: ManagedChannel) : AbstractGrpcClient(channel),
    ModuleEnvironmentTokenRepository {
    private val stub = ModuleEnvironmentTokenServiceGrpcKt.ModuleEnvironmentTokenServiceCoroutineStub(channel)

    override suspend fun findModuleEnvironmentTokenByTokenValue(
        tokenValue: String
    ): ModuleEnvironmentToken {
        return stub.findModuleEnvironmentTokenByTokenValue(moduleEnvironmentTokenSearchByTokenRequest {
            token = tokenValue
        }).toDomain()
    }
}

fun ModuleEnvironmentTokenApi.ModuleEnvironmentTokenSearchByTokenResponse.toDomain() = ModuleEnvironmentToken(
    moduleRef = UUID.fromString(moduleRef),
    environmentRef = UUID.fromString(environmentRef)
)