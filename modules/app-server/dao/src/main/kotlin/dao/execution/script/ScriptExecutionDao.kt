package dao.execution.script

import dao.utils.*
import execution.INITIAL_STATUS
import execution.Status
import execution.script.*
import generated.domain.enums.ExecutionStatus
import generated.domain.tables.references.DM_BATCH_EXECUTION
import generated.domain.tables.references.DM_SCRIPT
import generated.domain.tables.references.DM_SCRIPT_EXECUTION
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.DSL.*
import script.Script
import java.sql.ResultSet
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
            `val`(data.startDate?.toOffsetDateTime()),
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
            .set(DM_SCRIPT_EXECUTION.START_DATE, executionStartData.startDate.toOffsetDateTime())
            .set(DM_SCRIPT_EXECUTION.STATUS, ExecutionStatus.IN_PROGRESS)
            .where(DM_SCRIPT_EXECUTION.ID.eq(scriptExecutionId))
            .returning()
            .fetchOneInto(ScriptExecution::class.java)


    override fun updateScriptExecutionEndData(
        scriptExecutionId: UUID,
        executionEndData: ScriptExecutionEndUpdateRequest
    ): ScriptExecution? =
        dslContext.update(DM_SCRIPT_EXECUTION)
            .set(DM_SCRIPT_EXECUTION.END_DATE, executionEndData.endDate.toOffsetDateTime())
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

    override fun findModuleScriptsExecutionsInformation(moduleRef: UUID): List<ScriptWithAllExecutions> {
        val resultSet = dslContext.select(
            DM_SCRIPT.CHECKSUM,
            DM_SCRIPT.NAME,
            DM_SCRIPT.CONTENT,
            DM_SCRIPT_EXECUTION.ID,
            DM_SCRIPT_EXECUTION.START_DATE,
            DM_SCRIPT_EXECUTION.END_DATE,
            DM_SCRIPT_EXECUTION.DURATION_IN_MS,
            DM_SCRIPT_EXECUTION.EXECUTION_ORDER_INDEX,
            DM_SCRIPT_EXECUTION.OUTPUT,
            DM_SCRIPT_EXECUTION.STATUS,
            DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF,
        ).from(
                DM_SCRIPT_EXECUTION
                    .join(DM_BATCH_EXECUTION.where(DM_BATCH_EXECUTION.FK_MODULE_REF.eq(moduleRef))).on(
                        DM_BATCH_EXECUTION.ID.eq(DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF)
                    )
                    .join(DM_SCRIPT).on(DM_SCRIPT.CHECKSUM.eq(DM_SCRIPT_EXECUTION.FK_SCRIPT_REF))
            )
            .orderBy(DM_SCRIPT.CHECKSUM)
            .fetchResultSet()

        val scriptsWithAllExecutions = mutableListOf<ScriptWithAllExecutions>()
        var currentScript: Script? = null
        var currentScriptExecutions: MutableList<ScriptExecutionWithEnvironment> = mutableListOf()
        while(resultSet.next()) {
            val checksum = resultSet.getString(DM_SCRIPT.CHECKSUM.name)

            if (checksum != currentScript?.checksum) {
                if (currentScript != null) {
                    scriptsWithAllExecutions.add(
                        ScriptWithAllExecutions(
                            currentScript,
                            currentScriptExecutions
                        )
                    )
                }

                currentScript = Script(
                    name = resultSet.getString(DM_SCRIPT.NAME.name),
                    checksum = checksum,
                    content = resultSet.getString(DM_SCRIPT.CONTENT.name),
                )
                currentScriptExecutions = mutableListOf()
            }
            currentScriptExecutions.add(resultSet.toScriptExecutionWithModule())
        }

        if (currentScript != null) {
            scriptsWithAllExecutions.add(
                ScriptWithAllExecutions(
                    currentScript,
                    currentScriptExecutions
                )
            )
        }

        return scriptsWithAllExecutions
    }

    override fun findDetailByBatchExecutionId(batchExecutionId: UUID): List<ScriptExecutionDetail> {
        return dslContext.select(
            DM_SCRIPT_EXECUTION.ID,
            DM_SCRIPT_EXECUTION.START_DATE,
            DM_SCRIPT_EXECUTION.END_DATE,
            DM_SCRIPT_EXECUTION.DURATION_IN_MS,
            DM_SCRIPT_EXECUTION.EXECUTION_ORDER_INDEX,
            DM_SCRIPT_EXECUTION.OUTPUT,
            DM_SCRIPT_EXECUTION.STATUS,
            DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF,
            DM_SCRIPT.NAME.`as`("script.name"),
            DM_SCRIPT.CHECKSUM.`as`("script.checksum"),
            DM_SCRIPT.CONTENT.`as`("script.content"),
        ).from(DM_SCRIPT_EXECUTION.join(DM_SCRIPT).on(DM_SCRIPT.CHECKSUM.eq(DM_SCRIPT_EXECUTION.FK_SCRIPT_REF)))
            .where(DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF.eq(batchExecutionId))
            .fetchInto(ScriptExecutionDetail::class.java)
    }
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

fun ResultSet.toScriptExecutionWithModule(): ScriptExecutionWithEnvironment = ScriptExecutionWithEnvironment(
    id = this.getUUID(DM_SCRIPT_EXECUTION.ID.name),
    startDate = this.getOffsetDateTime(DM_SCRIPT_EXECUTION.START_DATE.name),
    durationInMs = this.getIntOrNull(DM_SCRIPT_EXECUTION.DURATION_IN_MS.name),
    executionOrderIndex = this.getInt(DM_SCRIPT_EXECUTION.EXECUTION_ORDER_INDEX.name),
    output = this.getString(DM_SCRIPT_EXECUTION.OUTPUT.name),
    status = Status.valueOf(this.getString(DM_SCRIPT_EXECUTION.STATUS.name)),
    fkEnvironmentRef = this.getUUID(DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF.name),
)