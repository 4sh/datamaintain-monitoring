package api.execution.report.domain.module.batch.execution

import java.time.Instant
import java.util.UUID

interface BatchExecutionRepository {
    suspend fun createBatchExecution(
        batchExecutionStartDate: Instant,
        batchExecutionModuleRef: UUID,
        batchExecutionEnvironmentRef: UUID,
    ): UUID
}