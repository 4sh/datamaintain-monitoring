package execution.batch

import java.util.UUID

interface BatchExecutionDaoInterface {
    fun insert(data: BatchExecutionCreationRequest): BatchExecution
    fun updateBatchExecutionStartData(
        batchExecutionId: UUID,
        batchExecutionStartUpdateRequest: BatchExecutionStartUpdateRequest
    ): BatchExecution?
    fun updateBatchExecutionEndData(
        batchExecutionId: UUID,
        batchExecutionEndUpdateRequest: BatchExecutionEndUpdateRequest
    ): BatchExecution?
    fun delete(id: UUID)
    fun findOneById(id: UUID): BatchExecution?
}