package moduleEnvironmentToken

import java.util.UUID

data class ModuleEnvironmentToken(val fkModuleRef: UUID, val fkEnvironmentRef: UUID, val tokenValue: UUID)