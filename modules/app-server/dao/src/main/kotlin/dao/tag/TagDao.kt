package dao.tag

import generated.domain.tables.pojos.DmTag
import generated.domain.tables.references.DM_TAG

import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import tag.TagCreationRequest

class TagDao(val dslContext: DSLContext) {
    fun insert(data: TagCreationRequest): DmTag =
        dslContext.insertInto(DM_TAG, DM_TAG.NAME)
            .values(
                `val`(data.name)
            )
            .returningResult(DM_TAG.NAME)
            .fetchSingleInto(DmTag::class.java)

    fun delete(id: String) {
        dslContext.delete(DM_TAG)
            .where(DM_TAG.NAME.eq(id))
            .execute()
    }

    fun findOneById(id: String): DmTag? =
        dslContext.fetchOne(DM_TAG, DM_TAG.NAME.eq(id))
            ?.into(DmTag::class.java)
}