package dao.script

import generated.domain.tables.references.DM_SCRIPT
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import script.Script
import script.ScriptCreationRequest
import script.ScriptDaoInterface

class ScriptDao(val dslContext: DSLContext): ScriptDaoInterface {
    override fun insert(data: ScriptCreationRequest): Script =
        dslContext.insertInto(DM_SCRIPT, DM_SCRIPT.NAME, DM_SCRIPT.CHECKSUM, DM_SCRIPT.CONTENT)
            .values(
                `val`(data.name),
                `val`(data.checksum),
                `val`(data.content)
            ).returningResult(DM_SCRIPT.CHECKSUM, DM_SCRIPT.NAME, DM_SCRIPT.CONTENT)
            .fetchSingleInto(Script::class.java)

    override fun delete(checksum: String) {
        dslContext.delete(DM_SCRIPT)
            .where(DM_SCRIPT.CHECKSUM.eq(checksum))
            .execute()
    }

    override fun findOneByChecksum(checksum: String): Script? =
        dslContext.fetchOne(DM_SCRIPT, DM_SCRIPT.CHECKSUM.eq(checksum))
            ?.into(Script::class.java)
}