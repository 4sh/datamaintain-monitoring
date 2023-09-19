package app.server.app

import dao.batch.execution.BatchExecutionDao
import dao.environment.EnvironmentDao
import dao.module.ModuleDao
import dao.project.ProjectDao
import dao.project.ProjectHierarchyDao
import environment.EnvironmentService
import execution.batch.BatchExecutionService
import module.ModuleService
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import project.ProjectService
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

val batchExecutionDao = BatchExecutionDao(dslContext)
val batchExecutionService = BatchExecutionService(batchExecutionDao)