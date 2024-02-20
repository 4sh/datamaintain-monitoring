package dao.moduleEnvironmentToken

import AbstractDaoTest
import dao.environment.EnvironmentDao
import dao.environment.buildEnvironmentCreationRequest
import dao.module.ModuleDao
import dao.module.buildModuleCreationRequest
import dao.project.ProjectDao
import dao.project.buildProjectCreationRequest
import generated.domain.tables.references.DM_MODULE_DM_ENVIRONMENT_TOKEN
import moduleEnvironmentToken.ModuleEnvironmentToken
import moduleEnvironmentToken.ModuleEnvironmentTokenCreationRequest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.*
import java.util.*

class ModuleEnvironmentTokenDaoTest : AbstractDaoTest() {
    private val moduleEnvironmentTokenDao = ModuleEnvironmentTokenDao(dslContext = dslContext)

    override fun dropTables() {
        dslContext.delete(DM_MODULE_DM_ENVIRONMENT_TOKEN).execute()
    }

    companion object {
        private lateinit var projectRef: UUID
        private lateinit var environmentRef: UUID
        private lateinit var moduleRef: UUID

        @BeforeAll
        @JvmStatic
        fun insertModuleAndEnvironmentInDB() {
            projectRef = ProjectDao(dslContext).insert(buildProjectCreationRequest()).id
            environmentRef =
                EnvironmentDao(dslContext).insert(buildEnvironmentCreationRequest(fkProjectRef = projectRef)).id
            moduleRef = ModuleDao(dslContext).insert(buildModuleCreationRequest(fkProjectRef = projectRef)).id
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val moduleEnvironmentTokenCreationRequest = ModuleEnvironmentTokenCreationRequest(
                moduleRef = moduleRef,
                environmentRef = environmentRef
            )

            // When
            val insertedModuleEnvironmentToken = moduleEnvironmentTokenDao.insert(moduleEnvironmentTokenCreationRequest)

            // Then
            expectThat(insertedModuleEnvironmentToken).isNotNull().and {
                get { fkModuleRef }.isEqualTo(moduleRef)
                get { fkEnvironmentRef }.isEqualTo(environmentRef)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val moduleEnvironmentTokenCreationRequest = ModuleEnvironmentTokenCreationRequest(
                moduleRef = moduleRef,
                environmentRef = environmentRef
            )

            // When
            moduleEnvironmentTokenDao.insert(moduleEnvironmentTokenCreationRequest)

            val insertedModuleEnvironmentToken = dslContext.select(
                DM_MODULE_DM_ENVIRONMENT_TOKEN.TOKEN_VALUE,
                DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_MODULE_REF,
                DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_ENVIRONMENT_REF,
            )
                .from(DM_MODULE_DM_ENVIRONMENT_TOKEN)
                .where(
                    DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_MODULE_REF.eq(moduleRef)
                        .and(DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_ENVIRONMENT_REF.eq(environmentRef))
                )
                .fetchOneInto(ModuleEnvironmentToken::class.java)

            // Then
            expectThat(insertedModuleEnvironmentToken).isNotNull().and {
                get { fkModuleRef }.isEqualTo(moduleRef)
                get { fkEnvironmentRef }.isEqualTo(environmentRef)
            }
        }

        @Test
        fun `insert should throw an error when trying to create a duplicate on module ref and environment ref`() {
            // Given
            val moduleEnvironmentTokenCreationRequest = ModuleEnvironmentTokenCreationRequest(
                moduleRef = moduleRef,
                environmentRef = environmentRef
            )

            // When
            moduleEnvironmentTokenDao.insert(moduleEnvironmentTokenCreationRequest)

            // Then
            expectCatching { (moduleEnvironmentTokenDao.insert(moduleEnvironmentTokenCreationRequest)) }
                .isFailure()

        }
    }

    @Nested
    inner class TestGetTokenByModuleAndEnvironmentRef {
        @Test
        fun `getTokenByModuleAndEnvironmentRef should return nothing when no row matches module ref and environment ref`() {
            // Given
            val otherModuleRef = ModuleDao(dslContext).insert(buildModuleCreationRequest(fkProjectRef = projectRef)).id
            val otherEnvironmentRef = EnvironmentDao(dslContext).insert(buildEnvironmentCreationRequest(fkProjectRef = projectRef)).id

            moduleEnvironmentTokenDao.insert(
                ModuleEnvironmentTokenCreationRequest(
                    moduleRef = otherModuleRef, environmentRef = otherEnvironmentRef
                )
            )
            moduleEnvironmentTokenDao.insert(
                ModuleEnvironmentTokenCreationRequest(
                    moduleRef = moduleRef, environmentRef = otherEnvironmentRef
                )
            )
            moduleEnvironmentTokenDao.insert(
                ModuleEnvironmentTokenCreationRequest(
                    moduleRef = otherModuleRef, environmentRef = environmentRef
                )
            )

            // When
            val moduleEnvironmentToken = moduleEnvironmentTokenDao.getTokenByModuleAndEnvironmentRef(
                moduleRef = moduleRef,
                environmentRef = environmentRef
            )

            // Then
            expectThat(moduleEnvironmentToken).isNull()
        }

        @Test
        fun `getTokenByModuleAndEnvironmentRef should return token corresponding to module and environment ref`() {
            // Given
            moduleEnvironmentTokenDao.insert(
                ModuleEnvironmentTokenCreationRequest(
                    moduleRef = moduleRef, environmentRef = environmentRef
                )
            )

            // When
            val moduleEnvironmentToken = moduleEnvironmentTokenDao.getTokenByModuleAndEnvironmentRef(
                moduleRef = moduleRef,
                environmentRef = environmentRef
            )

            // Then
            expectThat(moduleEnvironmentToken).isNotNull().and {
                get { fkModuleRef }.isEqualTo(moduleRef)
                get { fkEnvironmentRef }.isEqualTo(environmentRef)
            }
        }
    }

    @Nested
    inner class TestGetTokenByValue {
        @Test
        fun `getTokenByValue should return token corresponding to given token value`() {
            // Given
            val moduleEnvironmentTokenValue = moduleEnvironmentTokenDao.insert(
                ModuleEnvironmentTokenCreationRequest(
                    moduleRef = moduleRef, environmentRef = environmentRef
                )
            ).tokenValue

            // When
            val moduleEnvironmentToken = moduleEnvironmentTokenDao.getTokenByValue(
                moduleEnvironmentTokenValue
            )

            // Then
            expectThat(moduleEnvironmentToken).isNotNull().and {
                get { fkModuleRef }.isEqualTo(moduleRef)
                get { fkEnvironmentRef }.isEqualTo(environmentRef)
                get { tokenValue }.isEqualTo(moduleEnvironmentTokenValue)
            }
        }
    }

    @Nested
    inner class TestDeleteToken {
        @Test
        fun `deleteToken should do nothing when no token has the given value`() {
            // Given
            moduleEnvironmentTokenDao.insert(
                ModuleEnvironmentTokenCreationRequest(
                    moduleRef = moduleRef, environmentRef = environmentRef
                )
            )

            // When
            moduleEnvironmentTokenDao.deleteToken(UUID.randomUUID())

            // Then
            val moduleEnvironmentToken = moduleEnvironmentTokenDao.getTokenByModuleAndEnvironmentRef(
                moduleRef = moduleRef,
                environmentRef = environmentRef
            )

            expectThat(moduleEnvironmentToken).isNotNull().and {
                get { fkModuleRef }.isEqualTo(moduleRef)
                get { fkEnvironmentRef }.isEqualTo(environmentRef)
            }
        }

        @Test
        fun `deleteToken should delete the token with the given value`() {
            // Given
            val tokenValue = moduleEnvironmentTokenDao.insert(
                ModuleEnvironmentTokenCreationRequest(
                    moduleRef = moduleRef, environmentRef = environmentRef
                )
            ).tokenValue

            // When
            moduleEnvironmentTokenDao.deleteToken(tokenValue)

            // Then
            val moduleEnvironmentToken = moduleEnvironmentTokenDao.getTokenByModuleAndEnvironmentRef(
                moduleRef = moduleRef,
                environmentRef = environmentRef
            )

            expectThat(moduleEnvironmentToken).isNull()
        }
    }
}