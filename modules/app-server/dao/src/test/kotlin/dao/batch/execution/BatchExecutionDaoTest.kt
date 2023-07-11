package dao.batch.execution

import AbstractDaoTest
import dao.environment.EnvironmentDao
import dao.environment.buildEnvironmentCreationRequest
import dao.module.ModuleDao
import dao.module.buildModuleCreationRequest
import dao.project.ProjectDao
import dao.project.buildProjectCreationRequest
import dao.utils.toDto
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