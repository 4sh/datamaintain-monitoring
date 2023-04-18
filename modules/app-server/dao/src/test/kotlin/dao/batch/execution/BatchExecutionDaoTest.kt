package dao.batch.execution

import AbstractDaoTest
import dao.environment.EnvironmentDao
import dao.environment.buildDmEnvironment
import dao.module.ModuleDao
import dao.module.buildDmModule
import dao.project.ProjectDao
import dao.project.buildDmProject
import jooq.generated.domain.tables.pojos.DmBatchExecution
import jooq.generated.domain.tables.references.DM_BATCH_EXECUTION
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.util.*

internal class BatchExecutionDaoTest : AbstractDaoTest() {
    private val batchExecutionDao = BatchExecutionDao(dslContext)

    companion object {
        private lateinit var environmentId: UUID
        private lateinit var moduleId: UUID

        @BeforeAll
        @JvmStatic
        fun insertModuleAndEnvironmentInDB() {
            val projectId = ProjectDao(dslContext).insert(buildDmProject())!!.id!!
            environmentId = EnvironmentDao(dslContext).insert(buildDmEnvironment(fkProjectRef = projectId))!!.id!!
            moduleId = ModuleDao(dslContext).insert(buildDmModule(fkProjectRef = projectId))!!.id!!
        }
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val dmBatchExecution = buildDmBatchExecution(
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            )

            // When
            val insertedBatchExecution = batchExecutionDao.insert(dmBatchExecution)

            // Then
            expectThat(insertedBatchExecution).isNotNull().and {
                get { id }.isNotNull()
                get { fkModuleRef }.isEqualTo(dmBatchExecution.fkModuleRef)
                get { fkEnvironmentRef }.isEqualTo(dmBatchExecution.fkEnvironmentRef)
            }
        }

        @Test
        fun `insert should not use given id as document id`() {
            // Given
            val myId = UUID.randomUUID()
            val dmBatchExecution = buildDmBatchExecution(
                id = myId,
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            )

            // When
            val insertedId = batchExecutionDao.insert(dmBatchExecution)!!.id

            // Then
            expectThat(insertedId).isNotEqualTo(myId)
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val dmBatchExecution = buildDmBatchExecution(
                fkEnvironmentRef = environmentId,
                fkModuleRef = moduleId
            )

            // When
            val insertedId = batchExecutionDao.insert(dmBatchExecution)?.id

            // Then
            val insertedDmBatchExecution = findOneDmBatchExecutionById(insertedId)

            expectThat(
                insertedDmBatchExecution
            ).isNotNull().and {
                get { id }.isEqualTo(insertedId)
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
            val insertedId = batchExecutionDao.insert(buildDmBatchExecution(
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            ))!!.id!!
            val randomId = UUID.randomUUID()

            // When
            batchExecutionDao.delete(randomId)

            // Then
            expectThat(findOneDmBatchExecutionById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper document`() {
            // Given
            val insertedId1 = batchExecutionDao.insert(buildDmBatchExecution(
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            ))!!.id!!
            val insertedId2 = batchExecutionDao.insert(buildDmBatchExecution(
                fkModuleRef = moduleId,
                fkEnvironmentRef = environmentId
            ))!!.id!!

            // When
            batchExecutionDao.delete(insertedId1)

            // Then
            expectThat(findOneDmBatchExecutionById(insertedId1)).isNull()
            expectThat(findOneDmBatchExecutionById(insertedId2)).isNotNull()
        }
    }


    private fun findOneDmBatchExecutionById(id: UUID?) = dslContext.select(
        DM_BATCH_EXECUTION.ID,
        DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF,
        DM_BATCH_EXECUTION.FK_MODULE_REF
    )
        .from(DM_BATCH_EXECUTION)
        .where(DM_BATCH_EXECUTION.ID.eq(id))
        .fetchOneInto(DmBatchExecution::class.java)
}