package dao.module

import generated.domain.tables.pojos.DmModule
import generated.domain.tables.references.DM_MODULE
import org.jooq.DSLContext
import org.jooq.impl.DSL.defaultValue
import org.jooq.impl.DSL.`val`
import java.util.UUID

class ModuleDao(val dslContext: DSLContext) {
    fun insert(data: DmModule): DmModule? =
        dslContext.insertInto(DM_MODULE, DM_MODULE.ID, DM_MODULE.NAME, DM_MODULE.FK_PROJECT_REF)
            .values(
                defaultValue(DM_MODULE.ID),
                `val`(data.name),
                `val`(data.fkProjectRef)
            ).returningResult(DM_MODULE.ID, DM_MODULE.NAME, DM_MODULE.FK_PROJECT_REF)
            .fetchOne()
            ?.into(DmModule::class.java)

    fun update(data: DmModule): DmModule? =
        dslContext.update(DM_MODULE)
            .set(DM_MODULE.NAME, data.name)
            .where(DM_MODULE.ID.eq(data.id))
            .returningResult(DM_MODULE.ID, DM_MODULE.NAME, DM_MODULE.FK_PROJECT_REF)
            .fetchOne()
            ?.into(DmModule::class.java)

    fun delete(id: UUID) {
        dslContext.delete(DM_MODULE)
            .where(DM_MODULE.ID.eq(id))
            .execute()
    }

    fun findOneById(id: UUID): DmModule? =
        dslContext.fetchOne(DM_MODULE, DM_MODULE.ID.eq(id))
            ?.into(DmModule::class.java)
}