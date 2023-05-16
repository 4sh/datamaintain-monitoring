package dao.script

import jooq.generated.domain.tables.pojos.DmScript
import jooq.generated.domain.tables.references.DM_SCRIPT
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`

class ScriptDao(val dslContext: DSLContext) {
    fun insert(data: DmScript): DmScript? =
        dslContext.insertInto(DM_SCRIPT, DM_SCRIPT.NAME, DM_SCRIPT.CHECKSUM, DM_SCRIPT.CONTENT)
            .values(
                `val`(data.name),
                `val`(data.checksum),
                `val`(data.content)
            ).returningResult(DM_SCRIPT.CHECKSUM, DM_SCRIPT.NAME, DM_SCRIPT.CONTENT)
            .fetchOne()
            ?.into(DmScript::class.java)

    fun update(data: DmScript): DmScript? =
        dslContext.update(DM_SCRIPT)
            .set(DM_SCRIPT.NAME, data.name)
            .set(DM_SCRIPT.CONTENT, data.content)
            .where(DM_SCRIPT.CHECKSUM.eq(data.checksum))
            .returningResult(DM_SCRIPT.NAME, DM_SCRIPT.CHECKSUM, DM_SCRIPT.CONTENT)
            .fetchOne()
            ?.into(DmScript::class.java)

    fun delete(id: String) {
        dslContext.delete(DM_SCRIPT)
            .where(DM_SCRIPT.CHECKSUM.eq(id))
            .execute()
    }

    fun findOneById(id: String): DmScript? =
        dslContext.fetchOne(DM_SCRIPT, DM_SCRIPT.CHECKSUM.eq(id))
            ?.into(DmScript::class.java)
}