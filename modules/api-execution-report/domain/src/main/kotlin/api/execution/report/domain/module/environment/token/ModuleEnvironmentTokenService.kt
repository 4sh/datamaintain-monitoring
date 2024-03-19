package api.execution.report.domain.module.environment.token

class ModuleEnvironmentTokenService(private val repository: ModuleEnvironmentTokenRepository) {
    suspend fun findModuleEnvironmentTokenByTokenValue(
        tokenValue: String
    ): ModuleEnvironmentToken = repository.findModuleEnvironmentTokenByTokenValue(tokenValue)
}