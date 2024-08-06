package app.server.app

import BatchExecutionGrpcServiceImpl
import ModuleEnvironmentTokenGrpcServiceImpl
import ScriptExecutionGrpcServiceImpl
import ScriptGrpcServiceImpl
import dao.environment.EnvironmentDao
import dao.execution.batch.BatchExecutionDao
import dao.execution.script.ScriptExecutionDao
import dao.module.ModuleDao
import dao.moduleEnvironmentToken.ModuleEnvironmentTokenDao
import dao.project.ProjectDao
import dao.project.ProjectHierarchyDao
import dao.script.ScriptDao
import environment.EnvironmentService
import execution.batch.BatchExecutionService
import execution.script.ScriptExecutionService
import module.ModuleService
import moduleEnvironmentToken.ModuleEnvironmentTokenService
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
val scriptGrpcServiceImpl = ScriptGrpcServiceImpl(scriptService = scriptService)

val batchExecutionDao = BatchExecutionDao(dslContext)
val batchExecutionService = BatchExecutionService(batchExecutionDao)
val batchExecutionGrpcServiceImpl = BatchExecutionGrpcServiceImpl(batchExecutionService = batchExecutionService)

val scriptExecutionDao = ScriptExecutionDao(dslContext)
val scriptExecutionService = ScriptExecutionService(scriptExecutionDao, environmentService)
val scriptExecutionGrpcServiceImpl = ScriptExecutionGrpcServiceImpl(scriptExecutionService)

val moduleEnvironmentTokenDao = ModuleEnvironmentTokenDao(dslContext)
val moduleEnvironmentTokenService = ModuleEnvironmentTokenService(moduleEnvironmentTokenDao)
val moduleEnvironmentTokenGrpcServiceImpl = ModuleEnvironmentTokenGrpcServiceImpl(moduleEnvironmentTokenService)