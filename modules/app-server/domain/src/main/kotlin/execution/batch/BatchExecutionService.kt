package execution.batch

import java.util.*

class BatchExecutionService(
    private val batchExecutionDao: BatchExecutionDaoInterface
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

    fun find(searchRequest: BatchExecutionSearchRequest): List<BatchExecutionListItem> =
        batchExecutionDao.find(searchRequest)
}