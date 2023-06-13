package dao.script

import generated.domain.tables.pojos.DmScript
import generated.domain.tables.references.DM_SCRIPT
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import script.ScriptCreationRequest

class ScriptDao(val dslContext: DSLContext) {
    fun insert(data: ScriptCreationRequest): DmScript =
        dslContext.insertInto(DM_SCRIPT, DM_SCRIPT.NAME, DM_SCRIPT.CHECKSUM, DM_SCRIPT.CONTENT)
            .values(
                `val`(data.name),
                `val`(data.checksum),
                `val`(data.content)
            ).returningResult(DM_SCRIPT.CHECKSUM, DM_SCRIPT.NAME, DM_SCRIPT.CONTENT)
            .fetchSingleInto(DmScript::class.java)

    fun delete(id: String) {
        dslContext.delete(DM_SCRIPT)
            .where(DM_SCRIPT.CHECKSUM.eq(id))
            .execute()
    }

    fun findOneById(id: String): DmScript? =
        dslContext.fetchOne(DM_SCRIPT, DM_SCRIPT.CHECKSUM.eq(id))
            ?.into(DmScript::class.java)
}