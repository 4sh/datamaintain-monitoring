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

    internal fun getTokenByValue(tokenValue: UUID): ModuleEnvironmentToken? {
        return dao.getTokenByValue(tokenValue)
    }

    fun getTokenByValueSafe(tokenValue: UUID): ModuleEnvironmentToken {
        return getTokenByValue(tokenValue)?: throw ModuleEnvironmentTokenNotFoundByValueException(tokenValue)
    }

    private fun deleteToken(tokenValue: UUID) {
        dao.deleteToken(tokenValue)
    }
}

class ModuleEnvironmentTokenNotFoundByValueException(tokenValue: UUID):
    IllegalArgumentException("No token was found with the value $tokenValue")