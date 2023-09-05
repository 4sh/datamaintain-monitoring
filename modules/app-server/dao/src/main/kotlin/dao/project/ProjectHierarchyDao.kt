package dao.project

import environment.Environment
import environment.EnvironmentHierarchy
import generated.domain.tables.references.DM_ENVIRONMENT
import generated.domain.tables.references.DM_MODULE
import generated.domain.tables.references.DM_PROJECT
import org.jooq.DSLContext
import project.ProjectHierarchy
import project.ProjectHierarchyDaoInterface

class ProjectHierarchyDao(val dslContext: DSLContext) : ProjectHierarchyDaoInterface {
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