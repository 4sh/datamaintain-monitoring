package dao.script

import dao.BaseDao
import jooq.generated.domain.tables.pojos.DmScript
import jooq.generated.domain.tables.references.DM_SCRIPT
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`

class ScriptDao(override val dslContext: DSLContext) : BaseDao<DmScript, String> {
    override fun insert(data: DmScript): DmScript? =
        dslContext.insertInto(DM_SCRIPT, DM_SCRIPT.NAME, DM_SCRIPT.CHECKSUM, DM_SCRIPT.CONTENT)
            .values(
                `val`(data.name),
                `val`(data.checksum),
                `val`(data.content)
            ).returningResult(DM_SCRIPT.CHECKSUM, DM_SCRIPT.NAME, DM_SCRIPT.CONTENT)
            .fetchOne()
            ?.into(DmScript::class.java)

    override fun update(data: DmScript): DmScript? =
        dslContext.update(DM_SCRIPT)
            .set(DM_SCRIPT.NAME, data.name)
            .set(DM_SCRIPT.CONTENT, data.content)
            .where(DM_SCRIPT.CHECKSUM.eq(data.checksum))
            .returningResult(DM_SCRIPT.NAME, DM_SCRIPT.CHECKSUM, DM_SCRIPT.CONTENT)
            .fetchOne()
            ?.into(DmScript::class.java)

    override fun delete(id: String) {
        dslContext.delete(DM_SCRIPT)
            .where(DM_SCRIPT.CHECKSUM.eq(id))
            .execute()
    }

    override fun findOneById(id: String): DmScript? =
        dslContext.fetchOne(DM_SCRIPT, DM_SCRIPT.CHECKSUM.eq(id))
            ?.into(DmScript::class.java)
}