package dao.script

import AbstractDaoTest
import dao.jooq.generated.domain.tables.pojos.DmScript
import dao.jooq.generated.domain.tables.references.DM_SCRIPT
import isDuplicatedKeyException
import org.jooq.exception.IntegrityConstraintViolationException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.*
import java.util.*
import kotlin.text.get

class ScriptDaoTest : AbstractDaoTest() {
    private val scriptDao = ScriptDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_SCRIPT).execute()
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted document`() {
            // Given
            val scriptCreationRequest = buildScriptCreationRequest()

            // When
            val insertedScript = scriptDao.insert(scriptCreationRequest)

            // Then
            expectThat(insertedScript).and {
                get { name }.isEqualTo(scriptCreationRequest.name)
                get { checksum }.isEqualTo(scriptCreationRequest.checksum)
                get { content }.isEqualTo(scriptCreationRequest.content)
            }
        }

        @Test
        fun `insert should write document name in database`() {
            // Given
            val scriptName = "myName"
            val scriptCreationRequest = buildScriptCreationRequest(name = scriptName)

            // When
            val insertedId = scriptDao.insert(scriptCreationRequest).checksum

            // Then
            val insertedDmScript = dslContext.select(DM_SCRIPT.NAME, DM_SCRIPT.CHECKSUM, DM_SCRIPT.CONTENT)
                .from(DM_SCRIPT)
                .where(DM_SCRIPT.CHECKSUM.eq(insertedId))
                .fetchOneInto(DmScript::class.java)

            expectThat(insertedDmScript).isNotNull().and {
                get { name }.isEqualTo(scriptName)
            }
        }

        @Test
        fun `insert should throw exception when inserting already existing row`() {
            // Given
            val checksum = "myChecksum"
            scriptDao.insert(buildScriptCreationRequest(checksum = checksum))

            // When / Then
            expectThrows<IntegrityConstraintViolationException> { scriptDao.insert(buildScriptCreationRequest( checksum = checksum)) }
                .and { get { isDuplicatedKeyException() }.isTrue() }
        }
    }

    @Nested
    inner class TestFindOneById {
        @Test
        fun `should return null when checksum does not exist in db`() {
            // Given
            val checksum = UUID.randomUUID().toString()

            // When
            val loadedScript = scriptDao.findOneByChecksum(checksum)

            // Then
            expectThat(loadedScript).isNull()
        }

        @Test
        fun `should load project from db when it exists`() {
            // Given
            val script = buildScriptCreationRequest()
            val insertedChecksum = scriptDao.insert(script).checksum

            // When
            val loadedScript = scriptDao.findOneByChecksum(insertedChecksum)

            // Then
            expectThat(loadedScript).isNotNull().and {
                get { name }.isEqualTo(script.name)
                get { checksum }.isEqualTo(script.checksum)
                get { content }.isEqualTo(script.content)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing document`() {
            // Given
            val insertedChecksum = scriptDao.insert(buildScriptCreationRequest()).checksum
            val randomChecksum = UUID.randomUUID().toString()

            // When
            scriptDao.delete(randomChecksum)

            // Then
            expectThat(scriptDao.findOneByChecksum(insertedChecksum)).isNotNull()
        }

        @Test
        fun `should delete proper document`() {
            // Given
            val insertedChecksum1 =
                scriptDao.insert(buildScriptCreationRequest(checksum = UUID.randomUUID().toString())).checksum
            val insertedChecksum2 =
                scriptDao.insert(buildScriptCreationRequest(checksum = UUID.randomUUID().toString())).checksum

            // When
            scriptDao.delete(insertedChecksum1)

            // Then
            expectThat(scriptDao.findOneByChecksum(insertedChecksum1)).isNull()
            expectThat(scriptDao.findOneByChecksum(insertedChecksum2)).isNotNull()
        }
    }
}