package moduleEnvironmentToken

import java.util.UUID

data class ModuleEnvironmentTokenCreationRequest(val moduleRef: UUID, val environmentRef: UUID)