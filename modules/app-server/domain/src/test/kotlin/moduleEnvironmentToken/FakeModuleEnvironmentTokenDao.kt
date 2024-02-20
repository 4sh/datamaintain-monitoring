package moduleEnvironmentToken

import java.util.*

class FakeModuleEnvironmentTokenDao: ModuleEnvironmentTokenDaoInterface {
    private val tokens = mutableMapOf<UUID, ModuleEnvironmentToken>()

    override fun insert(creationRequest: ModuleEnvironmentTokenCreationRequest): ModuleEnvironmentToken {
        val tokenValue = UUID.randomUUID()
        val moduleEnvironmentToken = ModuleEnvironmentToken(
            fkModuleRef = creationRequest.moduleRef,
            fkEnvironmentRef = creationRequest.environmentRef,
            tokenValue = tokenValue,
        )
        tokens[tokenValue] = moduleEnvironmentToken
        return moduleEnvironmentToken
    }

    override fun getTokenByValue(tokenValue: UUID): ModuleEnvironmentToken? = tokens[tokenValue]

    override fun getTokenByModuleAndEnvironmentRef(moduleRef: UUID, environmentRef: UUID): ModuleEnvironmentToken? =
        tokens.map { it.value }.firstOrNull { it.fkModuleRef == moduleRef && it.fkEnvironmentRef == environmentRef }

    override fun deleteToken(tokenValue: UUID) {
        tokens.remove(tokenValue)
    }
}