package dao.batch.execution

import jooq.generated.domain.tables.pojos.DmBatchExecution
import jooq.generated.domain.tables.references.DM_BATCH_EXECUTION
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import java.util.*

class BatchExecutionDao(private val dslContext: DSLContext) {
    fun insert(data: DmBatchExecution): DmBatchExecution? =
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
            .fetchOne()
            ?.into(DmBatchExecution::class.java)

    fun delete(id: UUID) {
        TODO("Not yet implemented")
    }
}