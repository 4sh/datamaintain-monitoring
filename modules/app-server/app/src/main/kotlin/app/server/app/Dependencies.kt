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
val projectDao = ProjectDao(dslContext)
val projectService = ProjectService(projectDao)

val moduleDao = ModuleDao(dslContext)
val moduleService = ModuleService(moduleDao)