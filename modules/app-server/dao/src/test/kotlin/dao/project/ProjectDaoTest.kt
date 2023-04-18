package dao.project

import AbstractDaoTest
import jooq.generated.domain.tables.pojos.DmProject
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
internal class ProjectDaoTest : AbstractDaoTest() {
    private val projectDao = ProjectDao(dslContext)

}