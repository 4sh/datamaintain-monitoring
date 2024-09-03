package dao.execution.script.tag

import dao.jooq.generated.domain.tables.pojos.DmScriptExecutionDmTag
import dao.jooq.generated.domain.tables.references.DM_SCRIPT_EXECUTION_DM_TAG
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import execution.script.tag.ScriptExecutionTagCreationRequest
import java.util.*

class ScriptExecutionTagDao(val dslContext: DSLContext) {
    fun insert(data: ScriptExecutionTagCreationRequest): DmScriptExecutionDmTag =
        dslContext.insertInto(
            DM_SCRIPT_EXECUTION_DM_TAG,
            DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF,
            DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF
        )
            .values(
                `val`(data.fkScriptExecutionRef),
                `val`(data.fkTagRef)
            )
            .returning()
            .fetchSingleInto(DmScriptExecutionDmTag::class.java)

    fun delete(scriptExecutionRef: UUID, tagRef: String) {
        dslContext.delete(DM_SCRIPT_EXECUTION_DM_TAG)
            .where(DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF.eq(scriptExecutionRef))
            .and(DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF.eq(tagRef))
            .execute()
    }
}