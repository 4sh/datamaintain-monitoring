package dao.project

import AbstractDaoTest
import generated.domain.tables.pojos.DmProject
import generated.domain.tables.references.DM_PROJECT
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.util.UUID

internal class ProjectDaoTest : AbstractDaoTest() {
    private val projectDao = ProjectDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_PROJECT).execute()
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val projectCreationRequest = buildProjectCreationRequest()

            // When
            val insertedProject = projectDao.insert(projectCreationRequest)

            // Then
            expectThat(insertedProject).isNotNull().and {
                get { id }.isNotNull()
                get { name }.isEqualTo(projectCreationRequest.name)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val projectName = "myName"
            val projectCreationRequest = buildProjectCreationRequest(name = projectName)

            // When
            val insertedId = projectDao.insert(projectCreationRequest).id

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

    @Nested
    inner class TestFindOneById {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val id = UUID.randomUUID()

            // When
            val loadedProject = projectDao.findOneById(id)

            // Then
            expectThat(loadedProject).isNull()
        }

        @Test
        fun `should load project from db when it exists`() {
            // Given
            val project = buildProjectCreationRequest()
            val insertedId = projectDao.insert(project).id!!

            // When
            val loadedProject = projectDao.findOneById(insertedId)

            // Then
            expectThat(loadedProject).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(project.name)
            }
        }
    }

    @Nested
    inner class TestUpdate {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val project = buildProjectCreationRequest(name = "myName")
            projectDao.insert(project)
            val randomId = UUID.randomUUID()

            // When
            val updatedProject = projectDao.update(buildDmProject(
                name = "myOtherName",
                id = randomId
            ))

            // Then
            expectThat(updatedProject).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val project = buildProjectCreationRequest(name = "myName")
            val insertedId = projectDao.insert(project).id
            val randomId = UUID.randomUUID()

            // When
            projectDao.update(buildDmProject(name = "myOtherName", id = randomId))

            // Then
            val projectFromDb = projectDao.findOneById(insertedId!!)
            expectThat(projectFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(project.name)
            }
        }

        @Test
        fun `should update given project`() {
            // Given
            val project = buildProjectCreationRequest(name = "myName")
            val insertedId = projectDao.insert(project).id

            // When
            val newName = "myOtherName"
            projectDao.update(buildDmProject(id = insertedId, name = newName))

            // Then
            val updatedProjectFromDb = projectDao.findOneById(insertedId!!)
            expectThat(updatedProjectFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(newName)
            }
        }

        @Test
        fun `should return updated project`() {
            // Given
            val project = buildProjectCreationRequest(name = "myName")
            val insertedId = projectDao.insert(project).id

            // When
            val newName = "myOtherName"
            val updatedProject = projectDao.update(buildDmProject(id = insertedId, name = newName))

            // Then
            expectThat(updatedProject).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(newName)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing document`() {
            // Given
            val insertedId = projectDao.insert(buildProjectCreationRequest()).id!!
            val randomId = UUID.randomUUID()

            // When
            projectDao.delete(randomId)

            // Then
            expectThat(projectDao.findOneById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper document`() {
            // Given
            val insertedId1 = projectDao.insert(buildProjectCreationRequest()).id!!
            val insertedId2 = projectDao.insert(buildProjectCreationRequest()).id!!

            // When
            projectDao.delete(insertedId1)

            // Then
            expectThat(projectDao.findOneById(insertedId1)).isNull()
            expectThat(projectDao.findOneById(insertedId2)).isNotNull()
        }
    }
}