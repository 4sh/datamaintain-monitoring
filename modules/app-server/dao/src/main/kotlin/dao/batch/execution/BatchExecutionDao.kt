package dao.batch.execution

import dao.utils.toDto
import execution.batch.BatchExecution
import execution.batch.BatchExecutionCreationRequest
import execution.batch.BatchExecutionEndUpdateRequest
import execution.batch.BatchExecutionStartUpdateRequest
import generated.domain.enums.ExecutionStatus
import generated.domain.tables.references.DM_BATCH_EXECUTION
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import java.util.*

class BatchExecutionDao(private val dslContext: DSLContext) {
    fun insert(data: BatchExecutionCreationRequest): BatchExecution =
        dslContext.insertInto(
            DM_BATCH_EXECUTION,
            DM_BATCH_EXECUTION.START_DATE,
            DM_BATCH_EXECUTION.ORIGIN,
            DM_BATCH_EXECUTION.TYPE,
            DM_BATCH_EXECUTION.STATUS,
            DM_BATCH_EXECUTION.FK_MODULE_REF,
            DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF
        )
            .values(
                `val`(data.startDate),
                `val`(data.origin.toDto()),
                `val`(data.type.toDto()),
                `val`(data.status.toDto()),
                `val`(data.fkModuleRef),
                `val`(data.fkEnvironmentRef)
            ).returningResult(
                DM_BATCH_EXECUTION.ID,
                DM_BATCH_EXECUTION.START_DATE,
                DM_BATCH_EXECUTION.END_DATE,
                DM_BATCH_EXECUTION.DURATION_IN_MS,
                DM_BATCH_EXECUTION.ORIGIN,
                DM_BATCH_EXECUTION.STATUS,
                DM_BATCH_EXECUTION.TYPE,
                DM_BATCH_EXECUTION.FK_MODULE_REF,
                DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF
            )
            .fetchSingleInto(BatchExecution::class.java)

    fun updateBatchExecutionStartData(
        batchExecutionId: UUID,
        batchExecutionStartUpdateRequest: BatchExecutionStartUpdateRequest
    ): BatchExecution? =
        dslContext.update(DM_BATCH_EXECUTION)
            .set(DM_BATCH_EXECUTION.START_DATE, batchExecutionStartUpdateRequest.startDate)
            .set(DM_BATCH_EXECUTION.STATUS, ExecutionStatus.IN_PROGRESS)
            .where(DM_BATCH_EXECUTION.ID.eq(batchExecutionId))
            .returningResult(
                DM_BATCH_EXECUTION.ID,
                DM_BATCH_EXECUTION.START_DATE,
                DM_BATCH_EXECUTION.END_DATE,
                DM_BATCH_EXECUTION.DURATION_IN_MS,
                DM_BATCH_EXECUTION.ORIGIN,
                DM_BATCH_EXECUTION.STATUS,
                DM_BATCH_EXECUTION.TYPE,
                DM_BATCH_EXECUTION.FK_MODULE_REF,
                DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF
            )
            .fetchOne()
            ?.into(BatchExecution::class.java)

    fun updateBatchExecutionEndData(
        batchExecutionId: UUID,
        batchExecutionEndUpdateRequest: BatchExecutionEndUpdateRequest
    ): BatchExecution? =
        dslContext.update(DM_BATCH_EXECUTION)
            .set(DM_BATCH_EXECUTION.END_DATE, batchExecutionEndUpdateRequest.endDate)
            .set(DM_BATCH_EXECUTION.STATUS, batchExecutionEndUpdateRequest.status.toDto())
            .where(DM_BATCH_EXECUTION.ID.eq(batchExecutionId))
            .returningResult(
                DM_BATCH_EXECUTION.ID,
                DM_BATCH_EXECUTION.START_DATE,
                DM_BATCH_EXECUTION.END_DATE,
                DM_BATCH_EXECUTION.DURATION_IN_MS,
                DM_BATCH_EXECUTION.ORIGIN,
                DM_BATCH_EXECUTION.STATUS,
                DM_BATCH_EXECUTION.TYPE,
                DM_BATCH_EXECUTION.FK_MODULE_REF,
                DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF
            )
            .fetchOne()
            ?.into(BatchExecution::class.java)

    fun delete(id: UUID) {
        dslContext.delete(DM_BATCH_EXECUTION)
            .where(DM_BATCH_EXECUTION.ID.eq(id))
            .execute()
    }

    fun findOneById(id: UUID): BatchExecution? =
        dslContext.fetchOne(DM_BATCH_EXECUTION, DM_BATCH_EXECUTION.ID.eq(id))
            ?.into(BatchExecution::class.java)
}
