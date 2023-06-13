package dao.script.execution

import generated.domain.tables.pojos.DmScriptExecution
import generated.domain.tables.references.DM_SCRIPT_EXECUTION
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import script.execution.ScriptExecutionCreationRequest
import script.execution.ScriptExecutionEndUpdateRequest
import java.util.*

class ScriptExecutionDao(val dslContext: DSLContext) {
    fun insert(data: ScriptExecutionCreationRequest): DmScriptExecution =
        dslContext.insertInto(
            DM_SCRIPT_EXECUTION,
            DM_SCRIPT_EXECUTION.START_DATE,
            DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
            DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
        ).values(
            `val`(data.startDate),
            `val`(data.fkScriptRef),
            `val`(data.fkBatchExecutionRef),
        ).returningResult(
            DM_SCRIPT_EXECUTION.ID,
            DM_SCRIPT_EXECUTION.START_DATE,
            DM_SCRIPT_EXECUTION.END_DATE,
            DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
            DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
            DM_SCRIPT_EXECUTION.DURATION_IN_MS,
            DM_SCRIPT_EXECUTION.STATUS,
            DM_SCRIPT_EXECUTION.OUTPUT
        ).fetchSingleInto(DmScriptExecution::class.java)

    fun updateScriptExecutionEndData(scriptExecutionId: UUID, executionEndData: ScriptExecutionEndUpdateRequest): DmScriptExecution? =
        dslContext.update(DM_SCRIPT_EXECUTION)
            .set(DM_SCRIPT_EXECUTION.END_DATE, executionEndData.endDate)
            .set(DM_SCRIPT_EXECUTION.OUTPUT, executionEndData.output)
            .set(DM_SCRIPT_EXECUTION.STATUS, executionEndData.status)
            .set(DM_SCRIPT_EXECUTION.DURATION_IN_MS, executionEndData.durationInMs)
            .where(DM_SCRIPT_EXECUTION.ID.eq(scriptExecutionId))
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

    fun delete(id: UUID) {
        dslContext.delete(DM_SCRIPT_EXECUTION)
            .where(DM_SCRIPT_EXECUTION.ID.eq(id))
            .execute()
    }

    fun findOneById(id: UUID): DmScriptExecution? =
        dslContext.fetchOne(DM_SCRIPT_EXECUTION, DM_SCRIPT_EXECUTION.ID.eq(id))
            ?.into(DmScriptExecution::class.java)
}