package dao.execution.batch

import AbstractDaoTest
import dao.environment.buildEnvironmentCreationRequest
import dao.module.buildModuleCreationRequest
import dao.project.buildProjectCreationRequest
import dao.utils.toDto
import environment.Environment
import execution.INITIAL_STATUS
import execution.Status
import execution.batch.BatchExecutionEndUpdateRequest
import execution.batch.BatchExecutionSearchRequest
import generated.domain.tables.pojos.DmBatchExecution
import generated.domain.tables.references.DM_BATCH_EXECUTION
import generated.domain.tables.references.DM_ENVIRONMENT
import generated.domain.tables.references.DM_PROJECT
import module.Module
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import project.Project
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.size
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

internal class BatchExecutionDaoTest : AbstractDaoTest() {
    private val batchExecutionDao = BatchExecutionDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_BATCH_EXECUTION).execute()
    }

    companion object {
        private lateinit var project1: Project
        private lateinit var project2: Project
        private lateinit var environment1: Environment
        private lateinit var environment2: Environment
        private lateinit var module1: Module
        private lateinit var module2: Module

        @BeforeAll
        @JvmStatic
        fun insertModuleAndEnvironmentInDB() {
            project1 = projectDao.insert(buildProjectCreationRequest())
            project2 = projectDao.insert(buildProjectCreationRequest())
            environment1 =
                environmentDao.insert(buildEnvironmentCreationRequest(fkProjectRef = project1.id))
            environment2 =
                environmentDao.insert(buildEnvironmentCreationRequest(fkProjectRef = project2.id))
            module1 = moduleDao.insert(buildModuleCreationRequest(fkProjectRef = project1.id))
            module2 = moduleDao.insert(buildModuleCreationRequest(fkProjectRef = project2.id))
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
                fkModuleRef = module1.id,
                fkEnvironmentRef = environment1.id
            )

            // When
            val insertedBatchExecution = batchExecutionDao.insert(batchExecutionCreationRequest)

            // Then
            expectThat(insertedBatchExecution).isNotNull().and {
                get { startDate?.toInstant() }.isEqualTo(batchExecutionCreationRequest.startDate)
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
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
                fkEnvironmentRef = environment1.id,
                fkModuleRef = module1.id
            )

            // When
            val insertedId = batchExecutionDao.insert(batchExecutionCreationRequest).id

            // Then
            val insertedDmBatchExecution = findOneDmBatchExecutionById(insertedId)

            expectThat(
                insertedDmBatchExecution
            ).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.toInstant() }.isEqualTo(batchExecutionCreationRequest.startDate)
                get { endDate }.isNull()
                get { origin }.isEqualTo(batchExecutionCreationRequest.origin.toDto())
                get { type }.isEqualTo(batchExecutionCreationRequest.type.toDto())
                get { status }.isEqualTo(INITIAL_STATUS.toDto())
                get { fkEnvironmentRef }.isEqualTo(environment1.id)
                get { fkModuleRef }.isEqualTo(module1.id)
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
                fkEnvironmentRef = environment1.id,
                fkModuleRef = module1.id
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
            val batchExecution1 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkEnvironmentRef = environment1.id,
                    fkModuleRef = module1.id
                )
            )
            val batchExecution2 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkEnvironmentRef = environment2.id,
                    fkModuleRef = module2.id
                )
            )

            val searchRequest = BatchExecutionSearchRequest()

            // When
            val batchExecutions = batchExecutionDao.find(searchRequest)

            // Then
            expectThat(batchExecutions).and {
                size.isEqualTo(2)
                get { first() }.and {
                    get { id }.isEqualTo(batchExecution1.id)
                    get { startDate }.isEqualTo(batchExecution1.startDate)
                    get { endDate }.isEqualTo(batchExecution1.endDate)
                    get { durationInMs }.isEqualTo(batchExecution1.durationInMs)
                    get { origin }.isEqualTo(batchExecution1.origin)
                    get { status }.isEqualTo(batchExecution1.status)
                    get { type }.isEqualTo(batchExecution1.type)
                    get { project }.and {
                        get { id }.isEqualTo(project1.id)
                        get { name }.isEqualTo(project1.name)
                        get { smallName }.isEqualTo(project1.smallName)
                    }
                    get { module }.and {
                        get { id }.isEqualTo(module1.id)
                        get { name }.isEqualTo(module1.name)
                    }
                    get { environment }.and {
                        get { id }.isEqualTo(environment1.id)
                        get { name }.isEqualTo(environment1.name)
                        get { smallName }.isEqualTo(environment1.smallName)
                    }
                }
                get { last() }.and {
                    get { id }.isEqualTo(batchExecution2.id)
                    get { startDate }.isEqualTo(batchExecution2.startDate)
                    get { endDate }.isEqualTo(batchExecution2.endDate)
                    get { durationInMs }.isEqualTo(batchExecution2.durationInMs)
                    get { origin }.isEqualTo(batchExecution2.origin)
                    get { status }.isEqualTo(batchExecution2.status)
                    get { type }.isEqualTo(batchExecution2.type)
                    get { project }.and {
                        get { id }.isEqualTo(project2.id)
                        get { name }.isEqualTo(project2.name)
                        get { smallName }.isEqualTo(project2.smallName)
                    }
                    get { module }.and {
                        get { id }.isEqualTo(module2.id)
                        get { name }.isEqualTo(module2.name)
                    }
                    get { environment }.and {
                        get { id }.isEqualTo(environment2.id)
                        get { name }.isEqualTo(environment2.name)
                        get { smallName }.isEqualTo(environment2.smallName)
                    }
                }
            }
        }

        @Test
        fun `should find batchExecutions with status filter`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                fkEnvironmentRef = environment1.id,
                fkModuleRef = module1.id
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            val batchExecution2 =
                batchExecutionDao.insert(batchExecutionCreationRequest)
                    .let { batchExecution ->
                        batchExecutionDao.updateBatchExecutionEndData(
                            batchExecution.id,
                            BatchExecutionEndUpdateRequest(
                                endDate = OffsetDateTime.now().plusDays(1L).toInstant(),
                                status = Status.COMPLETED
                            )
                        )!!
                    }

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
                fkEnvironmentRef = environment1.id,
                fkModuleRef = module1.id
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            val batchExecution2 = batchExecutionDao.insert(
                batchExecutionCreationRequest.copy(fkModuleRef = module2.id, fkEnvironmentRef = environment2.id)
            )

            val searchRequest = BatchExecutionSearchRequest(projectRef = project2.id)

            // When
            val batchExecutions = batchExecutionDao.find(searchRequest)

            // Then
            expectThat(batchExecutions).and {
                size.isEqualTo(1)
                get { first() }.and {
                    get { id }.isEqualTo(batchExecution2.id)
                    get { project.id }.isEqualTo(project2.id)
                }
            }
        }

        @Test
        fun `should find batchExecutions with module filter`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                fkEnvironmentRef = environment1.id,
                fkModuleRef = module1.id
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            val batchExecution2 = batchExecutionDao.insert(
                batchExecutionCreationRequest.copy(fkModuleRef = module2.id, fkEnvironmentRef = environment2.id)
            )

            val searchRequest = BatchExecutionSearchRequest(moduleRef = batchExecution2.fkModuleRef)

            // When
            val batchExecutions = batchExecutionDao.find(searchRequest)

            // Then
            expectThat(batchExecutions).and {
                size.isEqualTo(1)
                get { first() }.and {
                    get { id }.isEqualTo(batchExecution2.id)
                    get { module.id }.isEqualTo(module2.id)
                }
            }
        }

        @Test
        fun `should find batchExecutions with environment filter`() {
            // Given
            val batchExecutionCreationRequest = buildBatchExecutionCreationRequest(
                fkEnvironmentRef = environment1.id,
                fkModuleRef = module1.id
            )

            batchExecutionDao.insert(batchExecutionCreationRequest)
            val batchExecution2 = batchExecutionDao.insert(
                batchExecutionCreationRequest.copy(fkModuleRef = module2.id, fkEnvironmentRef = environment2.id)
            )

            val searchRequest = BatchExecutionSearchRequest(environmentRef = batchExecution2.fkEnvironmentRef)

            // When
            val batchExecutions = batchExecutionDao.find(searchRequest)

            // Then
            expectThat(batchExecutions).and {
                size.isEqualTo(1)
                get { first() }.and {
                    get { id }.isEqualTo(batchExecution2.id)
                    get { environment.id }.isEqualTo(environment2.id)
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
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
                fkModuleRef = module1.id,
                fkEnvironmentRef = environment1.id
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
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
                fkModuleRef = module1.id,
                fkEnvironmentRef = environment1.id
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
                get { startDate?.toInstant() }.isEqualTo(batchExecutionCreationRequest.startDate)
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
                fkModuleRef = module1.id,
                fkEnvironmentRef = environment1.id
            )

            val insertedId = batchExecutionDao.insert(batchExecutionCreationRequest).id
            val newStartDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant()

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
                get { startDate?.toInstant() }.isEqualTo(newStartDate)
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
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
                fkModuleRef = module1.id,
                fkEnvironmentRef = environment1.id
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
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
                fkModuleRef = module1.id,
                fkEnvironmentRef = environment1.id
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
                get { startDate?.toInstant() }.isEqualTo(batchExecutionCreationRequest.startDate)
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
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
                fkModuleRef = module1.id,
                fkEnvironmentRef = environment1.id
            )

            val insertedId = batchExecutionDao.insert(batchExecutionCreationRequest).id
            val newEndDate = OffsetDateTime.of(2023, 5, 2, 14, 30, 0, 0, ZoneOffset.UTC).toInstant()

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
                get { startDate?.toInstant() }.isEqualTo(batchExecutionCreationRequest.startDate)
                get { endDate?.toInstant() }.isEqualTo(newEndDate)
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
                    startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
                    fkModuleRef = module1.id,
                    fkEnvironmentRef = environment1.id
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
                    startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
                    fkModuleRef = module1.id,
                    fkEnvironmentRef = environment1.id
                )
            ).id
            val insertedId2 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkModuleRef = module1.id,
                    fkEnvironmentRef = environment1.id
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