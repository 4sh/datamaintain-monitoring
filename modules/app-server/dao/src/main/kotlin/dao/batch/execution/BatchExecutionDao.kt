package dao.batch.execution

import dao.utils.toDto
import execution.batch.BatchExecution
import execution.batch.BatchExecutionCreationRequest
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
                DM_BATCH_EXECUTION.ORIGIN,
                DM_BATCH_EXECUTION.STATUS,
                DM_BATCH_EXECUTION.TYPE,
                DM_BATCH_EXECUTION.FK_MODULE_REF,
                DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF
            )
            .fetchSingleInto(BatchExecution::class.java)

    fun delete(id: UUID) {
        dslContext.delete(DM_BATCH_EXECUTION)
            .where(DM_BATCH_EXECUTION.ID.eq(id))
            .execute()
    }
}
