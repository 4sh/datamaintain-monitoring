package execution.batch

import execution.script.ScriptExecutionService
import java.util.*

class BatchExecutionService(
    private val batchExecutionDao: BatchExecutionDaoInterface,
    private val scriptExecutionService: ScriptExecutionService
) {
    fun insert(data: BatchExecutionCreationRequest): BatchExecution =
        batchExecutionDao.insert(data)

    fun updateBatchExecutionStartData(
        batchExecutionId: UUID,
        batchExecutionStartUpdateRequest: BatchExecutionStartUpdateRequest
    ): BatchExecution? =
        batchExecutionDao.updateBatchExecutionStartData(batchExecutionId, batchExecutionStartUpdateRequest)

    fun updateBatchExecutionEndData(
        batchExecutionId: UUID,
        batchExecutionEndUpdateRequest: BatchExecutionEndUpdateRequest
    ): BatchExecution? =
        batchExecutionDao.updateBatchExecutionEndData(batchExecutionId, batchExecutionEndUpdateRequest)

    fun delete(id: UUID) = batchExecutionDao.delete(id)

    fun findOneById(id: UUID): BatchExecutionListItem? = batchExecutionDao.findOneById(id)
    fun findOneDetailById(id: UUID): BatchExecutionDetail? {
        val batchExecution = batchExecutionDao.findOneById(id)?: return null

        return BatchExecutionDetail(
            id = batchExecution.id,
            startDate = batchExecution.startDate,
            endDate = batchExecution.endDate,
            durationInMs = batchExecution.durationInMs,
            origin = batchExecution.origin,
            status = batchExecution.status,
            type = batchExecution.type,
            project = batchExecution.project,
            module = batchExecution.module,
            environment = batchExecution.environment,
            scriptsExecutions = scriptExecutionService.findDetailByBatchExecutionId(batchExecution.id)
        )
    }

    fun find(searchRequest: BatchExecutionSearchRequest): List<BatchExecutionListItem> =
        batchExecutionDao.find(searchRequest)
}