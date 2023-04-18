package dao.project

import dao.BaseDao
import jooq.generated.domain.tables.pojos.DmProject
import jooq.generated.domain.tables.references.DM_PROJECT
import org.jooq.DSLContext
import org.jooq.impl.DSL.defaultValue
import org.jooq.impl.DSL.`val`
import java.util.UUID

class ProjectDao(override val dslContext: DSLContext) : BaseDao<DmProject, UUID> {
    override fun insert(data: DmProject): DmProject? =
        dslContext.insertInto(DM_PROJECT, DM_PROJECT.ID, DM_PROJECT.NAME)
            .values(
                defaultValue(DM_PROJECT.ID),
                `val`(data.name)
            )
            .returningResult(DM_PROJECT.ID, DM_PROJECT.NAME)
            .fetchOne()
            ?.into(DmProject::class.java)

    override fun update(data: DmProject): DmProject? =
        dslContext.update(DM_PROJECT)
            .set(DM_PROJECT.NAME, data.name)
            .where(DM_PROJECT.ID.eq(data.id))
            .returningResult(DM_PROJECT.ID, DM_PROJECT.NAME)
            .fetchOne()
            ?.into(DmProject::class.java)

    override fun delete(id: UUID) {
        dslContext.delete(DM_PROJECT)
            .where(DM_PROJECT.ID.eq(id))
            .execute()
    }
}