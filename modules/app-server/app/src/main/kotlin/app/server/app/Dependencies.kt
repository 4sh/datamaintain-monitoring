package app.server.app

import dao.module.ModuleDao
import dao.project.ProjectDao
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
val projectRepository = ProjectDao(dslContext)
val projectService = ProjectService(projectRepository)

val moduleRepository = ModuleDao(dslContext)
val moduleService = ModuleService(moduleRepository)