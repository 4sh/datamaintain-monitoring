package dao.script

import AbstractDaoTest
import generated.domain.tables.pojos.DmScript
import generated.domain.tables.references.DM_SCRIPT
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.util.UUID

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
            expectThat(insertedScript).isNotNull().and {
                get { name }.isEqualTo(scriptCreationRequest.name)
                get { checksum }.isEqualTo(scriptCreationRequest.checksum)
                get { content }.isEqualTo(scriptCreationRequest.content)
            }
        }

        @Test
        fun `insert should write document in database`() {
            // Given
            val scriptName = "myName"
            val scriptCreationRequest = buildScriptCreationRequest(name = scriptName)

            // When
            val insertedId = scriptDao.insert(scriptCreationRequest)?.checksum

            // Then
            val insertedDmScript = dslContext.select(DM_SCRIPT.NAME, DM_SCRIPT.CHECKSUM, DM_SCRIPT.CONTENT)
                .from(DM_SCRIPT)
                .where(DM_SCRIPT.CHECKSUM.eq(insertedId))
                .fetchOneInto(DmScript::class.java)

            expectThat(insertedDmScript).isNotNull().and {
                get { name }.isEqualTo(scriptName)
                get { checksum }.isEqualTo(scriptCreationRequest.checksum)
                get { content }.isEqualTo(scriptCreationRequest.content)
            }
        }
    }

    @Nested
    inner class TestFindOneById {
        @Test
        fun `should return null when checksum does not exist in db`() {
            // Given
            val checksum = UUID.randomUUID().toString()

            // When
            val loadedScript = scriptDao.findOneById(checksum)

            // Then
            expectThat(loadedScript).isNull()
        }

        @Test
        fun `should load project from db when it exists`() {
            // Given
            val script = buildScriptCreationRequest()
            val insertedChecksum = scriptDao.insert(script)!!.checksum!!

            // When
            val loadedScript = scriptDao.findOneById(insertedChecksum)

            // Then
            expectThat(loadedScript).isNotNull().and {
                get { name }.isEqualTo(script.name)
                get { checksum }.isEqualTo(script.checksum)
                get { content }.isEqualTo(script.content)
            }
        }
    }

    @Nested
    inner class TestUpdate {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val script = buildScriptCreationRequest(name = "myName", content = "myContent")
            scriptDao.insert(script)
            val randomChecksum = UUID.randomUUID().toString()

            // When
            val updatedScript = scriptDao.update(buildDmScript(
                name = "myOtherName",
                checksum = randomChecksum,
                content = "myOtherContent"
            ))

            // Then
            expectThat(updatedScript).isNull()
        }

        @Test
        fun `should not update anything when id does not exist`() {
            // Given
            val script = buildScriptCreationRequest(name = "myName", content = "myContent")
            val insertedChecksum = scriptDao.insert(script)!!.checksum!!
            val randomChecksum = UUID.randomUUID().toString()

            // When
            scriptDao.update(buildDmScript(
                name = "myOtherName",
                content = "myOtherContent",
                checksum = randomChecksum
            ))

            // Then
            expectThat(scriptDao.findOneById(insertedChecksum)).isNotNull().and {
                get { name }.isEqualTo(script.name)
                get { content }.isEqualTo(script.content)
                get { checksum }.isEqualTo(script.checksum)
            }
        }

        @Test
        fun `should update given project`() {
            // Given
            val script = buildScriptCreationRequest(name = "myName", content = "myContent")
            val insertedChecksum = scriptDao.insert(script)!!.checksum!!

            // When
            val newName = "myOtherName"
            val newContent = "myOtherContent"
            scriptDao.update(buildDmScript(checksum = insertedChecksum, name = newName, content = newContent))

            // Then
            expectThat(scriptDao.findOneById(insertedChecksum)).isNotNull().and {
                get { checksum }.isEqualTo(insertedChecksum)
                get { name }.isEqualTo(newName)
                get { content }.isEqualTo(newContent)
            }
        }

        @Test
        fun `should return updated project`() {
            // Given
            val script = buildScriptCreationRequest(name = "myName", content = "myContent")
            val insertedChecksum = scriptDao.insert(script)!!.checksum!!

            // When
            val newName = "myOtherName"
            val newContent = "myOtherContent"
            val updatedScript = scriptDao.update(
                buildDmScript(checksum = insertedChecksum, name = newName, content = newContent)
            )

            // Then
            expectThat(updatedScript).isNotNull().and {
                get { checksum }.isEqualTo(insertedChecksum)
                get { name }.isEqualTo(newName)
                get { content }.isEqualTo(newContent)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing document`() {
            // Given
            val insertedChecksum = scriptDao.insert(buildScriptCreationRequest())!!.checksum!!
            val randomChecksum = UUID.randomUUID().toString()

            // When
            scriptDao.delete(randomChecksum)

            // Then
            expectThat(scriptDao.findOneById(insertedChecksum)).isNotNull()
        }

        @Test
        fun `should delete proper document`() {
            // Given
            val insertedChecksum1 =
                scriptDao.insert(buildScriptCreationRequest(checksum = UUID.randomUUID().toString()))!!.checksum!!
            val insertedChecksum2 =
                scriptDao.insert(buildScriptCreationRequest(checksum = UUID.randomUUID().toString()))!!.checksum!!

            // When
            scriptDao.delete(insertedChecksum1)

            // Then
            expectThat(scriptDao.findOneById(insertedChecksum1)).isNull()
            expectThat(scriptDao.findOneById(insertedChecksum2)).isNotNull()
        }
    }
}