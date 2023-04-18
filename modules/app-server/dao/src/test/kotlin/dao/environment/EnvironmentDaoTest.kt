package dao.environment

import AbstractDaoTest
import dao.project.ProjectDao
import dao.project.buildDmProject
import jooq.generated.domain.tables.pojos.DmEnvironment
import jooq.generated.domain.tables.references.DM_ENVIRONMENT
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.util.*

internal class EnvironmentDaoTest: AbstractDaoTest() {
    val environmentDao = EnvironmentDao(dslContext)

    companion object {
        private lateinit var projectId: UUID

        @BeforeAll
        @JvmStatic
        fun insertOneProjectInDb() {
            projectId = ProjectDao(dslContext).insert(buildDmProject())!!.id!!
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val dmEnvironment = buildDmEnvironment(fkProjectRef = projectId)

            // When
            val insertedEnvironment = environmentDao.insert(dmEnvironment)

            // Then
            expectThat(insertedEnvironment).isNotNull().and {
                get { id }.isNotNull()
                get { name }.isEqualTo(dmEnvironment.name)
                get { fkProjectRef }.isEqualTo(dmEnvironment.fkProjectRef)
            }
        }

        @Test
        fun `insert should not use given id as document id`() {
            // Given
            val myId = UUID.randomUUID()
            val dmEnvironment = buildDmEnvironment(id = myId, fkProjectRef = projectId)

            // When
            val insertedId = environmentDao.insert(dmEnvironment)!!.id

            // Then
            expectThat(insertedId).isNotEqualTo(myId)
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val environmentName = "myName"
            val dmEnvironment = buildDmEnvironment(name = environmentName, fkProjectRef = projectId)

            // When
            val insertedId = environmentDao.insert(dmEnvironment)?.id

            // Then
            val insertedDmEnvironment = dslContext.select(DM_ENVIRONMENT.ID, DM_ENVIRONMENT.NAME, DM_ENVIRONMENT.FK_PROJECT_REF)
                .from(DM_ENVIRONMENT)
                .where(DM_ENVIRONMENT.ID.eq(insertedId))
                .fetchOneInto(DmEnvironment::class.java)

            expectThat(
                insertedDmEnvironment
            ).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(environmentName)
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
            val environment = buildDmEnvironment(fkProjectRef = projectId)
            val insertedId = environmentDao.insert(environment)!!.id!!

            // When
            val loadedEnvironment = environmentDao.findOneById(insertedId)

            // Then
            expectThat(loadedEnvironment).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(environment.name)
                get { fkProjectRef }.isEqualTo(environment.fkProjectRef)
            }
        }
    }

    @Nested
    inner class TestUpdate {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val environment = buildDmEnvironment(name = "myEnvironmentName", fkProjectRef = projectId)
            environmentDao.insert(environment)!!.id
            val randomId = UUID.randomUUID()

            // When
            val updatedEnvironment = environmentDao.update(buildDmEnvironment(
                name = "myOtherEnvironmentName",
                id = randomId,
                fkProjectRef = projectId
            ))

            // Then
            expectThat(updatedEnvironment).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val environment = buildDmEnvironment(name = "myName", fkProjectRef = projectId)
            val insertedId = environmentDao.insert(environment)!!.id
            val randomId = UUID.randomUUID()

            // When
            environmentDao.update(buildDmEnvironment(name = "myOtherName", id = randomId, fkProjectRef = projectId))

            // Then
            val environmentFromDb = environmentDao.findOneById(insertedId!!)
            expectThat(environmentFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(environment.name)
            }
        }

        @Test
        fun `should update given environment without updating project ref`() {
            // Given
            val environment = buildDmEnvironment(name = "myName", fkProjectRef = projectId)
            val insertedId = environmentDao.insert(environment)!!.id
            val otherProjectId = UUID.randomUUID()

            // When
            val newName = "myOtherName"
            environmentDao.update(buildDmEnvironment(id = insertedId, name = newName, fkProjectRef = otherProjectId))

            // Then
            val updatedEnvironmentFromDb = environmentDao.findOneById(insertedId!!)
            expectThat(updatedEnvironmentFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(newName)
                get { fkProjectRef }.isEqualTo(projectId)
            }
        }

        @Test
        fun `should return updated environment`() {
            // Given
            val environment = buildDmEnvironment(name = "myName", fkProjectRef = projectId)
            val insertedId = environmentDao.insert(environment)!!.id
            val otherProjectId = UUID.randomUUID()

            // When
            val newName = "myOtherName"
            val updatedEnvironment = environmentDao.update(buildDmEnvironment(id = insertedId, name = newName, fkProjectRef = otherProjectId))

            // Then
            expectThat(updatedEnvironment).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(newName)
                get { fkProjectRef }.isEqualTo(projectId)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing document`() {
            // Given
            val insertedId = environmentDao.insert(buildDmEnvironment(fkProjectRef = projectId))!!.id!!
            val randomId = UUID.randomUUID()

            // When
            environmentDao.delete(randomId)

            // Then
            expectThat(environmentDao.findOneById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper document`() {
            // Given
            val insertedId1 = environmentDao.insert(buildDmEnvironment(fkProjectRef = projectId))!!.id!!
            val insertedId2 = environmentDao.insert(buildDmEnvironment(fkProjectRef = projectId))!!.id!!

            // When
            environmentDao.delete(insertedId1)

            // Then
            expectThat(environmentDao.findOneById(insertedId1)).isNull()
            expectThat(environmentDao.findOneById(insertedId2)).isNotNull()
        }
    }

}