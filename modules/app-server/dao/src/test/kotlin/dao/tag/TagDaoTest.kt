package dao.tag

import AbstractDaoTest
import jooq.generated.domain.tables.pojos.DmTag
import jooq.generated.domain.tables.references.DM_TAG
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.util.UUID

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
            val dmTag: DmTag = buildDmTag()

            // When
            val insertedTag = tagDao.insert(dmTag)

            // Then
            expectThat(insertedTag).isNotNull().and {
                get { name }.isEqualTo(dmTag.name)
            }
        }

        @Test
        fun `insert should write row in database`() {
            // Given
            val tagName = "myTag"
            val dmTag = buildDmTag(name = tagName)

            // When
            val insertedId = tagDao.insert(dmTag)?.name

            // Then
            val insertedDmTag = dslContext.select(DM_TAG.NAME)
                .from(DM_TAG)
                .where(DM_TAG.NAME.eq(insertedId))
                .fetchOneInto(DmTag::class.java)

            expectThat(insertedDmTag).isNotNull().and {
                get { name }.isEqualTo(insertedId)
            }
        }

        @Test
        fun `insert should return null when inserting already existing row`() {
            // Given
            tagDao.insert(buildDmTag())!!

            // When
            val insertedTag = tagDao.insert(buildDmTag())

            // Then
            expectThat(insertedTag).isNull()
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
            val tag = buildDmTag()
            val insertedId = tagDao.insert(tag)!!.name!!

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
            val insertedId = tagDao.insert(buildDmTag())!!.name!!
            val randomId = UUID.randomUUID().toString()

            // When
            tagDao.delete(randomId)

            // Then
            expectThat(tagDao.findOneById(insertedId)).isNotNull()
        }

        @Test
        fun `should delete proper row`() {
            // Given
            val insertedId1 = tagDao.insert(buildDmTag(name = "myName1"))!!.name!!
            val insertedId2 = tagDao.insert(buildDmTag(name = "myName2"))!!.name!!

            // When
            tagDao.delete(insertedId1)

            // Then
            expectThat(tagDao.findOneById(insertedId1)).isNull()
            expectThat(tagDao.findOneById(insertedId2)).isNotNull()
        }
    }
}