package dao.script.execution

import dao.BaseDao
import jooq.generated.domain.tables.pojos.DmScriptExecution
import jooq.generated.domain.tables.references.DM_SCRIPT_EXECUTION
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import java.util.*

class ScriptExecutionDao(override val dslContext: DSLContext) : BaseDao<DmScriptExecution, UUID> {
    override fun insert(data: DmScriptExecution): DmScriptExecution? =
        dslContext.insertInto(
            DM_SCRIPT_EXECUTION,
            DM_SCRIPT_EXECUTION.START_DATE,
            DM_SCRIPT_EXECUTION.END_DATE,
            DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
            DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
            DM_SCRIPT_EXECUTION.DURATION_IN_MS,
            DM_SCRIPT_EXECUTION.STATUS,
            DM_SCRIPT_EXECUTION.OUTPUT
        ).values(
            `val`(data.startDate),
            `val`(data.endDate),
            `val`(data.fkScriptRef),
            `val`(data.fkBatchExecutionRef),
            `val`(data.durationInMs),
            `val`(data.status),
            `val`(data.output),
        ).returningResult(
            DM_SCRIPT_EXECUTION.ID,
            DM_SCRIPT_EXECUTION.START_DATE,
            DM_SCRIPT_EXECUTION.END_DATE,
            DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
            DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
            DM_SCRIPT_EXECUTION.DURATION_IN_MS,
            DM_SCRIPT_EXECUTION.STATUS,
            DM_SCRIPT_EXECUTION.OUTPUT
        ).fetchOne()
            ?.into(DmScriptExecution::class.java)

    override fun update(data: DmScriptExecution): DmScriptExecution? =
        dslContext.update(DM_SCRIPT_EXECUTION)
            .set(DM_SCRIPT_EXECUTION.END_DATE, data.endDate)
            .set(DM_SCRIPT_EXECUTION.OUTPUT, data.output)
            .set(DM_SCRIPT_EXECUTION.STATUS, data.status)
            .set(DM_SCRIPT_EXECUTION.DURATION_IN_MS, data.durationInMs)
            .where(DM_SCRIPT_EXECUTION.ID.eq(data.id))
            .returningResult(
                DM_SCRIPT_EXECUTION.ID,
                DM_SCRIPT_EXECUTION.START_DATE,
                DM_SCRIPT_EXECUTION.END_DATE,
                DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
                DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
                DM_SCRIPT_EXECUTION.DURATION_IN_MS,
                DM_SCRIPT_EXECUTION.STATUS,
                DM_SCRIPT_EXECUTION.OUTPUT
            )
            .fetchOne()
            ?.into(DmScriptExecution::class.java)

    override fun delete(id: UUID) {
        dslContext.delete(DM_SCRIPT_EXECUTION)
            .where(DM_SCRIPT_EXECUTION.ID.eq(id))
            .execute()
    }

    override fun findOneById(id: UUID): DmScriptExecution? =
        dslContext.fetchOne(DM_SCRIPT_EXECUTION, DM_SCRIPT_EXECUTION.ID.eq(id))
            ?.into(DmScriptExecution::class.java)
}