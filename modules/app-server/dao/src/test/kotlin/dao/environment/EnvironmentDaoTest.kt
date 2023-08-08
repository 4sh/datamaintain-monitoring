package dao.environment

import AbstractDaoTest
import dao.project.ProjectDao
import dao.project.buildProjectCreationRequest
import environment.Environment
import generated.domain.tables.references.DM_ENVIRONMENT
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.util.*

internal class EnvironmentDaoTest: AbstractDaoTest() {
    val environmentDao = EnvironmentDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_ENVIRONMENT).execute()
    }

    companion object {
        private lateinit var projectId: UUID

        @BeforeAll
        @JvmStatic
        fun insertOneProjectInDb() {
            projectId = ProjectDao(dslContext).insert(buildProjectCreationRequest()).id
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val environmentCreationRequest = buildEnvironmentCreationRequest(fkProjectRef = projectId)

            // When
            val insertedEnvironment = environmentDao.insert(environmentCreationRequest)

            // Then
            expectThat(
                insertedEnvironment
            ).isNotNull().and {
                get { name }.isEqualTo(environmentCreationRequest.name)
                get { smallName }.isEqualTo(environmentCreationRequest.smallName)
                get { fkProjectRef }.isEqualTo(environmentCreationRequest.fkProjectRef)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val environmentName = "myName"
            val environmentSmallName = "mn"
            val environmentCreationRequest =
                buildEnvironmentCreationRequest(name = environmentName, smallName = environmentSmallName, fkProjectRef = projectId)

            // When
            val insertedId = environmentDao.insert(environmentCreationRequest).id

            // Then
            val insertedDmEnvironment =
                dslContext.select(DM_ENVIRONMENT.ID, DM_ENVIRONMENT.NAME, DM_ENVIRONMENT.FK_PROJECT_REF, DM_ENVIRONMENT.SMALL_NAME)
                    .from(DM_ENVIRONMENT)
                    .where(DM_ENVIRONMENT.ID.eq(insertedId))
                    .fetchOneInto(Environment::class.java)

            expectThat(
                insertedDmEnvironment
            ).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(environmentName)
                get { smallName }.isEqualTo(environmentSmallName)
                get { fkProjectRef }.isEqualTo(projectId)
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
            val loadedEnvironment = environmentDao.findOneById(id)

            // Then
            expectThat(loadedEnvironment).isNull()
        }

        @Test
        fun `should load environment from db when it exists`() {
            // Given
            val environment = buildEnvironmentCreationRequest(fkProjectRef = projectId)
            val insertedId = environmentDao.insert(environment).id

            // When
            val loadedEnvironment = environmentDao.findOneById(insertedId)

            // Then
            expectThat(loadedEnvironment).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(environment.name)
                get { smallName }.isEqualTo(environment.smallName)
                get { fkProjectRef }.isEqualTo(environment.fkProjectRef)
            }
        }
    }

    @Nested
    inner class TestUpdateName {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val environment = buildEnvironmentCreationRequest(name = "myEnvironmentName", fkProjectRef = projectId)
            environmentDao.insert(environment).id
            val randomId = UUID.randomUUID()

            // When
            val updatedEnvironment = environmentDao.updateEnvironmentName(randomId, buildEnvironmentNameUpdateRequest(
                name = "myOtherEnvironmentName",
            ))

            // Then
            expectThat(updatedEnvironment).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val environment = buildEnvironmentCreationRequest(name = "myName", fkProjectRef = projectId)
            val insertedId = environmentDao.insert(environment).id
            val randomId = UUID.randomUUID()

            // When
            environmentDao.updateEnvironmentName(randomId, buildEnvironmentNameUpdateRequest(name = "myOtherName"))

            // Then
            val environmentFromDb = environmentDao.findOneById(insertedId)
            expectThat(environmentFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(environment.name)
            }
        }

        @Test
        fun `should update given environment's name and small name`() {
            // Given
            val environment = buildEnvironmentCreationRequest(name = "myName", smallName = "mn", fkProjectRef = projectId)
            val insertedId = environmentDao.insert(environment).id
            UUID.randomUUID()

            // When
            val newName = "myOtherName"
            val newSmallName = "mon"
            environmentDao.updateEnvironmentName(insertedId, buildEnvironmentNameUpdateRequest(name = newName, smallName = newSmallName))

            // Then
            val updatedEnvironmentFromDb = environmentDao.findOneById(insertedId)
            expectThat(updatedEnvironmentFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(newName)
                get { smallName }.isEqualTo(newSmallName)
                get { fkProjectRef }.isEqualTo(projectId)
            }
        }

        @Test
        fun `should return updated environment`() {
            // Given
            val environment = buildEnvironmentCreationRequest(name = "myName", fkProjectRef = projectId)
            val insertedId = environmentDao.insert(environment).id

            // When
            val newName = "myOtherName"
            val newSmallName = "mon"
            val updatedEnvironment =
                environmentDao.updateEnvironmentName(insertedId, buildEnvironmentNameUpdateRequest(name = newName, smallName = newSmallName))

            // Then
            expectThat(updatedEnvironment).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(newName)
                get { smallName }.isEqualTo(newSmallName)
                get { fkProjectRef }.isEqualTo(projectId)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing document`() {
            // Given
            val insertedId = environmentDao.insert(buildEnvironmentCreationRequest(fkProjectRef = projectId)).id
            val randomId = UUID.randomUUID()

            // When
            environmentDao.delete(randomId)

            // Then
            expectThat(environmentDao.findOneById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper document`() {
            // Given
            val insertedId1 = environmentDao.insert(buildEnvironmentCreationRequest(fkProjectRef = projectId)).id
            val insertedId2 = environmentDao.insert(buildEnvironmentCreationRequest(fkProjectRef = projectId)).id

            // When
            environmentDao.delete(insertedId1)

            // Then
            expectThat(environmentDao.findOneById(insertedId1)).isNull()
            expectThat(environmentDao.findOneById(insertedId2)).isNotNull()
        }
    }

}