package dao.batch.execution

import AbstractDaoTest
import dao.environment.EnvironmentDao
import dao.environment.buildEnvironmentCreationRequest
import dao.module.ModuleDao
import dao.module.buildModuleCreationRequest
import dao.project.ProjectDao
import dao.project.buildProjectCreationRequest
import dao.utils.toDto
import execution.Status
import generated.domain.tables.pojos.DmBatchExecution
import generated.domain.tables.references.DM_BATCH_EXECUTION
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.isTrue
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

internal class BatchExecutionDaoTest : AbstractDaoTest() {
    private val batchExecutionDao = BatchExecutionDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_BATCH_EXECUTION).execute()
    }

    companion object {
        private lateinit var environmentId: UUID
        private lateinit var moduleId: UUID

        @BeforeAll
        @JvmStatic
        fun insertModuleAndEnvironmentInDB() {
            val projectId = ProjectDao(dslContext).insert(buildProjectCreationRequest()).id
            environmentId =
                EnvironmentDao(dslContext).insert(buildEnvironmentCreationRequest(fkProjectRef = projectId)).id
            moduleId = ModuleDao(dslContext).insert(buildModuleCreationRequest(fkProjectRef = projectId)).id
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            )

            // When
            val insertedBatchExecution = batchExecutionDao.insert(batchExecutionCreationRequest)

            // Then
            expectThat(insertedBatchExecution).isNotNull().and {
                get { startDate?.isEqual(batchExecutionCreationRequest.startDate) }.isTrue()
                get { endDate }.isNull()
                get { origin }.isEqualTo(batchExecutionCreationRequest.origin)
                get { type }.isEqualTo(batchExecutionCreationRequest.type)
                get { status }.isEqualTo(batchExecutionCreationRequest.status)
                get { fkModuleRef }.isEqualTo(batchExecutionCreationRequest.fkModuleRef)
                get { fkEnvironmentRef }.isEqualTo(batchExecutionCreationRequest.fkEnvironmentRef)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                fkEnvironmentRef = environmentId,
                fkModuleRef = moduleId
            )

            // When
            val insertedId = batchExecutionDao.insert(batchExecutionCreationRequest).id

            // Then
            val insertedDmBatchExecution = findOneDmBatchExecutionById(insertedId)

            expectThat(
                insertedDmBatchExecution
            ).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.isEqual(batchExecutionCreationRequest.startDate) }.isTrue()
                get { endDate }.isNull()
                get { origin }.isEqualTo(batchExecutionCreationRequest.origin.toDto())
                get { type }.isEqualTo(batchExecutionCreationRequest.type.toDto())
                get { status }.isEqualTo(batchExecutionCreationRequest.status.toDto())
                get { fkEnvironmentRef }.isEqualTo(environmentId)
                get { fkModuleRef }.isEqualTo(moduleId)
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
            val batchExecution = batchExecutionDao.findOneById(id)

            // Then
            expectThat(batchExecution).isNull()
        }

        @Test
        fun `should load script execution from db when it exists`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                fkEnvironmentRef = environmentId,
                fkModuleRef = moduleId
            )

            val insertedId = batchExecutionDao.insert(batchExecutionCreationRequest).id

            // When
            val batchExecution = batchExecutionDao.findOneById(insertedId)

            // Then
            expectThat(batchExecution).isNotNull().and {
                get { id }.isEqualTo(insertedId)
            }
        }
    }

    @Nested
    inner class TestUpdateBatchExecutionStartData {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            val randomId = UUID.randomUUID()

            // When
            val updatedBatchExecution = batchExecutionDao.updateBatchExecutionStartData(
                randomId,
                buildBatchExecutionStartUpdateRequest()
            )

            // Then
            expectThat(updatedBatchExecution).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            )

            val insertedId = batchExecutionDao.insert(batchExecutionCreationRequest).id
            val randomId = UUID.randomUUID()

            // When
            batchExecutionDao.updateBatchExecutionStartData(
                randomId,
                buildBatchExecutionStartUpdateRequest()
            )

            // Then
            val batchExecutionFromDb = batchExecutionDao.findOneById(insertedId)
            expectThat(batchExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.isEqual(batchExecutionCreationRequest.startDate) }.isTrue()
                get { endDate }.isNull()
                get { durationInMs }.isNull()
                get { origin }.isEqualTo(batchExecutionCreationRequest.origin)
                get { status }.isEqualTo(batchExecutionCreationRequest.status)
                get { type }.isEqualTo(batchExecutionCreationRequest.type)
                get { fkModuleRef }.isEqualTo(batchExecutionCreationRequest.fkModuleRef)
                get { fkEnvironmentRef }.isEqualTo(batchExecutionCreationRequest.fkEnvironmentRef)
            }
        }

        @Test
        fun `should return updated script execution`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = null,
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            )

            val insertedId = batchExecutionDao.insert(batchExecutionCreationRequest).id
            val newStartDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC)

            // When
            batchExecutionDao.updateBatchExecutionStartData(
                insertedId,
                buildBatchExecutionStartUpdateRequest(
                    startDate = newStartDate,
                )
            )

            // Then
            val batchExecutionFromDb = batchExecutionDao.findOneById(insertedId)
            expectThat(batchExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.isEqual(newStartDate) }.isTrue()
                get { endDate }.isNull()
                get { durationInMs }.isNull()
                get { origin }.isEqualTo(batchExecutionCreationRequest.origin)
                get { status }.isEqualTo(batchExecutionCreationRequest.status)
                get { type }.isEqualTo(batchExecutionCreationRequest.type)
                get { fkModuleRef }.isEqualTo(batchExecutionCreationRequest.fkModuleRef)
                get { fkEnvironmentRef }.isEqualTo(batchExecutionCreationRequest.fkEnvironmentRef)
            }
        }
    }

    @Nested
    inner class TestUpdateBatchExecutionEndData {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            val randomId = UUID.randomUUID()

            // When
            val updatedBatchExecution = batchExecutionDao.updateBatchExecutionEndData(
                randomId,
                buildBatchExecutionEndUpdateRequest()
            )

            // Then
            expectThat(updatedBatchExecution).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            )

            val insertedId = batchExecutionDao.insert(batchExecutionCreationRequest).id
            val randomId = UUID.randomUUID()

            // When
            batchExecutionDao.updateBatchExecutionEndData(
                randomId,
                buildBatchExecutionEndUpdateRequest()
            )

            // Then
            val batchExecutionFromDb = batchExecutionDao.findOneById(insertedId)
            expectThat(batchExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.isEqual(batchExecutionCreationRequest.startDate) }.isTrue()
                get { endDate }.isNull()
                get { durationInMs }.isNull()
                get { origin }.isEqualTo(batchExecutionCreationRequest.origin)
                get { status }.isEqualTo(batchExecutionCreationRequest.status)
                get { type }.isEqualTo(batchExecutionCreationRequest.type)
                get { fkModuleRef }.isEqualTo(batchExecutionCreationRequest.fkModuleRef)
                get { fkEnvironmentRef }.isEqualTo(batchExecutionCreationRequest.fkEnvironmentRef)
            }
        }

        @Test
        fun `should return updated script execution`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            )

            val insertedId = batchExecutionDao.insert(batchExecutionCreationRequest).id
            val newEndDate = OffsetDateTime.of(2023, 5, 2, 14, 30, 0, 0, ZoneOffset.UTC)

            // When
            batchExecutionDao.updateBatchExecutionEndData(
                insertedId,
                buildBatchExecutionEndUpdateRequest(
                    endDate = newEndDate,
                    status = Status.COMPLETED
                )
            )

            // Then
            val batchExecutionFromDb = batchExecutionDao.findOneById(insertedId)
            expectThat(batchExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.isEqual(batchExecutionCreationRequest.startDate) }.isTrue()
                get { endDate?.isEqual(newEndDate) }.isTrue()
                get { durationInMs }.isEqualTo(240_000)
                get { origin }.isEqualTo(batchExecutionCreationRequest.origin)
                get { status }.isEqualTo(Status.COMPLETED)
                get { type }.isEqualTo(batchExecutionCreationRequest.type)
                get { fkModuleRef }.isEqualTo(batchExecutionCreationRequest.fkModuleRef)
                get { fkEnvironmentRef }.isEqualTo(batchExecutionCreationRequest.fkEnvironmentRef)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing document`() {
            // Given
            val insertedId = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                    fkModuleRef = moduleId,
                    fkEnvironmentRef = environmentId
                )
            ).id
            val randomId = UUID.randomUUID()

            // When
            batchExecutionDao.delete(randomId)

            // Then
            expectThat(findOneDmBatchExecutionById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper document`() {
            // Given
            val insertedId1 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                    fkModuleRef = moduleId,
                    fkEnvironmentRef = environmentId
                )
            ).id
            val insertedId2 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkModuleRef = moduleId,
                    fkEnvironmentRef = environmentId
                )
            ).id

            // When
            batchExecutionDao.delete(insertedId1)

            // Then
            expectThat(findOneDmBatchExecutionById(insertedId1)).isNull()
            expectThat(findOneDmBatchExecutionById(insertedId2)).isNotNull()
        }
    }


    private fun findOneDmBatchExecutionById(id: UUID?) = dslContext.select(
        DM_BATCH_EXECUTION.ID,
        DM_BATCH_EXECUTION.START_DATE,
        DM_BATCH_EXECUTION.END_DATE,
        DM_BATCH_EXECUTION.DURATION_IN_MS,
        DM_BATCH_EXECUTION.ORIGIN,
        DM_BATCH_EXECUTION.TYPE,
        DM_BATCH_EXECUTION.STATUS,
        DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF,
        DM_BATCH_EXECUTION.FK_MODULE_REF
    )
        .from(DM_BATCH_EXECUTION)
        .where(DM_BATCH_EXECUTION.ID.eq(id))
        .fetchOneInto(DmBatchExecution::class.java)
}