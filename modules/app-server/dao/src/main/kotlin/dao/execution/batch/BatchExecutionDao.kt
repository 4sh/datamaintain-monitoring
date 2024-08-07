package dao.execution.batch

import dao.utils.toDto
import dao.utils.toOffsetDateTime
import execution.INITIAL_STATUS
import execution.batch.*
import generated.domain.enums.ExecutionStatus
import generated.domain.tables.references.DM_BATCH_EXECUTION
import generated.domain.tables.references.DM_ENVIRONMENT
import generated.domain.tables.references.DM_MODULE
import generated.domain.tables.references.DM_PROJECT
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.DSL.`val`
import java.util.*

class BatchExecutionDao(private val dslContext: DSLContext): BatchExecutionDaoInterface {
    override fun insert(data: BatchExecutionCreationRequest): BatchExecution =
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
                `val`(data.startDate?.toOffsetDateTime()),
                `val`(data.origin.toDto()),
                `val`(data.type.toDto()),
                `val`(INITIAL_STATUS.toDto()),
                `val`(data.fkModuleRef),
                `val`(data.fkEnvironmentRef)
            ).returning()
            .fetchSingleInto(BatchExecution::class.java)

    override fun updateBatchExecutionStartData(
        batchExecutionId: UUID,
        batchExecutionStartUpdateRequest: BatchExecutionStartUpdateRequest
    ): BatchExecution? =
        dslContext.update(DM_BATCH_EXECUTION)
            .set(DM_BATCH_EXECUTION.START_DATE, batchExecutionStartUpdateRequest.startDate.toOffsetDateTime())
            .set(DM_BATCH_EXECUTION.STATUS, ExecutionStatus.IN_PROGRESS)
            .where(DM_BATCH_EXECUTION.ID.eq(batchExecutionId))
            .returning()
            .fetchOneInto(BatchExecution::class.java)

    override fun updateBatchExecutionEndData(
        batchExecutionId: UUID,
        batchExecutionEndUpdateRequest: BatchExecutionEndUpdateRequest
    ): BatchExecution? =
        dslContext.update(DM_BATCH_EXECUTION)
            .set(DM_BATCH_EXECUTION.END_DATE, batchExecutionEndUpdateRequest.endDate.toOffsetDateTime())
            .set(DM_BATCH_EXECUTION.STATUS, batchExecutionEndUpdateRequest.status.toDto())
            .where(DM_BATCH_EXECUTION.ID.eq(batchExecutionId))
            .returning()
            .fetchOneInto(BatchExecution::class.java)

    override fun delete(id: UUID) {
        dslContext.delete(DM_BATCH_EXECUTION)
            .where(DM_BATCH_EXECUTION.ID.eq(id))
            .execute()
    }

    override fun findOneById(id: UUID): BatchExecutionListItem? =
        selectBatchExecutionListItemData()
            .limit(1)
            .fetchOneInto(BatchExecutionListItem::class.java)

    override fun find(searchRequest: BatchExecutionSearchRequest): List<BatchExecutionListItem>  {
        return selectBatchExecutionListItemData()
            .where(searchRequest.toCondition())
            .fetchInto(BatchExecutionListItem::class.java)
    }

    override fun findMostRecentExecutions(): List<BatchExecutionListItem> {
        return selectBatchExecutionListItemData()
            .orderBy(DM_BATCH_EXECUTION.START_DATE.desc())
            .limit(4)
            .fetchInto(BatchExecutionListItem::class.java)
    }

    private fun selectBatchExecutionListItemData() = dslContext
        .select(
            DM_BATCH_EXECUTION.ID,
            DM_BATCH_EXECUTION.START_DATE,
            DM_BATCH_EXECUTION.END_DATE,
            DM_BATCH_EXECUTION.DURATION_IN_MS,
            DM_BATCH_EXECUTION.ORIGIN,
            DM_BATCH_EXECUTION.TYPE,
            DM_BATCH_EXECUTION.STATUS,
            DM_MODULE.ID.`as`("module.id"),
            DM_MODULE.NAME.`as`("module.name"),
            DM_MODULE.FK_PROJECT_REF.`as`("module.fk_project_ref"),
            DM_ENVIRONMENT.ID.`as`("environment.id"),
            DM_ENVIRONMENT.NAME.`as`("environment.name"),
            DM_ENVIRONMENT.SMALL_NAME.`as`("environment.small_name"),
            DM_ENVIRONMENT.FK_PROJECT_REF.`as`("environment.fk_project_ref"),
            DM_PROJECT.ID.`as`("project.id"),
            DM_PROJECT.NAME.`as`("project.name"),
            DM_PROJECT.SMALL_NAME.`as`("project.small_name"),
        )
        .from(
            DM_BATCH_EXECUTION
                .join(DM_MODULE).on(DM_MODULE.ID.eq(DM_BATCH_EXECUTION.FK_MODULE_REF))
                .join(DM_ENVIRONMENT).on(DM_ENVIRONMENT.ID.eq(DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF))
                .join(DM_PROJECT).on(DM_PROJECT.ID.eq(DM_MODULE.FK_PROJECT_REF))
        )
}

fun BatchExecutionSearchRequest.toCondition(): Condition {
    var condition = DSL.noCondition()

    if (this.status != null) {
        condition = condition.and(DM_BATCH_EXECUTION.STATUS.eq(this.status!!.toDto()))
    }

    if (this.projectRef != null) {
        condition = condition.and(DM_PROJECT.ID.eq(this.projectRef))
    }

    if (this.moduleRef != null) {
        condition = condition.and(DM_MODULE.ID.eq(this.moduleRef))
    }

    if (this.environmentRef != null) {
        condition = condition.and(DM_ENVIRONMENT.ID.eq(this.environmentRef))
    }

    return condition;
}
