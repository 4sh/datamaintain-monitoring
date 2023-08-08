package dao.project

import AbstractDaoTest
import EnvironmentCreationRequestWithoutForeignKey
import dao.environment.buildEnvironmentCreationRequest
import dao.module.ModuleDao
import dao.module.buildModuleCreationRequest
import generated.domain.tables.references.DM_PROJECT
import module.ModuleCreationRequest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import project.Project
import project.ProjectCreationRequest
import project.ProjectHierarchy
import strikt.api.expectThat
import strikt.assertions.*
import java.util.*

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
                get { name }.isEqualTo(projectCreationRequest.name)
                get { smallName }.isEqualTo(projectCreationRequest.smallName)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val projectName = "myName"
            val projectSmallName = "myLittleName"
            val projectCreationRequest = buildProjectCreationRequest(name = projectName, smallName = projectSmallName)

            // When
            val insertedId = projectDao.insert(projectCreationRequest).id

            // Then
            val insertedDmProject = dslContext.select(DM_PROJECT.ID, DM_PROJECT.NAME, DM_PROJECT.SMALL_NAME)
                .from(DM_PROJECT)
                .where(DM_PROJECT.ID.eq(insertedId))
                .fetchOneInto(Project::class.java)

            expectThat(
                insertedDmProject
            ).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(projectName)
                get { smallName }.isEqualTo(projectCreationRequest.smallName)
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
            val insertedId = projectDao.insert(project).id

            // When
            val loadedProject = projectDao.findOneById(insertedId)

            // Then
            expectThat(loadedProject).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(project.name)
                get { smallName }.isEqualTo(project.smallName)
            }
        }
    }

    @Nested
    inner class TestUpdateName {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val project = buildProjectCreationRequest(name = "myName")
            projectDao.insert(project)
            val randomId = UUID.randomUUID()

            // When
            val updatedProject = projectDao.updateProjectName(randomId, buildProjectNameUpdateRequest(
                name = "myOtherName"
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
            projectDao.updateProjectName(randomId, buildProjectNameUpdateRequest(name = "myOtherName"))

            // Then
            val projectFromDb = projectDao.findOneById(insertedId)
            expectThat(projectFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(project.name)
            }
        }

        @Test
        fun `should update given project name and small name`() {
            // Given
            val project = buildProjectCreationRequest(name = "myName")
            val insertedId = projectDao.insert(project).id

            // When
            val newName = "myOtherName"
            val newSmallName = "myOtherSmallName"
            projectDao.updateProjectName(insertedId, buildProjectNameUpdateRequest(name = newName, smallName = newSmallName))

            // Then
            val updatedProjectFromDb = projectDao.findOneById(insertedId)
            expectThat(updatedProjectFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(newName)
                get { smallName }.isEqualTo(newSmallName)
            }
        }

        @Test
        fun `should return updated project`() {
            // Given
            val project = buildProjectCreationRequest(name = "myName")
            val insertedId = projectDao.insert(project).id

            // When
            val newName = "myOtherName"
            val newSmallName = "myOtherSmallName"
            val updatedProject = projectDao.updateProjectName(insertedId, buildProjectNameUpdateRequest(
                name = newName,
                smallName = newSmallName
            ))

            // Then
            expectThat(updatedProject).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { name }.isEqualTo(newName)
                get { smallName }.isEqualTo(newSmallName)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing document`() {
            // Given
            val insertedId = projectDao.insert(buildProjectCreationRequest()).id
            val randomId = UUID.randomUUID()

            // When
            projectDao.delete(randomId)

            // Then
            expectThat(projectDao.findOneById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper document`() {
            // Given
            val insertedId1 = projectDao.insert(buildProjectCreationRequest()).id
            val insertedId2 = projectDao.insert(buildProjectCreationRequest()).id

            // When
            projectDao.delete(insertedId1)

            // Then
            expectThat(projectDao.findOneById(insertedId1)).isNull()
            expectThat(projectDao.findOneById(insertedId2)).isNotNull()
        }
    }

    @Nested
    inner class TestFindProjectHierarchies {
        @Test
        fun `should load project linked modules and environments`() {
            // Given
            withProjectInDb(
                project = ProjectCreationRequest(name = "myProject", "mp"),
                modulesNames = listOf("mySecondModule"),
                environments = listOf(EnvironmentCreationRequestWithoutForeignKey(name = "myFirstEnvironment", smallName = "mfe"))
            )

            // When
            val projectHierarchies = projectDao.findProjectHierarchies()

            // Then
            expectThat(
                projectHierarchies[0]
            ) {
                get { name }.isEqualTo("myProject")
                get { smallName }.isEqualTo("mp")
                get { envs[0].name }.isEqualTo("myFirstEnvironment")
                get { envs[0].smallName }.isEqualTo("mfe")
                get { envs[0].modules[0].name }.isEqualTo("mySecondModule")
            }
        }

        @Test
        fun `should put all modules into each environment hierarchy`() {
            // Given
            withProjectInDb(
                projectName = "myProject",
                modulesNames = listOf("mySecondModule", "myFirstModule"),
                environmentsNames = listOf("myFirstEnvironment", "mySecondEnvironment")
            )

            // When
            val projectHierarchies = projectDao.findProjectHierarchies()

            // Then
            expectThat(
                projectHierarchies[0].envs
            ).all {
                get { modules.map { it.name }}.containsExactly("myFirstModule", "mySecondModule")
            }
        }

        @Test
        fun `should sort modules by their names with alphabetical ascendant order`() {
            // Given
            withProjectInDb(
                projectName = "myProject",
                modulesNames = listOf("mySecondModule", "myFirstModule"),
                environmentsNames = listOf("myFirstEnvironment")
            )

            // When
            val projectHierarchies = projectDao.findProjectHierarchies()

            // Then
            expectThat(
                projectHierarchies[0].envs[0].modules.map { it.name }
            ).containsExactly("myFirstModule", "mySecondModule")
        }

        @Test
        fun `should sort environments by their names with alphabetical ascendant order`() {
            // Given
            withProjectInDb(
                projectName = "myProject",
                environmentsNames = listOf("mySecondEnvironment", "myFirstEnvironment")
            )

            // When
            val projectHierarchies = projectDao.findProjectHierarchies()

            // Then

            expectThat(
                projectHierarchies[0]
            ) {
                get { envs[0].name }.isEqualTo("myFirstEnvironment")
                get { envs[1].name }.isEqualTo("mySecondEnvironment")
            }
        }

        @Test
        fun `should sort projects by their names with alphabetical ascendant order`() {
            // Given
            withProjectInDb(
                projectName = "myProject"
            )

            withProjectInDb(
                projectName = "myOtherProject"
            )

            // When
            val projectHierarchies = projectDao.findProjectHierarchies()

            // Then

            expectThat(
                projectHierarchies
            ) {
                get(0).get { name }.isEqualTo("myOtherProject")
                get(1).get { name }.isEqualTo("myProject")
            }
        }
    }
}