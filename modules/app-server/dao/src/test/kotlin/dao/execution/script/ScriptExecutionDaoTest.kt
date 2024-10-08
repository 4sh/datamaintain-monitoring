package dao.execution.script

import AbstractDaoTest
import dao.environment.buildEnvironmentCreationRequest
import dao.execution.batch.buildBatchExecutionCreationRequest
import dao.module.buildModuleCreationRequest
import dao.project.buildProjectCreationRequest
import dao.script.buildScriptCreationRequest
import dao.utils.toDto
import execution.INITIAL_STATUS
import execution.Status
import execution.script.ScriptExecutionDetail
import execution.script.ScriptExecutionSearchRequest
import execution.script.ScriptExecutionWithEnvironment
import dao.jooq.generated.domain.tables.pojos.DmScriptExecution
import dao.jooq.generated.domain.tables.references.DM_SCRIPT_EXECUTION
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import script.Script
import strikt.api.expectThat
import strikt.assertions.*
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

internal class ScriptExecutionDaoTest : AbstractDaoTest() {
    private val scriptExecutionDao = ScriptExecutionDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_SCRIPT_EXECUTION).execute()
    }

    companion object {
        private const val SCRIPT_CHECKSUM = "myScriptChecksum"
        private lateinit var batchExecutionRef1: UUID
        private lateinit var batchExecutionRef2: UUID
        private lateinit var projectId: UUID
        private lateinit var environmentId: UUID
        private lateinit var moduleId: UUID
        private lateinit var script1: Script

        @BeforeAll
        @JvmStatic
        fun insertNeededObjectsInDB() {
            projectId = projectDao.insert(buildProjectCreationRequest()).id
            environmentId = environmentDao.insert(buildEnvironmentCreationRequest(fkProjectRef = projectId)).id
            moduleId = moduleDao.insert(buildModuleCreationRequest(fkProjectRef = projectId)).id
            batchExecutionRef1 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkEnvironmentRef = environmentId,
                    fkModuleRef = moduleId
                )
            ).id
            batchExecutionRef2 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkEnvironmentRef = environmentId,
                    fkModuleRef = moduleId
                )
            ).id
            script1 = scriptDao.insert(buildScriptCreationRequest(checksum = SCRIPT_CHECKSUM))
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted row`() {
            // Given
            val dmScriptExecution = buildScriptExecutionCreationRequest(
                executionOrderIndex = 4,
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
            )

            // When
            val insertedScriptExecution = scriptExecutionDao.insert(dmScriptExecution)

            // Then
            expectThat(insertedScriptExecution).and {
                get { startDate?.toInstant() }.isEqualTo(dmScriptExecution.startDate)
                get { endDate }.isNull()
                get { durationInMs }.isNull()
                get { executionOrderIndex }.isEqualTo(4)
                get { status }.isEqualTo(Status.PENDING)
                get { fkScriptRef }.isEqualTo(SCRIPT_CHECKSUM)
                get { fkBatchExecutionRef }.isEqualTo(batchExecutionRef1)
            }
        }

        @Test
        fun `insert should write row in database`() {
            // Given
            val dmScriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
            )

            // When
            val insertedId = scriptExecutionDao.insert(dmScriptExecution).id

            // Then
            val insertedDmScriptExecution = dslContext.select(
                DM_SCRIPT_EXECUTION.ID,
                DM_SCRIPT_EXECUTION.START_DATE,
                DM_SCRIPT_EXECUTION.END_DATE,
                DM_SCRIPT_EXECUTION.DURATION_IN_MS,
                DM_SCRIPT_EXECUTION.OUTPUT,
                DM_SCRIPT_EXECUTION.EXECUTION_ORDER_INDEX,
                DM_SCRIPT_EXECUTION.STATUS,
                DM_SCRIPT_EXECUTION.FK_SCRIPT_REF,
                DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF
            )
                .from(DM_SCRIPT_EXECUTION)
                .where(DM_SCRIPT_EXECUTION.ID.eq(insertedId))
                .fetchOneInto(DmScriptExecution::class.java)

            expectThat(
                insertedDmScriptExecution
            ).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.toInstant() }.isEqualTo(dmScriptExecution.startDate)
                get { endDate }.isNull()
                get { durationInMs }.isNull()
                get { output }.isNull()
                get { executionOrderIndex }.isEqualTo(0)
                get { status }.isEqualTo(INITIAL_STATUS.toDto())
                get { fkScriptRef }.isEqualTo(SCRIPT_CHECKSUM)
                get { fkBatchExecutionRef }.isEqualTo(batchExecutionRef1)
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
            val loadedScriptExecution = scriptExecutionDao.findOneById(id)

            // Then
            expectThat(loadedScriptExecution).isNull()
        }

        @Test
        fun `should load script execution from db when it exists`() {
            // Given
            val dmScriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
            )

            val insertedId = scriptExecutionDao.insert(dmScriptExecution).id

            // When
            val loadedScriptExecution = scriptExecutionDao.findOneById(insertedId)

            // Then
            expectThat(loadedScriptExecution).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.toInstant() }.isEqualTo(dmScriptExecution.startDate)
                get { endDate }.isNull()
                get { status }.isEqualTo(Status.PENDING)
                get { fkScriptRef }.isEqualTo(SCRIPT_CHECKSUM)
                get { fkBatchExecutionRef }.isEqualTo(batchExecutionRef1)
            }
        }
    }

    @Nested
    inner class TestUpdateExecutionEndData {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM
            )
            scriptExecutionDao.insert(scriptExecution)
            val randomId = UUID.randomUUID()

            // When
            val updatedScriptExecution = scriptExecutionDao.updateScriptExecutionEndData(
                randomId,
                buildScriptExecutionEndUpdateRequest()
            )

            // Then
            expectThat(updatedScriptExecution).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 16, 14, 26, 0, 0, ZoneOffset.UTC).toInstant()
            )
            val insertedId = scriptExecutionDao.insert(scriptExecution).id
            val randomId = UUID.randomUUID()

            // When
            scriptExecutionDao.updateScriptExecutionEndData(
                randomId,
                buildScriptExecutionEndUpdateRequest()
            )

            // Then
            val scriptExecutionFromDb = scriptExecutionDao.findOneById(insertedId)
            expectThat(scriptExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.toInstant() }.isEqualTo(scriptExecution.startDate)
                get { endDate }.isNull()
                get { executionOrderIndex }.isEqualTo(0)
                get { status }.isEqualTo(Status.PENDING)
                get { output }.isNull()
                get { durationInMs }.isNull()
            }
        }

        @Test
        fun `should return updated script execution`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant()
            )
            val insertedId = scriptExecutionDao.insert(scriptExecution).id

            // When
            val newEndDate = OffsetDateTime.of(2023, 5, 2, 14, 38, 0, 0, ZoneOffset.UTC).toInstant()
            val newOutput = "myOtherOutput"
            val newStatus = Status.ERROR
            scriptExecutionDao.updateScriptExecutionEndData(insertedId, buildScriptExecutionEndUpdateRequest(
                endDate = newEndDate,
                output = newOutput,
                status = newStatus
            )
            )

            // Then
            val updatedScriptExecutionFromDb = scriptExecutionDao.findOneById(insertedId)
            expectThat(updatedScriptExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { fkBatchExecutionRef }.isEqualTo(scriptExecution.fkBatchExecutionRef)
                get { fkScriptRef }.isEqualTo(scriptExecution.fkScriptRef)
                get { startDate?.toInstant() }.isEqualTo(scriptExecution.startDate)
                get { endDate?.toInstant() }.isEqualTo(newEndDate)
                get { durationInMs }.isEqualTo(720_000)
                get { output }.isEqualTo(newOutput)
                get { executionOrderIndex }.isEqualTo(0)
                get { status }.isEqualTo(newStatus)
            }
        }
    }

    @Nested
    inner class TestUpdateExecutionStartData {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM
            )
            scriptExecutionDao.insert(scriptExecution)
            val randomId = UUID.randomUUID()

            // When
            val updatedScriptExecution = scriptExecutionDao.updateScriptExecutionStartData(
                randomId,
                buildScriptExecutionStartUpdateRequest()
            )

            // Then
            expectThat(updatedScriptExecution).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 16, 14, 26, 0, 0, ZoneOffset.UTC).toInstant()
            )
            val insertedId = scriptExecutionDao.insert(scriptExecution).id
            val randomId = UUID.randomUUID()

            // When
            scriptExecutionDao.updateScriptExecutionStartData(
                randomId,
                buildScriptExecutionStartUpdateRequest()
            )

            // Then
            val scriptExecutionFromDb = scriptExecutionDao.findOneById(insertedId)
            expectThat(scriptExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.toInstant() }.isEqualTo(scriptExecution.startDate)
                get { endDate }.isNull()
                get { executionOrderIndex }.isEqualTo(0)
                get { status }.isEqualTo(Status.PENDING)
                get { output }.isNull()
                get { durationInMs }.isNull()
            }
        }

        @Test
        fun `should return updated script execution`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant()
            )
            val insertedId = scriptExecutionDao.insert(scriptExecution).id

            // When
            val newStartDate = OffsetDateTime.of(2024, 5, 2, 14, 38, 0, 0, ZoneOffset.UTC).toInstant()
            scriptExecutionDao.updateScriptExecutionStartData(
                insertedId,
                buildScriptExecutionStartUpdateRequest(
                    startDate = newStartDate
                )
            )

            // Then
            val updatedScriptExecutionFromDb = scriptExecutionDao.findOneById(insertedId)
            expectThat(updatedScriptExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { fkBatchExecutionRef }.isEqualTo(scriptExecution.fkBatchExecutionRef)
                get { fkScriptRef }.isEqualTo(scriptExecution.fkScriptRef)
                get { startDate?.toInstant() }.isEqualTo(newStartDate)
                get { endDate }.isNull()
                get { durationInMs }.isNull()
                get { executionOrderIndex }.isEqualTo(0)
                get { status }.isEqualTo(Status.IN_PROGRESS)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing row`() {
            // Given
            val insertedId = scriptExecutionDao.insert(
                buildScriptExecutionCreationRequest(
                    batchExecutionRef = batchExecutionRef1,
                    scriptRef = SCRIPT_CHECKSUM
                )
            ).id
            val randomId = UUID.randomUUID()

            // When
            scriptExecutionDao.delete(randomId)

            // Then
            expectThat(scriptExecutionDao.findOneById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper row`() {
            // Given
            val insertedId1 = scriptExecutionDao.insert(
                buildScriptExecutionCreationRequest(
                    batchExecutionRef = batchExecutionRef1,
                    scriptRef = SCRIPT_CHECKSUM
                )
            ).id
            val insertedId2 = scriptExecutionDao.insert(
                buildScriptExecutionCreationRequest(
                    batchExecutionRef = batchExecutionRef1,
                    scriptRef = SCRIPT_CHECKSUM
                )
            ).id

            // When
            scriptExecutionDao.delete(insertedId1)

            // Then
            expectThat(scriptExecutionDao.findOneById(insertedId1)).isNull()
            expectThat(scriptExecutionDao.findOneById(insertedId2)).isNotNull()
        }
    }

    @Nested
    inner class TestFindOneDetailById {
        @Test
        fun `should return null when script execution id does not exist in db`() {
            // Given
            val id = UUID.randomUUID()

            // When
            val loadedScriptExecution = scriptExecutionDao.findOneDetailById(id)

            // Then
            expectThat(loadedScriptExecution).isNull()
        }

        @Test
        fun `should load script execution with script from db when it exists`() {
            // Given
            val dmScriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
            )

            val insertedId = scriptExecutionDao.insert(dmScriptExecution).id

            // When
            val loadedScriptExecutionDetail = scriptExecutionDao.findOneDetailById(insertedId)

            // Then
            expectThat(loadedScriptExecutionDetail).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.toInstant() }.isEqualTo(dmScriptExecution.startDate)
                get { endDate }.isNull()
                get { status }.isEqualTo(Status.PENDING)
                get { script }.isEqualTo(script1)
                get { fkBatchExecutionRef }.isEqualTo(batchExecutionRef1)
            }
        }
    }

    @Nested
    inner class TestFind {
        @Test
        fun `should return all when no search parameters are present in the request`() {
            // Given
            val dmScriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
            )

            val insertedScriptExecution1 = scriptExecutionDao.insert(dmScriptExecution)
            val insertedScriptExecution2 = scriptExecutionDao.insert(dmScriptExecution)

            val searchRequest = ScriptExecutionSearchRequest()

            // When
            val loadedScriptExecutions = scriptExecutionDao.find(searchRequest)

            // Then
            expectThat(loadedScriptExecutions).and {
                size isEqualTo 2
                get { first() }.and {
                    get { id }.isEqualTo(insertedScriptExecution1.id)
                    get { startDate }.isEqualTo(insertedScriptExecution1.startDate)
                    get { endDate }.isEqualTo(insertedScriptExecution1.endDate)
                    get { durationInMs }.isEqualTo(insertedScriptExecution1.durationInMs)
                    get { executionOrderIndex }.isEqualTo(insertedScriptExecution1.executionOrderIndex)
                    get { status }.isEqualTo(insertedScriptExecution1.status)
                    get { script }.and {
                        get { checksum }.isEqualTo(script1?.checksum)
                        get { name }.isEqualTo(script1?.name)
                    }
                }
                get { last() }.and {
                    get { id }.isEqualTo(insertedScriptExecution2.id)
                    get { startDate }.isEqualTo(insertedScriptExecution2.startDate)
                    get { endDate }.isEqualTo(insertedScriptExecution2.endDate)
                    get { durationInMs }.isEqualTo(insertedScriptExecution2.durationInMs)
                    get { executionOrderIndex }.isEqualTo(insertedScriptExecution2.executionOrderIndex)
                    get { status }.isEqualTo(insertedScriptExecution2.status)
                    get { script }.and {
                        get { checksum }.isEqualTo(script1?.checksum)
                        get { name }.isEqualTo(script1?.name)
                    }
                }
            }
        }

        @Test
        fun `should return proper list when status parameter is present in the request`() {
            // Given
            val dmScriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
            )

            val insertedScriptExecution1 = scriptExecutionDao.insert(dmScriptExecution)
            val insertedScriptExecution2 = scriptExecutionDao.insert(dmScriptExecution)
            scriptExecutionDao.insert(dmScriptExecution)

            val scriptExecutionEndUpdateRequest = buildScriptExecutionEndUpdateRequest(
                endDate = OffsetDateTime.of(2023, 5, 2, 14, 30, 0, 0, ZoneOffset.UTC).toInstant()
            )
            scriptExecutionDao.updateScriptExecutionEndData(
                insertedScriptExecution1.id,
                scriptExecutionEndUpdateRequest
            )
            scriptExecutionDao.updateScriptExecutionEndData(
                insertedScriptExecution2.id,
                scriptExecutionEndUpdateRequest
            )

            val searchRequest = ScriptExecutionSearchRequest(status = Status.COMPLETED)

            // When
            val loadedScriptExecutions = scriptExecutionDao.find(searchRequest)

            // Then
            expectThat(loadedScriptExecutions).and {
                size isEqualTo 2
                get { first() }.and {
                    get { id }.isEqualTo(insertedScriptExecution1.id)
                    get { startDate?.isEqual(insertedScriptExecution1.startDate) }.isTrue()
                    get { endDate?.toInstant() }.isEqualTo(scriptExecutionEndUpdateRequest.endDate)
                    get { durationInMs }.isEqualTo(240_000)
                    get { executionOrderIndex }.isEqualTo(insertedScriptExecution1.executionOrderIndex)
                    get { status }.isEqualTo(searchRequest.status)
                    get { script }.and {
                        get { checksum }.isEqualTo(script1?.checksum)
                        get { name }.isEqualTo(script1?.name)
                    }
                }
                get { last() }.and {
                    get { id }.isEqualTo(insertedScriptExecution2.id)
                    get { startDate?.isEqual(insertedScriptExecution2.startDate) }.isTrue()
                    get { endDate?.toInstant() }.isEqualTo(scriptExecutionEndUpdateRequest.endDate)
                    get { durationInMs }.isEqualTo(240_000)
                    get { executionOrderIndex }.isEqualTo(insertedScriptExecution2.executionOrderIndex)
                    get { status }.isEqualTo(searchRequest.status)
                    get { script }.and {
                        get { checksum }.isEqualTo(script1?.checksum)
                        get { name }.isEqualTo(script1?.name)
                    }
                }
            }
        }

        @Test
        fun `should return proper list when batchExecutionRef parameter is present in the request`() {
            // Given
            val dmScriptExecution = buildScriptExecutionCreationRequest(
                batchExecutionRef = batchExecutionRef1,
                scriptRef = SCRIPT_CHECKSUM,
                startDate = OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC).toInstant(),
            )

            scriptExecutionDao.insert(dmScriptExecution.copy(fkBatchExecutionRef = batchExecutionRef2))
            val insertedScriptExecution2 = scriptExecutionDao.insert(dmScriptExecution)
            val insertedScriptExecution3 = scriptExecutionDao.insert(dmScriptExecution)

            val searchRequest = ScriptExecutionSearchRequest(batchExecutionRef = batchExecutionRef1)

            // When
            val loadedScriptExecutions = scriptExecutionDao.find(searchRequest)

            // Then
            expectThat(loadedScriptExecutions).and {
                size isEqualTo 2
                get { first() }.and {
                    get { id }.isEqualTo(insertedScriptExecution2.id)
                    get { startDate?.isEqual(insertedScriptExecution2.startDate) }.isTrue()
                    get { executionOrderIndex }.isEqualTo(insertedScriptExecution2.executionOrderIndex)
                    get { script }.and {
                        get { checksum }.isEqualTo(script1?.checksum)
                        get { name }.isEqualTo(script1?.name)
                    }
                }
                get { last() }.and {
                    get { id }.isEqualTo(insertedScriptExecution3.id)
                    get { startDate?.isEqual(insertedScriptExecution3.startDate) }.isTrue()
                    get { executionOrderIndex }.isEqualTo(insertedScriptExecution3.executionOrderIndex)
                    get { script }.and {
                        get { checksum }.isEqualTo(script1?.checksum)
                        get { name }.isEqualTo(script1?.name)
                    }
                }
            }
        }
    }

    @Nested
    inner class TestFindModuleScriptsExecutionsInformation {
        @Test
        fun `load all scripts executed at least once on any environment`() {
            // Given
            val environment1 = environmentDao.insert(buildEnvironmentCreationRequest(fkProjectRef = projectId))
            val environment2 = environmentDao.insert(buildEnvironmentCreationRequest(fkProjectRef = projectId))

            val script1 = scriptDao.insert(buildScriptCreationRequest())
            val script2 = scriptDao.insert(buildScriptCreationRequest())

            val batchExecution1 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkEnvironmentRef = environment1.id,
                    fkModuleRef = moduleId
                )
            )

            val batchExecution2 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkEnvironmentRef = environment2.id,
                    fkModuleRef = moduleId
                )
            )
            scriptExecutionDao.insert(
                buildScriptExecutionCreationRequest(
                    batchExecutionRef = batchExecution1.id,
                    scriptRef = script1.checksum
                )
            )
            scriptExecutionDao.insert(
                buildScriptExecutionCreationRequest(
                    batchExecutionRef = batchExecution2.id,
                    scriptRef = script2.checksum
                )
            )

            // When
            val moduleScriptsExecutionsInformation = scriptExecutionDao.findModuleScriptsExecutionsInformation(
                moduleRef = moduleId
            )

            // Then
            expectThat(moduleScriptsExecutionsInformation).map { it.script }
                .containsExactlyInAnyOrder(
                    script1,
                    script2
                )
        }

        @Test
        fun `load all executions of one script`() {
            // Given
            val script1 = scriptDao.insert(buildScriptCreationRequest())

            val batchExecution1 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkEnvironmentRef = environmentId,
                    fkModuleRef = moduleId
                )
            )

            val batchExecution2 = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkEnvironmentRef = environmentId,
                    fkModuleRef = moduleId
                )
            )

            val scriptExecution1 = scriptExecutionDao.insert(
                buildScriptExecutionCreationRequest(
                    batchExecutionRef = batchExecution1.id,
                    scriptRef = script1.checksum
                )
            )
            val scriptExecution2 = scriptExecutionDao.insert(
                buildScriptExecutionCreationRequest(
                    batchExecutionRef = batchExecution2.id,
                    scriptRef = script1.checksum
                )
            )

            // When
            val moduleScriptsExecutionsInformation = scriptExecutionDao.findModuleScriptsExecutionsInformation(
                moduleRef = moduleId
            )

            // Then
            expectThat(moduleScriptsExecutionsInformation)[0].get { executions }.containsExactlyInAnyOrder(
                ScriptExecutionWithEnvironment(
                    id = scriptExecution1.id,
                    startDate = scriptExecution1.startDate,
                    durationInMs = scriptExecution1.durationInMs,
                    executionOrderIndex = scriptExecution1.executionOrderIndex,
                    output = scriptExecution1.output,
                    status = scriptExecution1.status,
                    fkEnvironmentRef = environmentId
                ),
                ScriptExecutionWithEnvironment(
                    id = scriptExecution2.id,
                    startDate = scriptExecution2.startDate,
                    durationInMs = scriptExecution2.durationInMs,
                    executionOrderIndex = scriptExecution2.executionOrderIndex,
                    output = scriptExecution2.output,
                    status = scriptExecution2.status,
                    fkEnvironmentRef = environmentId
                )
            )
        }
    }

    @Nested
    inner class TestFindDetailByBatchExecutionId {
        @Test
        fun `should return empty list when there are no script executions for given batch execution id`() {
            // Given inserted script execution not linked to batchExecutionRef1
            scriptExecutionDao.insert(buildScriptExecutionCreationRequest(scriptRef = SCRIPT_CHECKSUM, batchExecutionRef = batchExecutionRef2))

            // When retrieving scripts execution linked to batchExecutionRef1
            val scriptsExecutions = scriptExecutionDao.findDetailByBatchExecutionId(
                batchExecutionId = batchExecutionRef1
            )

            // Then scripts executions should be empty
            expectThat(scriptsExecutions).isEmpty()
        }
        @Test

        fun `should return batch execution's scripts executions`() {
            // Given inserted script execution linked to batchExecutionRef1
            val insertedScriptExecutions = listOf(
                scriptExecutionDao.insert(buildScriptExecutionCreationRequest(scriptRef = SCRIPT_CHECKSUM, batchExecutionRef = batchExecutionRef1)),
                scriptExecutionDao.insert(buildScriptExecutionCreationRequest(scriptRef = SCRIPT_CHECKSUM, batchExecutionRef = batchExecutionRef1))
            )

            // When retrieving scripts execution linked to batchExecutionRef1
            val scriptsExecutions = scriptExecutionDao.findDetailByBatchExecutionId(
                batchExecutionId = batchExecutionRef1
            )

            // Then scripts executions should contain previously inserted script executions with script1
            expectThat(scriptsExecutions).containsExactlyInAnyOrder(
                insertedScriptExecutions
                    .map { ScriptExecutionDetail(it, script1) }
            )
        }
    }
}