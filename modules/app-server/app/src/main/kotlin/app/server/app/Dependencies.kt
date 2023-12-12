package app.server.app

import dao.environment.EnvironmentDao
import dao.module.ModuleDao
import dao.project.ProjectDao
import dao.project.ProjectHierarchyDao
import dao.script.ScriptDao
import environment.EnvironmentService
import module.ModuleService
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import project.ProjectService
import script.ScriptService
import java.sql.DriverManager

val dslContext = DSL.using(
    DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/datamaintain",
        "datamaintain",
        "datamaintain"
    ), SQLDialect.POSTGRES
)
val projectDao = ProjectDao(dslContext)
val projectHierarchyDao = ProjectHierarchyDao(dslContext)
val projectService = ProjectService(projectDao, projectHierarchyDao)

val moduleDao = ModuleDao(dslContext)
val moduleService = ModuleService(moduleDao)

val environmentDao = EnvironmentDao(dslContext)
val environmentService = EnvironmentService(environmentDao)

val scriptDao = ScriptDao(dslContext)
val scriptService = ScriptService(scriptDao)