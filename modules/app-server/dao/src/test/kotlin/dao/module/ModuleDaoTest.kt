package dao.module

import AbstractDaoTest
import dao.project.ProjectDao
import dao.project.buildProjectCreationRequest
import generated.domain.tables.pojos.DmModule
import generated.domain.tables.references.DM_MODULE
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.util.UUID

class ModuleDaoTest : AbstractDaoTest() {
    private val moduleDao = ModuleDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_MODULE).execute()
    }

    companion object {
        private lateinit var projectId1: UUID
        private lateinit var projectId2: UUID

        @BeforeAll
        @JvmStatic
        fun insertOneProject() {
            projectId1 = ProjectDao(dslContext).insert(buildProjectCreationRequest(name = "project1"))!!.id!!
            projectId2 = ProjectDao(dslContext).insert(buildProjectCreationRequest(name = "project2"))!!.id!!
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val moduleCreationRequest = buildModuleCreationRequest(fkProjectRef = projectId1)

            // When
            val insertedModule = moduleDao.insert(moduleCreationRequest)

            // Then
            expectThat(insertedModule).isNotNull().and {
                get { id }.isNotNull()
                get { name }.isEqualTo(moduleCreationRequest.name)
                get { fkProjectRef }.isEqualTo(moduleCreationRequest.fkProjectRef)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val moduleName = "myName"
            val moduleCreationRequest = buildModuleCreationRequest(name = moduleName, fkProjectRef = projectId1)

            // When
            val insertedId = moduleDao.insert(moduleCreationRequest)?.id

            // Then
            val insertedDmModule = dslContext.select(DM_MODULE.ID, DM_MODULE.NAME, DM_MODULE.FK_PROJECT_REF)
                .from(DM_MODULE)
                .where(DM_MODULE.ID.eq(insertedId))
                .fetchOneInto(DmModule::class.java)

            expectThat(insertedDmModule).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(moduleName)
                get { fkProjectRef }.isEqualTo(projectId1)
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
            val loadedModule = moduleDao.findOneById(id)

            // Then
            expectThat(loadedModule).isNull()
        }

        @Test
        fun `should load project from db when it exists`() {
            // Given
            val module = buildModuleCreationRequest(fkProjectRef = projectId1)
            val insertedId = moduleDao.insert(module)!!.id!!

            // When
            val loadedModule = moduleDao.findOneById(insertedId)

            // Then
            expectThat(loadedModule).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(module.name)
                get { fkProjectRef }.isEqualTo(projectId1)
            }
        }


    }

    @Nested
    inner class TestUpdate {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val module = buildModuleCreationRequest(name = "myName", fkProjectRef = projectId1)
            moduleDao.insert(module)
            val randomId = UUID.randomUUID()

            // When
            val updatedModule = moduleDao.update(buildDmModule(
                id = randomId,
                name = "myOtherName",
                fkProjectRef = projectId2
            ))

            // Then
            expectThat(updatedModule).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val module = buildModuleCreationRequest(name = "myName", fkProjectRef = projectId1)
            val insertedId = moduleDao.insert(module)!!.id!!
            val randomId = UUID.randomUUID()

            // When
            moduleDao.update(buildDmModule(name = "myOtherName", id = randomId, fkProjectRef = projectId2))

            // Then
            expectThat(moduleDao.findOneById(insertedId)).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(module.name)
                get { fkProjectRef }.isEqualTo(projectId1)
            }
        }

        @Test
        fun `should update given module (no fk update possible)`() {
            // Given
            val module = buildModuleCreationRequest(name = "myName", fkProjectRef = projectId1)
            val insertId = moduleDao.insert(module)!!.id!!

            // When
            val newName = "myOtherName"
            moduleDao.update(buildDmModule(id = insertId, name = newName, fkProjectRef = projectId2))

            // Then
            val updatedProjectFromDb = moduleDao.findOneById(insertId)
            expectThat(updatedProjectFromDb).isNotNull().and {
                get { id }.isEqualTo(insertId)
                get { name }.isEqualTo(newName)
                get { fkProjectRef }.isEqualTo(projectId1)
            }
        }

        @Test
        fun `should return updated module`() {
            // Given
            val module = buildModuleCreationRequest(name = "myName", fkProjectRef = projectId1)
            val insertedId = moduleDao.insert(module)!!.id!!

            // When
            val newName = "myOtherName"
            val updatedModule = moduleDao.update(
                buildDmModule(id = insertedId, name = newName, fkProjectRef = projectId2)
            )

            // Then
            expectThat(updatedModule).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(newName)
                get { fkProjectRef }.isEqualTo(projectId1)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing document`() {
            // Given
            val insertedId = moduleDao.insert(buildModuleCreationRequest(fkProjectRef = projectId1))!!.id!!
            val randomId = UUID.randomUUID()

            // When
            moduleDao.delete(randomId)

            // Then
            expectThat(moduleDao.findOneById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper document`() {
            // Given
            val insertedId1 = moduleDao.insert(buildModuleCreationRequest(fkProjectRef = projectId1))!!.id!!
            val insertedId2 = moduleDao.insert(buildModuleCreationRequest(fkProjectRef = projectId1))!!.id!!

            // When
            moduleDao.delete(insertedId1)

            // Then
            expectThat(moduleDao.findOneById(insertedId1)).isNull()
            expectThat(moduleDao.findOneById(insertedId2)).isNotNull()
        }
    }
}