package dao.execution.batch

import AbstractDaoTest
import dao.environment.EnvironmentDao
import dao.environment.buildEnvironmentCreationRequest
import dao.module.ModuleDao
import dao.module.buildModuleCreationRequest
import dao.project.ProjectDao
import dao.project.buildProjectCreationRequest
import dao.utils.toDto
import execution.INITIAL_STATUS
import execution.Status
import execution.batch.BatchExecutionEndUpdateRequest
import execution.batch.BatchExecutionSearchRequest
import generated.domain.tables.pojos.DmBatchExecution
import generated.domain.tables.references.DM_BATCH_EXECUTION
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

internal class BatchExecutionDaoTest : AbstractDaoTest() {
    private val batchExecutionDao = BatchExecutionDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_BATCH_EXECUTION).execute()
    }

    companion object {
        private lateinit var projectId1: UUID
        private lateinit var projectId2: UUID
        private lateinit var environmentId1: UUID
        private lateinit var environmentId2: UUID
        private lateinit var moduleId1: UUID
        private lateinit var moduleId2: UUID

        @BeforeAll
        @JvmStatic
        fun insertModuleAndEnvironmentInDB() {
            projectId1 = ProjectDao(dslContext).insert(buildProjectCreationRequest()).id
            projectId2 = ProjectDao(dslContext).insert(buildProjectCreationRequest()).id
            environmentId1 =
                EnvironmentDao(dslContext).insert(buildEnvironmentCreationRequest(fkProjectRef = projectId1)).id
            environmentId2 =
                EnvironmentDao(dslContext).insert(buildEnvironmentCreationRequest(fkProjectRef = projectId2)).id
            moduleId1 = ModuleDao(dslContext).insert(buildModuleCreationRequest(fkProjectRef = projectId1)).id
            moduleId2 = ModuleDao(dslContext).insert(buildModuleCreationRequest(fkProjectRef = projectId2)).id
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                fkModuleRef = moduleId1,
                fkEnvironmentRef = environmentId1
            )

            // When
            val insertedBatchExecution = batchExecutionDao.insert(batchExecutionCreationRequest)

            // Then
            expectThat(insertedBatchExecution).isNotNull().and {
                get { startDate?.isEqual(batchExecutionCreationRequest.startDate) }.isTrue()
                get { endDate }.isNull()
                get { origin }.isEqualTo(batchExecutionCreationRequest.origin)
                get { type }.isEqualTo(batchExecutionCreationRequest.type)
                get { status }.isEqualTo(INITIAL_STATUS)
                get { fkModuleRef }.isEqualTo(batchExecutionCreationRequest.fkModuleRef)
                get { fkEnvironmentRef }.isEqualTo(batchExecutionCreationRequest.fkEnvironmentRef)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                fkEnvironmentRef = environmentId1,
                fkModuleRef = moduleId1
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
                get { status }.isEqualTo(INITIAL_STATUS.toDto())
                get { fkEnvironmentRef }.isEqualTo(environmentId1)
                get { fkModuleRef }.isEqualTo(moduleId1)
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
                fkEnvironmentRef = environmentId1,
                fkModuleRef = moduleId1
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
    inner class TestFind {
        @Test
        fun `should return all batchExecution`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                fkEnvironmentRef = environmentId1,
                fkModuleRef = moduleId1
            )

            val batchExecution1 = batchExecutionDao.insert(batchExecutionCreationRequest)
            val batchExecution2 = batchExecutionDao.insert(batchExecutionCreationRequest)

            val searchRequest = BatchExecutionSearchRequest()

            // When
            val batchExecutions = batchExecutionDao.find(searchRequest)

            // Then
            expectThat(batchExecutions).and {
                size.isEqualTo(2)
                get { first().id }.isEqualTo(batchExecution1.id)
                get { last().id }.isEqualTo(batchExecution2.id)
            }
        }

        @Test
        fun `should find batchExecutions with status filter`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                fkEnvironmentRef = environmentId1,
                fkModuleRef = moduleId1
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            var batchExecution2 = batchExecutionDao.insert(batchExecutionCreationRequest)
            batchExecution2 = batchExecutionDao.updateBatchExecutionEndData(
                batchExecution2.id,
                BatchExecutionEndUpdateRequest(
                    endDate = OffsetDateTime.now().plusDays(1L),
                    status = Status.COMPLETED
                )
            )!!

            val searchRequest = BatchExecutionSearchRequest(status = Status.COMPLETED)

            // When
            val batchExecutions = batchExecutionDao.find(searchRequest)

            // Then
            expectThat(batchExecutions).and {
                size.isEqualTo(1)
                get { first() }.and {
                    get { id }.isEqualTo(batchExecution2.id)
                    get { status }.isEqualTo(Status.COMPLETED)
                }
            }
        }

        @Test
        fun `should find batchExecutions with project filter`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                fkEnvironmentRef = environmentId1,
                fkModuleRef = moduleId1
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            val batchExecution2 = batchExecutionDao.insert(
                batchExecutionCreationRequest.copy(fkModuleRef = moduleId2, fkEnvironmentRef = environmentId2)
            )

            val searchRequest = BatchExecutionSearchRequest(projectRef = projectId2)

            // When
            val batchExecutions = batchExecutionDao.find(searchRequest)

            // Then
            expectThat(batchExecutions).and {
                size.isEqualTo(1)
                get { first() }.and {
                    get { id }.isEqualTo(batchExecution2.id)
                    get { project.id }.isEqualTo(projectId2)
                }
            }
        }

        @Test
        fun `should find batchExecutions with module filter`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                fkEnvironmentRef = environmentId1,
                fkModuleRef = moduleId1
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            val batchExecution2 = batchExecutionDao.insert(
                batchExecutionCreationRequest.copy(fkModuleRef = moduleId2, fkEnvironmentRef = environmentId2)
            )

            val searchRequest = BatchExecutionSearchRequest(moduleRef = batchExecution2.fkModuleRef)

            // When
            val batchExecutions = batchExecutionDao.find(searchRequest)

            // Then
            expectThat(batchExecutions).and {
                size.isEqualTo(1)
                get { first() }.and {
                    get { id }.isEqualTo(batchExecution2.id)
                    get { module.id }.isEqualTo(moduleId2)
                }
            }
        }

        @Test
        fun `should find batchExecutions with environment filter`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                fkEnvironmentRef = environmentId1,
                fkModuleRef = moduleId1
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            val batchExecution2 = batchExecutionDao.insert(
                batchExecutionCreationRequest.copy(fkModuleRef = moduleId2, fkEnvironmentRef = environmentId2)
            )

            val searchRequest = BatchExecutionSearchRequest(environmentRef = batchExecution2.fkEnvironmentRef)

            // When
            val batchExecutions = batchExecutionDao.find(searchRequest)

            // Then
            expectThat(batchExecutions).and {
                size.isEqualTo(1)
                get { first() }.and {
                    get { id }.isEqualTo(batchExecution2.id)
                    get { environment.id }.isEqualTo(environmentId2)
                }
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
                fkModuleRef = moduleId1,
                fkEnvironmentRef = environmentId1
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
                fkModuleRef = moduleId1,
                fkEnvironmentRef = environmentId1
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
                get { status }.isEqualTo(INITIAL_STATUS)
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
                fkModuleRef = moduleId1,
                fkEnvironmentRef = environmentId1
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
                get { status }.isEqualTo(Status.IN_PROGRESS)
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
                fkModuleRef = moduleId1,
                fkEnvironmentRef = environmentId1
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
                fkModuleRef = moduleId1,
                fkEnvironmentRef = environmentId1
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
                get { status }.isEqualTo(Status.PENDING)
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
                fkModuleRef = moduleId1,
                fkEnvironmentRef = environmentId1
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
                    fkModuleRef = moduleId1,
                    fkEnvironmentRef = environmentId1
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
                    fkModuleRef = moduleId1,
                    fkEnvironmentRef = environmentId1
                )
            ).id
            val insertedId2 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkModuleRef = moduleId1,
                    fkEnvironmentRef = environmentId1
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