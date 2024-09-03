package dao.tag

import dao.jooq.generated.domain.tables.references.DM_TAG
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import tag.Tag
import tag.TagCreationRequest

class TagDao(val dslContext: DSLContext) {
    fun insert(data: TagCreationRequest): Tag =
        dslContext.insertInto(DM_TAG, DM_TAG.NAME)
            .values(
                `val`(data.name)
            )
            .returning()
            .fetchSingleInto(Tag::class.java)

    fun delete(id: String) {
        dslContext.delete(DM_TAG)
            .where(DM_TAG.NAME.eq(id))
            .execute()
    }

    fun findOneById(id: String): Tag? =
        dslContext.fetchOne(DM_TAG, DM_TAG.NAME.eq(id))
            ?.into(Tag::class.java)
}