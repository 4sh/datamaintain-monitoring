import dao.environment.EnvironmentDao
import dao.module.ModuleDao
import dao.project.ProjectDao
import dao.project.buildProjectCreationRequest
import environment.EnvironmentCreationRequest
import module.ModuleCreationRequest
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import project.ProjectCreationRequest
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

@Testcontainers
abstract class AbstractDaoTest {
    abstract fun dropTables()

    @BeforeEach
    fun emptyTables() {
        dropTables()
    }

    fun withProjectInDb(
        project: ProjectCreationRequest,
        environments: List<EnvironmentCreationRequestWithoutForeignKey> = listOf(),
        modulesNames: List<String> = listOf(),
    ) {
        val projectId = projectDao.insert(buildProjectCreationRequest(name = project.name, smallName = project.smallName)).id
        modulesNames.forEach { moduleDao.insert(ModuleCreationRequest(name = it, fkProjectRef = projectId)) }
        environments.forEach { environmentDao.insert(EnvironmentCreationRequest(name = it.name, fkProjectRef = projectId, smallName = it.smallName)) }
    }

    fun  withProjectInDb(
        projectName: String,
        modulesNames: List<String> = listOf(),
        environmentsNames: List<String> = listOf(),
        projectSmallName: String = ""
    ) {
        val projectId = projectDao.insert(buildProjectCreationRequest(name = projectName, smallName = projectSmallName)).id
        modulesNames.forEach { moduleDao.insert(ModuleCreationRequest(name = it, fkProjectRef = projectId)) }
        environmentsNames.forEach { environmentDao.insert(EnvironmentCreationRequest(name = it, fkProjectRef = projectId, smallName = "")) }
    }

    fun withModulesInDb(vararg modules: ModuleCreationRequest) {
        val moduleDao = ModuleDao(dslContext)
        modules.forEach { moduleDao.insert(it) }
    }

    fun withEnvironmentsInDb(vararg environments: EnvironmentCreationRequest) {
        val environmentDao = EnvironmentDao(dslContext)
        environments.forEach { environmentDao.insert(it) }
    }

    companion object {
        lateinit var dslContext: DSLContext
        private const val databaseName = "datamaintain"
        private lateinit var moduleDao: ModuleDao
        private lateinit var projectDao: ProjectDao
        private lateinit var environmentDao: EnvironmentDao

        @Container
        val postgresContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15")
            .withDatabaseName(databaseName)
            .withInitScript("../../resources/test/init-tables.sql")

        @BeforeAll
        @JvmStatic
        fun startContainer() {
            postgresContainer.start()
            val connection: Connection = DriverManager.getConnection(
                postgresContainer.jdbcUrl,
                postgresContainer.username,
                postgresContainer.password
            )
            dslContext = DSL.using(connection, SQLDialect.POSTGRES)
            moduleDao = ModuleDao(dslContext)
            projectDao = ProjectDao(dslContext)
            environmentDao = EnvironmentDao(dslContext)
        }

        @AfterAll
        @JvmStatic
        fun stopContainer() {
            postgresContainer.stop()
        }
    }
}

data class ModuleCreationRequestWithoutForeignKey(val name: String)

data class EnvironmentCreationRequestWithoutForeignKey(val name: String, val smallName: String = "small $name")