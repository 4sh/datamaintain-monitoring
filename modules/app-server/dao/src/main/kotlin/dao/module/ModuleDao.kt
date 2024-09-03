package dao.module

import dao.jooq.generated.domain.tables.references.DM_MODULE
import module.*
import org.jooq.DSLContext
import org.jooq.impl.DSL.defaultValue
import org.jooq.impl.DSL.`val`
import java.util.*

class ModuleDao(val dslContext: DSLContext) : ModuleDaoInterface {
    override fun insert(data: ModuleCreationRequest): Module =
        dslContext.insertInto(DM_MODULE, DM_MODULE.ID, DM_MODULE.NAME, DM_MODULE.FK_PROJECT_REF)
            .values(
                defaultValue(DM_MODULE.ID),
                `val`(data.name),
                `val`(data.fkProjectRef)
            )
            .returning()
            .fetchSingleInto(Module::class.java)

    override fun updateModuleName(id: UUID, updateRequest: ModuleNameUpdateRequest): Module? =
        dslContext.update(DM_MODULE)
            .set(DM_MODULE.NAME, updateRequest.name)
            .where(DM_MODULE.ID.eq(id))
            .returning()
            .fetchOneInto(Module::class.java)

    override fun delete(id: UUID) {
        dslContext.delete(DM_MODULE)
            .where(DM_MODULE.ID.eq(id))
            .execute()
    }

    override fun findOneById(id: UUID): Module? =
        dslContext.fetchOne(DM_MODULE, DM_MODULE.ID.eq(id))
            ?.into(Module::class.java)
}