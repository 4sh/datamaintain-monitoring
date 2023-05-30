package dao.batch.execution

import batch.execution.BatchExecutionCreationRequest
import generated.domain.tables.pojos.DmBatchExecution
import generated.domain.tables.references.DM_BATCH_EXECUTION
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import java.util.*

class BatchExecutionDao(private val dslContext: DSLContext) {
    fun insert(data: BatchExecutionCreationRequest): DmBatchExecution =
        dslContext.insertInto(
            DM_BATCH_EXECUTION,
            DM_BATCH_EXECUTION.FK_MODULE_REF,
            DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF
        )
            .values(
                `val`(data.fkModuleRef),
                `val`(data.fkEnvironmentRef)
            ).returningResult(
                DM_BATCH_EXECUTION.ID,
                DM_BATCH_EXECUTION.FK_MODULE_REF,
                DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF
            )
            .fetchSingleInto(DmBatchExecution::class.java)

    fun delete(id: UUID) {
        dslContext.delete(DM_BATCH_EXECUTION)
            .where(DM_BATCH_EXECUTION.ID.eq(id))
            .execute()
    }
}