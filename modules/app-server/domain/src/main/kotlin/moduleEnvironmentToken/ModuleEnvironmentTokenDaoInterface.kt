package moduleEnvironmentToken

import java.util.*

interface ModuleEnvironmentTokenDaoInterface {
    fun insert(creationRequest: ModuleEnvironmentTokenCreationRequest): ModuleEnvironmentToken
    fun getTokenByModuleAndEnvironmentRef(moduleRef: UUID, environmentRef: UUID): ModuleEnvironmentToken?
    fun deleteToken(tokenValue: UUID)
    fun getTokenByValue(tokenValue: UUID): ModuleEnvironmentToken?
}