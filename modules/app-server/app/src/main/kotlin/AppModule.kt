import dao.project.ProjectDao
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection
import java.sql.DriverManager

val url = "jdbc:postgresql://localhost:5432/datamaintain"
val user = "datamaintain"
val mdp = "datamaintain"

val connection: Connection = DriverManager.getConnection(url, user, mdp)
val dslContext = DSL.using(connection, SQLDialect.POSTGRES)

val projectDao = ProjectDao(dslContext)

fun main() {
    //println(projectDao.insert(DmProject(name = "Izivia")))
}