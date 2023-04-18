package dao.project

import AbstractDaoTest
import jooq.generated.domain.tables.pojos.DmProject
import jooq.generated.domain.tables.references.DM_PROJECT
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Testcontainers
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

@Testcontainers
internal class ProjectDaoTest : AbstractDaoTest() {
    private val projectDao = ProjectDao(dslContext)

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return document with not null id`() {
            // Given
            val dmProject = DmProject(
                name = "myName"
            )

            // When
            val insertedId = projectDao.insert(dmProject)?.id

            // Then
            expectThat(insertedId).isNotNull()
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val projectName = "myName"
            val dmProject = DmProject(
                name = projectName
            )

            // When
            val insertedId = projectDao.insert(dmProject)?.id

            // Then
            val insertedDmProject = dslContext.select(DM_PROJECT.ID, DM_PROJECT.NAME)
                .from(DM_PROJECT)
                .where(DM_PROJECT.ID.eq(insertedId))
                .fetchOneInto(DmProject::class.java)

            expectThat(
                insertedDmProject
            ).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(projectName)
            }
        }
    }
}