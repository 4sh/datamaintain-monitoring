package dao.script.execution

import dao.utils.toDto
import execution.INITIAL_STATUS
import generated.domain.enums.ExecutionStatus
import generated.domain.tables.references.DM_SCRIPT
import generated.domain.tables.references.DM_SCRIPT_EXECUTION
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import script.Script
import script.execution.*
import java.util.*

class ScriptExecutionDao(val dslContext: DSLContext): ScriptExecutionDaoInterface {
    override fun insert(data: ScriptExecutionCreationRequest): ScriptExecution =
        dslContext.insertInto(
            DM_SCRIPT_EXECUTION,
            DM_SCRIPT_EXECUTION.START_DATE,
            DM_SCRIPT_EXECUTION.EXECUTION_ORDER_INDEX,
            DM_SCRIPT_EXECUTION.STATUS,
            DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
            DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
        ).values(
            `val`(data.startDate),
            `val`(data.executionOrderIndex),
            `val`(INITIAL_STATUS.toDto()),
            `val`(data.fkScriptRef),
            `val`(data.fkBatchExecutionRef),
        ).returningResult(
            DM_SCRIPT_EXECUTION.ID,
            DM_SCRIPT_EXECUTION.START_DATE,
            DM_SCRIPT_EXECUTION.END_DATE,
            DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
            DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
            DM_SCRIPT_EXECUTION.DURATION_IN_MS,
            DM_SCRIPT_EXECUTION.EXECUTION_ORDER_INDEX,
            DM_SCRIPT_EXECUTION.STATUS,
            DM_SCRIPT_EXECUTION.OUTPUT
        ).fetchSingleInto(ScriptExecution::class.java)

    override fun updateScriptExecutionStartData(
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
                DM_SCRIPT_EXECUTION.EXECUTION_ORDER_INDEX,
                DM_SCRIPT_EXECUTION.STATUS,
                DM_SCRIPT_EXECUTION.OUTPUT
            ).fetchOne()
            ?.into(ScriptExecution::class.java)


    override fun updateScriptExecutionEndData(
        scriptExecutionId: UUID,
        executionEndData: ScriptExecutionEndUpdateRequest
    ): ScriptExecution? =
        dslContext.update(DM_SCRIPT_EXECUTION)
            .set(DM_SCRIPT_EXECUTION.END_DATE, executionEndData.endDate)
            .set(DM_SCRIPT_EXECUTION.OUTPUT, executionEndData.output)
            .set(DM_SCRIPT_EXECUTION.STATUS, executionEndData.status.toDto())
            .where(DM_SCRIPT_EXECUTION.ID.eq(scriptExecutionId))
            .returningResult(
                DM_SCRIPT_EXECUTION.ID,
                DM_SCRIPT_EXECUTION.START_DATE,
                DM_SCRIPT_EXECUTION.END_DATE,
                DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
                DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
                DM_SCRIPT_EXECUTION.DURATION_IN_MS,
                DM_SCRIPT_EXECUTION.EXECUTION_ORDER_INDEX,
                DM_SCRIPT_EXECUTION.STATUS,
                DM_SCRIPT_EXECUTION.OUTPUT
            )
            .fetchOne()
            ?.into(ScriptExecution::class.java)

    override fun delete(id: UUID) {
        dslContext.delete(DM_SCRIPT_EXECUTION)
            .where(DM_SCRIPT_EXECUTION.ID.eq(id))
            .execute()
    }

    override fun findOneById(id: UUID): ScriptExecution? =
        dslContext.fetchOne(DM_SCRIPT_EXECUTION, DM_SCRIPT_EXECUTION.ID.eq(id))
            ?.into(ScriptExecution::class.java)

    override fun findOneDetailById(id: UUID): ScriptExecutionDetail? =
        dslContext.fetchOne(DM_SCRIPT_EXECUTION, DM_SCRIPT_EXECUTION.ID.eq(id))
            ?.into(ScriptExecution::class.java)
            ?.let { scriptExecution ->
                dslContext
                    .fetchOne(DM_SCRIPT, DM_SCRIPT.CHECKSUM.eq(scriptExecution.fkScriptRef))
                    ?.into(Script::class.java)
                    ?.let { script ->
                        ScriptExecutionDetail(scriptExecution, script)
                    }
            }
}