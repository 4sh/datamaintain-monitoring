package api.execution.report.domain.module.environment.token

interface ModuleEnvironmentTokenRepository {
    suspend fun findModuleEnvironmentTokenByTokenValue(
        tokenValue: String
    ): ModuleEnvironmentToken
}