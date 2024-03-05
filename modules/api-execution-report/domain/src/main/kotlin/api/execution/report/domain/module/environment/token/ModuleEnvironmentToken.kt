package api.execution.report.domain.module.environment.token

import java.util.UUID

data class ModuleEnvironmentToken(val moduleRef: UUID, val environmentRef: UUID)