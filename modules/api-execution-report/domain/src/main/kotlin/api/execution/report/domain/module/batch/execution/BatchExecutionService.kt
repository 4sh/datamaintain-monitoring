package api.execution.report.domain.module.batch.execution

import api.execution.report.domain.module.environment.token.ModuleEnvironmentTokenService
import java.time.Instant
import java.util.*

class BatchExecutionService(
    private val moduleEnvironmentTokenService: ModuleEnvironmentTokenService,
    private val batchExecutionRepository: BatchExecutionRepository
) {
    suspend fun createBatchExecution(
        moduleEnvironmentTokenValue: String,
        startDate: Instant
    ): UUID {
        val moduleEnvironmentToken =
            moduleEnvironmentTokenService.findModuleEnvironmentTokenByTokenValue(moduleEnvironmentTokenValue)
        return batchExecutionRepository.createBatchExecution(
            batchExecutionStartDate = startDate,
            batchExecutionModuleRef = moduleEnvironmentToken.moduleRef,
            batchExecutionEnvironmentRef = moduleEnvironmentToken.environmentRef
        )
    }
}