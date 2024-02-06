package app.server.app

import dao.environment.EnvironmentDao
import dao.execution.batch.BatchExecutionDao
import dao.module.ModuleDao
import dao.project.ProjectDao
import dao.project.ProjectHierarchyDao
import dao.script.ScriptDao
import dao.execution.script.ScriptExecutionDao
import environment.EnvironmentService
import execution.batch.BatchExecutionService
import module.ModuleService
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import project.ProjectService
import script.ScriptService
import execution.script.ScriptExecutionService
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

val batchExecutionDao = BatchExecutionDao(dslContext)
val batchExecutionService = BatchExecutionService(batchExecutionDao)

val scriptExecutionDao = ScriptExecutionDao(dslContext)
val scriptExecutionService = ScriptExecutionService(scriptExecutionDao)