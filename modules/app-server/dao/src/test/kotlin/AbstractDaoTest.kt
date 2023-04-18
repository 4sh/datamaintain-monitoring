import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.sql.Connection
import java.sql.DriverManager

@Testcontainers
abstract class AbstractDaoTest {

    companion object {
        lateinit var dslContext: DSLContext
        private const val databaseName = "datamaintain"

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
        }

        @AfterAll
        @JvmStatic
        fun stopContainer() {
            postgresContainer.stop()
        }
    }
}