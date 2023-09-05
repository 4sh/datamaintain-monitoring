package dao.project

import AbstractDaoTest
import EnvironmentCreationRequestWithoutForeignKey
import generated.domain.tables.references.DM_ENVIRONMENT
import generated.domain.tables.references.DM_MODULE
import generated.domain.tables.references.DM_PROJECT
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import project.ProjectCreationRequest
import strikt.api.expectThat
import strikt.assertions.all
import strikt.assertions.containsExactly
import strikt.assertions.get
import strikt.assertions.isEqualTo

internal class ProjectHierarchyDaoTest : AbstractDaoTest() {
    private val projectHierarchyDao = ProjectHierarchyDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_PROJECT).execute()
        dslContext.delete(DM_ENVIRONMENT).execute()
        dslContext.delete(DM_MODULE).execute()
    }

    @Nested
    inner class TestFindProjectHierarchies {
        @Test
        fun `should load project linked modules and environments`() {
            // Given
            withProjectInDb(
                project = ProjectCreationRequest(name = "myProject", "mp"),
                modulesNames = listOf("mySecondModule"),
                environments = listOf(
                    EnvironmentCreationRequestWithoutForeignKey(
                        name = "myFirstEnvironment",
                        smallName = "mfe"
                    )
                )
            )

            // When
            val projectHierarchies = projectHierarchyDao.findProjectHierarchies()

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
            val projectHierarchies = projectHierarchyDao.findProjectHierarchies()

            // Then
            expectThat(
                projectHierarchies[0].envs
            ).all {
                get { modules.map { it.name } }.containsExactly("myFirstModule", "mySecondModule")
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
            val projectHierarchies = projectHierarchyDao.findProjectHierarchies()

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
            val projectHierarchies = projectHierarchyDao.findProjectHierarchies()

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
            val projectHierarchies = projectHierarchyDao.findProjectHierarchies()

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