package dao.script.execution

import AbstractDaoTest
import dao.batch.execution.BatchExecutionDao
import dao.batch.execution.buildBatchExecutionCreationRequest
import dao.batch.execution.buildDmBatchExecution
import dao.environment.EnvironmentDao
import dao.environment.buildDmEnvironment
import dao.environment.buildEnvironmentCreationRequest
import dao.module.ModuleDao
import dao.module.buildDmModule
import dao.module.buildModuleCreationRequest
import dao.project.ProjectDao
import dao.project.buildDmProject
import dao.project.buildProjectCreationRequest
import dao.script.ScriptDao
import dao.script.buildDmScript
import dao.script.buildScriptCreationRequest
import generated.domain.enums.ScriptExecutionStatus
import generated.domain.keys.DM_SCRIPT_EXECUTION_DM_TAG_PKEY
import generated.domain.keys.DM_SCRIPT_EXECUTION__DM_SCRIPT_EXECUTION_FK_BATCH_EXECUTION_REF_FKEY
import generated.domain.tables.pojos.DmScriptExecution
import generated.domain.tables.references.DM_SCRIPT_EXECUTION
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
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
        private const val scriptChecksum = "myScriptChecksum"
        private lateinit var batchExecutionRef: UUID

        @BeforeAll
        @JvmStatic
        fun insertNeededObjectsInDB() {
            val projectId = ProjectDao(dslContext).insert(buildProjectCreationRequest())!!.id!!
            val environmentId = EnvironmentDao(dslContext).insert(buildEnvironmentCreationRequest(fkProjectRef = projectId))!!.id!!
            val moduleId = ModuleDao(dslContext).insert(buildModuleCreationRequest(fkProjectRef = projectId))!!.id!!
            batchExecutionRef = BatchExecutionDao(dslContext).insert(buildBatchExecutionCreationRequest(fkEnvironmentRef = environmentId, fkModuleRef = moduleId))!!.id!!
            ScriptDao(dslContext).insert(buildScriptCreationRequest(checksum = scriptChecksum))
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted row`() {
            // Given
            val dmScriptExecution = buildScriptExecutionCreationRequest(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum,
                startDate =  OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
            )

            // When
            val insertedScriptExecution = scriptExecutionDao.insert(dmScriptExecution)

            // Then
            expectThat(insertedScriptExecution).isNotNull().and {
                get { id }.isNotNull()
                get { startDate?.isEqual(dmScriptExecution.startDate) }.isTrue()
                get { fkScriptRef }.isEqualTo(scriptChecksum)
                get { fkBatchExecutionRef }.isEqualTo(batchExecutionRef)
            }
        }

        @Test
        fun `insert should write row in database`() {
            // Given
            val dmScriptExecution = buildScriptExecutionCreationRequest(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum,
                startDate =  OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
            )

            // When
            val insertedId = scriptExecutionDao.insert(dmScriptExecution)?.id

            // Then
            val insertedDmScriptExecution = dslContext.select(
                DM_SCRIPT_EXECUTION.ID,
                DM_SCRIPT_EXECUTION.START_DATE,
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
                get { startDate?.isEqual(dmScriptExecution.startDate) }.isTrue()
                get { fkScriptRef }.isEqualTo(scriptChecksum)
                get { fkBatchExecutionRef }.isEqualTo(batchExecutionRef)
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
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum,
                startDate =  OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
            )

            val insertedId = scriptExecutionDao.insert(dmScriptExecution)!!.id!!

            // When
            val loadedScriptExecution = scriptExecutionDao.findOneById(insertedId)

            // Then
            expectThat(loadedScriptExecution).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.isEqual(dmScriptExecution.startDate) }.isTrue()
                get { fkScriptRef }.isEqualTo(scriptChecksum)
                get { fkBatchExecutionRef }.isEqualTo(batchExecutionRef)
            }
        }
    }

    @Nested
    inner class TestUpdate {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum,
                startDate = OffsetDateTime.of(2023, 5, 16, 14, 26, 0, 0, ZoneOffset.UTC)
            )
            scriptExecutionDao.insert(scriptExecution)
            val randomId = UUID.randomUUID()

            // When
            val updatedScriptExecution = scriptExecutionDao.update(
                buildDmScriptExecution(
                    id = randomId,
                    startDate = OffsetDateTime.of(2023, 8, 26, 4, 26, 0, 0, ZoneOffset.UTC),
                    fkBatchExecutionRef = batchExecutionRef,
                    fkScriptRef = scriptChecksum,
                )
            )

            // Then
            expectThat(updatedScriptExecution).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum,
                startDate = OffsetDateTime.of(2023, 5, 16, 14, 26, 0, 0, ZoneOffset.UTC)
            )
            val insertedId = scriptExecutionDao.insert(scriptExecution)!!.id
            val randomId = UUID.randomUUID()

            // When
            scriptExecutionDao.update(buildDmScriptExecution(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum,
                output = "myOutput2",
                id = randomId
            ))

            // Then
            val scriptExecutionFromDb = scriptExecutionDao.findOneById(insertedId!!)
            expectThat(scriptExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { startDate?.isEqual(scriptExecution.startDate) }.isTrue()
            }
        }

        @Test
        fun `should update given script execution without updating start date, batch execution nor script ref`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum,
                startDate =  OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
            )
            val insertedId = scriptExecutionDao.insert(scriptExecution)!!.id

            // When
            val newEndDate = OffsetDateTime.of(2024, 5, 2, 14, 38, 0, 0, ZoneOffset.UTC)
            val newDurationInMs = 129
            val newOutput = "myOtherOutput"
            val newStatus = ScriptExecutionStatus.KO
            scriptExecutionDao.update(buildDmScriptExecution(
                id = insertedId,
                fkBatchExecutionRef = UUID.randomUUID(),
                fkScriptRef = "anotherScriptChecksum",
                startDate = OffsetDateTime.of(2024, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                endDate = newEndDate,
                durationInMs = newDurationInMs,
                output = newOutput,
                status = newStatus
            ))

            // Then
            val updatedScriptExecutionFromDb = scriptExecutionDao.findOneById(insertedId!!)
            expectThat(updatedScriptExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { fkBatchExecutionRef }.isEqualTo(scriptExecution.fkBatchExecutionRef)
                get { fkScriptRef }.isEqualTo(scriptExecution.fkScriptRef)
                get { startDate?.isEqual(scriptExecution.startDate) }.isTrue()
                get { endDate?.isEqual(newEndDate) }.isTrue()
                get { durationInMs }.isEqualTo(newDurationInMs)
                get { output }.isEqualTo(newOutput)
                get { status }.isEqualTo(newStatus)
            }
        }

        @Test
        fun `should return updated script execution`() {
            // Given
            val scriptExecution = buildScriptExecutionCreationRequest(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum,
                startDate =  OffsetDateTime.of(2023, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC)
            )
            val insertedId = scriptExecutionDao.insert(scriptExecution)!!.id

            // When
            val newEndDate = OffsetDateTime.of(2024, 5, 2, 14, 38, 0, 0, ZoneOffset.UTC)
            val newDurationInMs = 129
            val newOutput = "myOtherOutput"
            val newStatus = ScriptExecutionStatus.KO
            scriptExecutionDao.update(buildDmScriptExecution(
                id = insertedId,
                fkBatchExecutionRef = UUID.randomUUID(),
                fkScriptRef = "anotherScriptChecksum",
                startDate = OffsetDateTime.of(2024, 5, 2, 14, 26, 0, 0, ZoneOffset.UTC),
                endDate = newEndDate,
                durationInMs = newDurationInMs,
                output = newOutput,
                status = newStatus
            ))

            // Then
            val updatedScriptExecutionFromDb = scriptExecutionDao.findOneById(insertedId!!)
            expectThat(updatedScriptExecutionFromDb).isNotNull().and {
                get { id }.isEqualTo(insertedId)
                get { fkBatchExecutionRef }.isEqualTo(scriptExecution.fkBatchExecutionRef)
                get { fkScriptRef }.isEqualTo(scriptExecution.fkScriptRef)
                get { startDate?.isEqual(scriptExecution.startDate) }.isTrue()
                get { endDate?.isEqual(newEndDate) }.isTrue()
                get { durationInMs }.isEqualTo(newDurationInMs)
                get { output }.isEqualTo(newOutput)
                get { status }.isEqualTo(newStatus)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing row`() {
            // Given
            val insertedId = scriptExecutionDao.insert(buildScriptExecutionCreationRequest(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum
            ))!!.id!!
            val randomId = UUID.randomUUID()

            // When
            scriptExecutionDao.delete(randomId)

            // Then
            expectThat(scriptExecutionDao.findOneById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper row`() {
            // Given
            val insertedId1 = scriptExecutionDao.insert(buildScriptExecutionCreationRequest(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum
            ))!!.id!!
            val insertedId2 = scriptExecutionDao.insert(buildScriptExecutionCreationRequest(
                fkBatchExecutionRef = batchExecutionRef,
                fkScriptRef = scriptChecksum
            ))!!.id!!

            // When
            scriptExecutionDao.delete(insertedId1)

            // Then
            expectThat(scriptExecutionDao.findOneById(insertedId1)).isNull()
            expectThat(scriptExecutionDao.findOneById(insertedId2)).isNotNull()
        }
    }
}