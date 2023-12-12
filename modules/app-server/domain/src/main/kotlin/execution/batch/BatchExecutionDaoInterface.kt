package execution.batch

import execution.batch.BatchExecution
import execution.batch.BatchExecutionCreationRequest
import execution.batch.BatchExecutionEndUpdateRequest
import execution.batch.BatchExecutionStartUpdateRequest
import java.util.*

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