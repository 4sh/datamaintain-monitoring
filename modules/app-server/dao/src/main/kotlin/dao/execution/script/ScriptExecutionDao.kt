package dao.execution.script

import dao.utils.toDto
import execution.INITIAL_STATUS
import execution.script.*
import generated.domain.enums.ExecutionStatus
import generated.domain.tables.references.DM_SCRIPT
import generated.domain.tables.references.DM_SCRIPT_EXECUTION
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.DSL.`val`
import script.Script
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
        )
            .returning()
            .fetchSingleInto(ScriptExecution::class.java)

    override fun updateScriptExecutionStartData(
        scriptExecutionId: UUID,
        executionStartData: ScriptExecutionStartUpdateRequest
    ): ScriptExecution? =
        dslContext.update(DM_SCRIPT_EXECUTION)
            .set(DM_SCRIPT_EXECUTION.START_DATE, executionStartData.startDate)
            .set(DM_SCRIPT_EXECUTION.STATUS, ExecutionStatus.IN_PROGRESS)
            .where(DM_SCRIPT_EXECUTION.ID.eq(scriptExecutionId))
            .returning()
            .fetchOneInto(ScriptExecution::class.java)


    override fun updateScriptExecutionEndData(
        scriptExecutionId: UUID,
        executionEndData: ScriptExecutionEndUpdateRequest
    ): ScriptExecution? =
        dslContext.update(DM_SCRIPT_EXECUTION)
            .set(DM_SCRIPT_EXECUTION.END_DATE, executionEndData.endDate)
            .set(DM_SCRIPT_EXECUTION.OUTPUT, executionEndData.output)
            .set(DM_SCRIPT_EXECUTION.STATUS, executionEndData.status.toDto())
            .where(DM_SCRIPT_EXECUTION.ID.eq(scriptExecutionId))
            .returning()
            .fetchOneInto(ScriptExecution::class.java)

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

    override fun find(searchRequest: ScriptExecutionSearchRequest): List<ScriptExecutionListItem> =
        dslContext
            .select(
                DM_SCRIPT_EXECUTION.ID,
                DM_SCRIPT_EXECUTION.START_DATE,
                DM_SCRIPT_EXECUTION.END_DATE,
                DM_SCRIPT_EXECUTION.DURATION_IN_MS,
                DM_SCRIPT_EXECUTION.EXECUTION_ORDER_INDEX,
                DM_SCRIPT_EXECUTION.STATUS
            )
            .select(
                DSL.field(DM_SCRIPT.CHECKSUM.name).`as`("script.checksum"),
                DSL.field(DM_SCRIPT.NAME.name).`as`("script.name")
            )
            .from(DM_SCRIPT_EXECUTION.join(DM_SCRIPT).on(DM_SCRIPT.CHECKSUM.eq(DM_SCRIPT_EXECUTION.FK_SCRIPT_REF)))
            .where(searchRequest.toCondition())
            .fetchInto(ScriptExecutionListItem::class.java)
}

fun ScriptExecutionSearchRequest.toCondition(): Condition {
    var condition = DSL.noCondition()

    if (this.status != null) {
        condition = condition.and(DM_SCRIPT_EXECUTION.STATUS.eq(this.status!!.toDto()))
    }

    if (this.batchExecutionRef != null) {
        condition = condition.and(DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF.eq(this.batchExecutionRef))
    }

    return condition
}