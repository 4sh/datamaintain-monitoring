package dao.script.execution

import dao.utils.toDto
import generated.domain.enums.ExecutionStatus
import generated.domain.tables.references.DM_SCRIPT_EXECUTION
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import script.execution.ScriptExecution
import script.execution.ScriptExecutionCreationRequest
import script.execution.ScriptExecutionEndUpdateRequest
import script.execution.ScriptExecutionStartUpdateRequest
import java.util.*

class ScriptExecutionDao(val dslContext: DSLContext) {
    fun insert(data: ScriptExecutionCreationRequest): ScriptExecution =
        dslContext.insertInto(
            DM_SCRIPT_EXECUTION,
            DM_SCRIPT_EXECUTION.START_DATE,
            DM_SCRIPT_EXECUTION.STATUS,
            DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
            DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
        ).values(
            `val`(data.startDate),
            `val`(data.status.toDto()),
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
        ).fetchSingleInto(ScriptExecution::class.java)

    fun updateScriptExecutionStartData(
        scriptExecutionId: UUID,
        executionStartData: ScriptExecutionStartUpdateRequest
    ): ScriptExecution? =
        dslContext.update(DM_SCRIPT_EXECUTION)
            .set(DM_SCRIPT_EXECUTION.START_DATE, executionStartData.startDate)
            .set(DM_SCRIPT_EXECUTION.STATUS, ExecutionStatus.IN_PROGRESS)
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
            ).fetchOne()
            ?.into(ScriptExecution::class.java)


    fun updateScriptExecutionEndData(
        scriptExecutionId: UUID,
        executionEndData: ScriptExecutionEndUpdateRequest
    ): ScriptExecution? =
        dslContext.update(DM_SCRIPT_EXECUTION)
            .set(DM_SCRIPT_EXECUTION.END_DATE, executionEndData.endDate)
            .set(DM_SCRIPT_EXECUTION.OUTPUT, executionEndData.output)
            .set(DM_SCRIPT_EXECUTION.STATUS, executionEndData.status.toDto())
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
            ?.into(ScriptExecution::class.java)

    fun delete(id: UUID) {
        dslContext.delete(DM_SCRIPT_EXECUTION)
            .where(DM_SCRIPT_EXECUTION.ID.eq(id))
            .execute()
    }

    fun findOneById(id: UUID): ScriptExecution? =
        dslContext.fetchOne(DM_SCRIPT_EXECUTION, DM_SCRIPT_EXECUTION.ID.eq(id))
            ?.into(ScriptExecution::class.java)
}