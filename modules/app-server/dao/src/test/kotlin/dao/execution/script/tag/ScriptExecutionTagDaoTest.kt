package dao.execution.script.tag

import AbstractDaoTest
import dao.environment.buildEnvironmentCreationRequest
import dao.execution.batch.buildBatchExecutionCreationRequest
import dao.execution.script.buildScriptExecutionCreationRequest
import dao.module.buildModuleCreationRequest
import dao.project.buildProjectCreationRequest
import dao.script.buildScriptCreationRequest
import dao.tag.TagDao
import dao.tag.buildTagCreationRequest
import generated.domain.tables.pojos.DmScriptExecutionDmTag
import generated.domain.tables.references.DM_SCRIPT_EXECUTION_DM_TAG
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.util.*

class ScriptExecutionTagDaoTest : AbstractDaoTest() {
    private val scriptExecutionTagDao = ScriptExecutionTagDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_SCRIPT_EXECUTION_DM_TAG).execute()
    }

    companion object {
        private lateinit var scriptExecutionRef: UUID
        private lateinit var tagRef: String
        private lateinit var tagRef2: String

        @BeforeAll
        @JvmStatic
        fun insertNeededObjectsInDB() {
            val projectId = projectDao.insert(buildProjectCreationRequest()).id
            val environmentId = environmentDao.insert(buildEnvironmentCreationRequest(fkProjectRef = projectId)).id
            val moduleId = moduleDao.insert(buildModuleCreationRequest(fkProjectRef = projectId)).id
            val scriptChecksum = "myChecksum"
            scriptDao.insert(buildScriptCreationRequest(checksum = scriptChecksum))

            val batchExecutionRef = batchExecutionDao.insert(
                buildBatchExecutionCreationRequest(
                    fkEnvironmentRef = environmentId,
                    fkModuleRef = moduleId
                )
            ).id

            scriptExecutionRef = scriptExecutionDao.insert(
                buildScriptExecutionCreationRequest(
                    scriptRef = scriptChecksum,
                    batchExecutionRef = batchExecutionRef
                )
            ).id

            val tagDao = TagDao(dslContext)
            tagRef = tagDao.insert(buildTagCreationRequest()).name
            tagRef2 = tagDao.insert(buildTagCreationRequest(name = "myName2")).name
        }
    }


    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted row`() {
            // Given
            val scriptExecutionTagCreationRequest = buildScriptExecutionTagCreationRequest(
                scriptExecutionRef = scriptExecutionRef,
                tagRef = tagRef
            )

            // When
            val insertedScriptExecutionTag = scriptExecutionTagDao.insert(scriptExecutionTagCreationRequest)

            // Then
            expectThat(insertedScriptExecutionTag).isNotNull().and {
                get { fkScriptExecutionRef }.isEqualTo(scriptExecutionRef)
                get { fkTagRef }.isEqualTo(tagRef)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val scriptExecutionTagCreationRequest = buildScriptExecutionTagCreationRequest(
                scriptExecutionRef = scriptExecutionRef,
                tagRef = tagRef
            )

            // When
            scriptExecutionTagDao.insert(scriptExecutionTagCreationRequest)

            // Then
            val insertedDmScriptExecutionTag = dslContext
                .select(DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF, DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF)
                .from(DM_SCRIPT_EXECUTION_DM_TAG)
                .where(DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF.eq(scriptExecutionRef))
                .and(DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF.eq(tagRef))
                .fetchOneInto(DmScriptExecutionDmTag::class.java)

            expectThat(
                insertedDmScriptExecutionTag
            ).isNotNull().and {
                get { fkScriptExecutionRef }.isEqualTo(scriptExecutionRef)
                get { fkTagRef }.isEqualTo(tagRef)

            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing row`() {
            // Given
            scriptExecutionTagDao.insert(
                buildScriptExecutionTagCreationRequest(
                    scriptExecutionRef = scriptExecutionRef,
                    tagRef = tagRef
                )
            )
            val randomScriptExecutionRef = UUID.randomUUID()
            val randomTagRef = UUID.randomUUID().toString()

            // When
            scriptExecutionTagDao.delete(randomScriptExecutionRef, randomTagRef)

            // Then
            val insertedDmScriptExecutionTag = dslContext
                .select(DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF, DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF)
                .from(DM_SCRIPT_EXECUTION_DM_TAG)
                .where(DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF.eq(scriptExecutionRef))
                .and(DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF.eq(tagRef))
                .fetchOneInto(DmScriptExecutionDmTag::class.java)

            expectThat(insertedDmScriptExecutionTag).isNotNull()
        }

        @Test
        fun `should delete proper row`() {
            // Given
            val scriptExecutionDmTag1 = scriptExecutionTagDao.insert(
                buildScriptExecutionTagCreationRequest(
                    scriptExecutionRef = scriptExecutionRef,
                    tagRef = tagRef
                )
            )
            val scriptExecutionDmTag2 = scriptExecutionTagDao.insert(
                buildScriptExecutionTagCreationRequest(
                    scriptExecutionRef = scriptExecutionRef,
                    tagRef = tagRef2
                )
            )

            // When
            scriptExecutionTagDao.delete(scriptExecutionDmTag1.fkScriptExecutionRef!!, scriptExecutionDmTag1.fkTagRef!!)

            // Then
            val insertedDmScriptExecutionTag1 = dslContext
                .select(DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF, DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF)
                .from(DM_SCRIPT_EXECUTION_DM_TAG)
                .where(DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF.eq(scriptExecutionDmTag1.fkScriptExecutionRef))
                .and(DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF.eq(scriptExecutionDmTag1.fkTagRef))
                .fetchOneInto(DmScriptExecutionDmTag::class.java)

            val insertedDmScriptExecutionTag2 = dslContext
                .select(DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF, DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF)
                .from(DM_SCRIPT_EXECUTION_DM_TAG)
                .where(DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF.eq(scriptExecutionDmTag2.fkScriptExecutionRef))
                .and(DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF.eq(scriptExecutionDmTag2.fkTagRef))
                .fetchOneInto(DmScriptExecutionDmTag::class.java)

            expectThat(insertedDmScriptExecutionTag1).isNull()
            expectThat(insertedDmScriptExecutionTag2).isNotNull()
        }
    }
}