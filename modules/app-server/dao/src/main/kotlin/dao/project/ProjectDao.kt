package dao.project

import dao.jooq.generated.domain.tables.references.DM_PROJECT
import org.jooq.DSLContext
import org.jooq.impl.DSL.defaultValue
import org.jooq.impl.DSL.`val`
import project.Project
import project.ProjectCreationRequest
import project.ProjectDaoInterface
import project.ProjectNameUpdateRequest
import java.util.*

class ProjectDao(val dslContext: DSLContext) : ProjectDaoInterface {
    override fun insert(data: ProjectCreationRequest): Project =
        dslContext.insertInto(DM_PROJECT, DM_PROJECT.ID, DM_PROJECT.NAME, DM_PROJECT.SMALL_NAME)
            .values(
                defaultValue(DM_PROJECT.ID),
                `val`(data.name),
                `val`(data.smallName)
            )
            .returning()
            .fetchSingleInto(Project::class.java)

    override fun updateProjectName(id: UUID, updateRequest: ProjectNameUpdateRequest): Project? =
        dslContext.update(DM_PROJECT)
            .set(DM_PROJECT.NAME, updateRequest.name)
            .set(DM_PROJECT.SMALL_NAME, updateRequest.smallName)
            .where(DM_PROJECT.ID.eq(id))
            .returning()
            .fetchOneInto(Project::class.java)

    override fun delete(id: UUID) {
        dslContext.delete(DM_PROJECT)
            .where(DM_PROJECT.ID.eq(id))
            .execute()
    }

    override fun findOneById(id: UUID): Project? =
        dslContext.fetchOne(DM_PROJECT, DM_PROJECT.ID.eq(id))
            ?.into(Project::class.java)
}