package dao.script.execution.tag

import AbstractDaoTest
import dao.batch.execution.BatchExecutionDao
import dao.batch.execution.buildDmBatchExecution
import dao.environment.EnvironmentDao
import dao.environment.buildDmEnvironment
import dao.module.ModuleDao
import dao.module.buildDmModule
import dao.project.ProjectDao
import dao.project.buildDmProject
import dao.script.ScriptDao
import dao.script.buildDmScript
import dao.script.execution.ScriptExecutionDao
import dao.script.execution.buildDmScriptExecution
import dao.tag.TagDao
import dao.tag.buildDmTag
import jooq.generated.domain.tables.pojos.DmScriptExecutionDmTag
import jooq.generated.domain.tables.references.DM_SCRIPT_EXECUTION_DM_TAG
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
            val projectId = ProjectDao(dslContext).insert(buildDmProject())!!.id!!
            val environmentId = EnvironmentDao(dslContext).insert(buildDmEnvironment(fkProjectRef = projectId))!!.id!!
            val moduleId = ModuleDao(dslContext).insert(buildDmModule(fkProjectRef = projectId))!!.id!!
            val scriptChecksum = "myChecksum"
            ScriptDao(dslContext).insert(buildDmScript(checksum = scriptChecksum))

            val batchExecutionRef = BatchExecutionDao(dslContext).insert(buildDmBatchExecution(fkEnvironmentRef = environmentId, fkModuleRef = moduleId))!!.id!!

            scriptExecutionRef = ScriptExecutionDao(dslContext).insert(
                buildDmScriptExecution(
                    fkScriptRef = scriptChecksum,
                    fkBatchExecutionRef = batchExecutionRef
                )
            )!!.id!!

            tagRef = TagDao(dslContext).insert(buildDmTag())!!.name!!
            tagRef2 = TagDao(dslContext).insert(buildDmTag(name = "myName2"))!!.name!!
        }
    }


    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted row`() {
            // Given
            val dmScriptExecutionTag = buildScriptExecutionTag(
                scriptExecutionRef = scriptExecutionRef,
                tagRef = tagRef
            )

            // When
            val insertedScriptExecutionTag = scriptExecutionTagDao.insert(dmScriptExecutionTag)

            // Then
            expectThat(insertedScriptExecutionTag).isNotNull().and {
                get { fkScriptExecutionRef }.isEqualTo(scriptExecutionRef)
                get { fkTagRef }.isEqualTo(tagRef)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val dmScriptExecutionTag = buildScriptExecutionTag(
                scriptExecutionRef = scriptExecutionRef,
                tagRef = tagRef
            )

            // When
            scriptExecutionTagDao.insert(dmScriptExecutionTag)

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
                buildScriptExecutionTag(
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
                buildScriptExecutionTag(
                    scriptExecutionRef = scriptExecutionRef,
                    tagRef = tagRef
                )
            )!!
            val scriptExecutionDmTag2 = scriptExecutionTagDao.insert(
                buildScriptExecutionTag(
                    scriptExecutionRef = scriptExecutionRef,
                    tagRef = tagRef2
                )
            )!!

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