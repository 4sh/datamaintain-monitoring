package dao.project

import generated.domain.tables.pojos.DmProject
import generated.domain.tables.references.DM_PROJECT
import org.jooq.DSLContext
import org.jooq.impl.DSL.defaultValue
import org.jooq.impl.DSL.`val`
import project.ProjectCreationRequest
import java.util.UUID

class ProjectDao(val dslContext: DSLContext) {
    fun insert(data: ProjectCreationRequest): DmProject? =
        dslContext.insertInto(DM_PROJECT, DM_PROJECT.ID, DM_PROJECT.NAME)
            .values(
                defaultValue(DM_PROJECT.ID),
                `val`(data.name)
            )
            .returningResult(DM_PROJECT.ID, DM_PROJECT.NAME)
            .fetchOne()
            ?.into(DmProject::class.java)

    fun update(data: DmProject): DmProject? =
        dslContext.update(DM_PROJECT)
            .set(DM_PROJECT.NAME, data.name)
            .where(DM_PROJECT.ID.eq(data.id))
            .returningResult(DM_PROJECT.ID, DM_PROJECT.NAME)
            .fetchOne()
            ?.into(DmProject::class.java)

    fun delete(id: UUID) {
        dslContext.delete(DM_PROJECT)
            .where(DM_PROJECT.ID.eq(id))
            .execute()
    }

    fun findOneById(id: UUID): DmProject? =
        dslContext.fetchOne(DM_PROJECT, DM_PROJECT.ID.eq(id))
            ?.into(DmProject::class.java)
}