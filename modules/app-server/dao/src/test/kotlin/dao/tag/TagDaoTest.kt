package dao.tag

import AbstractDaoTest
import dao.jooq.generated.domain.tables.references.DM_TAG
import isDuplicatedKeyException
import org.jooq.exception.IntegrityConstraintViolationException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.*
import tag.Tag
import java.util.*
import kotlin.text.get

internal class TagDaoTest : AbstractDaoTest() {
    private val tagDao = TagDao(dslContext)

    override fun dropTables() {
        dslContext.delete(DM_TAG).execute()
    }

    @Nested
    inner class TestInsert {
        @Test
        fun `insert should return inserted row`() {
            // Given
            val tagCreationRequest = buildTagCreationRequest()

            // When
            val insertedTag = tagDao.insert(tagCreationRequest)

            // Then
            expectThat(insertedTag).and {
                get { name }.isEqualTo(tagCreationRequest.name)
            }
        }

        @Test
        fun `insert should write row in database`() {
            // Given
            val tagName = "myTag"
            val tagCreationRequest = buildTagCreationRequest(name = tagName)

            // When
            val insertedId = tagDao.insert(tagCreationRequest).name

            // Then
            val insertedDmTag = dslContext.select(DM_TAG.NAME)
                .from(DM_TAG)
                .where(DM_TAG.NAME.eq(insertedId))
                .fetchOneInto(Tag::class.java)

            expectThat(insertedDmTag).isNotNull().and {
                get { name }.isEqualTo(insertedId)
            }
        }

        @Test
        fun `insert should throw exception when inserting already existing row`() {
            // Given
            tagDao.insert(buildTagCreationRequest())

            // When / Then
            expectThrows<IntegrityConstraintViolationException> { tagDao.insert(buildTagCreationRequest()) }
                .and { get { isDuplicatedKeyException() }.isTrue() }
        }
    }

    @Nested
    inner class TestFindOneById {
        @Test
        fun `should return null when id does not exist in db`() {
            // Given
            val id = UUID.randomUUID().toString()

            // When
            val loadedTag = tagDao.findOneById(id)

            // Then
            expectThat(loadedTag).isNull()
        }

        @Test
        fun `should load tag from db when it exists`() {
            // Given
            val tag = buildTagCreationRequest()
            val insertedId = tagDao.insert(tag).name

            // When
            val loadedTAg = tagDao.findOneById(insertedId)

            // Then
            expectThat(loadedTAg).isNotNull().and {
                get { name }.isEqualTo(insertedId)
            }
        }
    }

    @Nested
    inner class TestDelete {
        @Test
        fun `should do nothing when deleting non existing row`() {
            // Given
            val insertedId = tagDao.insert(buildTagCreationRequest()).name
            val randomId = UUID.randomUUID().toString()

            // When
            tagDao.delete(randomId)

            // Then
            expectThat(tagDao.findOneById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper row`() {
            // Given
            val insertedId1 = tagDao.insert(buildTagCreationRequest(name = "myName1")).name
            val insertedId2 = tagDao.insert(buildTagCreationRequest(name = "myName2")).name

            // When
            tagDao.delete(insertedId1)

            // Then
            expectThat(tagDao.findOneById(insertedId1)).isNull()
            expectThat(tagDao.findOneById(insertedId2)).isNotNull()
        }
    }
}