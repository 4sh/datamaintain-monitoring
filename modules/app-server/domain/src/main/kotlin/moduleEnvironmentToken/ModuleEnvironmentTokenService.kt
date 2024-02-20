package moduleEnvironmentToken

import java.util.UUID

class ModuleEnvironmentTokenService(val dao: ModuleEnvironmentTokenDaoInterface) {
    fun regenerateToken(creationRequest: ModuleEnvironmentTokenCreationRequest): ModuleEnvironmentToken {
        // There can only be one token by module and environment
        val preexistingTokenValue = getTokenByModuleAndEnvironmentRef(
            moduleRef = creationRequest.moduleRef,
            environmentRef = creationRequest.environmentRef
        )?.tokenValue

        if (preexistingTokenValue != null) {
            deleteToken(preexistingTokenValue)
        }

        return dao.insert(creationRequest)
    }

    fun getTokenByModuleAndEnvironmentRef(moduleRef: UUID, environmentRef: UUID): ModuleEnvironmentToken? {
        return dao.getTokenByModuleAndEnvironmentRef(moduleRef, environmentRef)
    }

    fun getTokenByValue(tokenValue: UUID): ModuleEnvironmentToken? {
        return dao.getTokenByValue(tokenValue)
    }

    private fun deleteToken(tokenValue: UUID) {
        dao.deleteToken(tokenValue)
    }
}