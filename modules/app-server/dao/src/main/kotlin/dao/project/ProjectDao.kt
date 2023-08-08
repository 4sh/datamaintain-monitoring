package dao.project

import environment.Environment
import environment.EnvironmentHierarchy
import generated.domain.tables.references.DM_ENVIRONMENT
import generated.domain.tables.references.DM_MODULE
import generated.domain.tables.references.DM_PROJECT
import org.jooq.DSLContext
import org.jooq.impl.DSL.defaultValue
import org.jooq.impl.DSL.`val`
import project.*
import java.util.*

class ProjectDao(val dslContext: DSLContext) : ProjectDaoInterface {
    override fun insert(data: ProjectCreationRequest): Project =
        dslContext.insertInto(DM_PROJECT, DM_PROJECT.ID, DM_PROJECT.NAME, DM_PROJECT.SMALL_NAME)
            .values(
                defaultValue(DM_PROJECT.ID),
                `val`(data.name),
                `val`(data.smallName)
            )
            .returningResult(DM_PROJECT.ID, DM_PROJECT.NAME, DM_PROJECT.SMALL_NAME)
            .fetchSingleInto(Project::class.java)

    override fun updateProjectName(id: UUID, updateRequest: ProjectNameUpdateRequest): Project? =
        dslContext.update(DM_PROJECT)
            .set(DM_PROJECT.NAME, updateRequest.name)
            .set(DM_PROJECT.SMALL_NAME, updateRequest.smallName)
            .where(DM_PROJECT.ID.eq(id))
            .returningResult(
                DM_PROJECT.ID,
                DM_PROJECT.NAME,
                DM_PROJECT.SMALL_NAME
            )
            .fetchOne()
            ?.into(Project::class.java)

    override fun delete(id: UUID) {
        dslContext.delete(DM_PROJECT)
            .where(DM_PROJECT.ID.eq(id))
            .execute()
    }

    override fun findOneById(id: UUID): Project? =
        dslContext.fetchOne(DM_PROJECT, DM_PROJECT.ID.eq(id))
            ?.into(Project::class.java)

    override fun findProjectHierarchies(): List<ProjectHierarchy> {
        val modulesRows = dslContext.select()
            .from(
                DM_PROJECT
                    .innerJoin(DM_MODULE).on(DM_MODULE.FK_PROJECT_REF.eq(DM_PROJECT.ID))
            )
            .orderBy(DM_PROJECT.NAME, DM_MODULE.NAME)
            .fetch()

        val environmentsRows = dslContext.select()
            .from(
                DM_PROJECT
                    .leftJoin(DM_ENVIRONMENT).on(DM_ENVIRONMENT.FK_PROJECT_REF.eq(DM_PROJECT.ID))
            )
            .orderBy(DM_PROJECT.NAME, DM_ENVIRONMENT.NAME)
            .fetch()

        val modulesRowsByProjectsIds = modulesRows.groupBy { it.into(DM_PROJECT).id }


        return environmentsRows.groupBy { it.into(DM_PROJECT) }
            .map { projectRow ->
                val project = projectRow.key
                val projectModules = modulesRowsByProjectsIds[project.id]
                    ?.map { it.into(module.Module::class.java) }
                    ?.sortedBy { it.name }
                    ?: listOf()
                val environments = try {
                    projectRow.value.map { environmentRow ->
                        val environment = environmentRow.into(Environment::class.java)
                        EnvironmentHierarchy(
                            projectModules,
                            environment.id,
                            environment.name,
                            environment.fkProjectRef,
                            environment.smallName
                        )
                    }
                } catch (exception: Exception) {
                    listOf()
                }
                ProjectHierarchy(
                    environments,
                    project.id!!,
                    project.name!!,
                    project.smallName!!
                )
            }
    }
}