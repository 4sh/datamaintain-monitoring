package dao.environment

import dao.BaseDao
import jooq.generated.domain.tables.pojos.DmEnvironment
import jooq.generated.domain.tables.references.DM_ENVIRONMENT
import org.jooq.DSLContext
import org.jooq.impl.DSL.defaultValue
import org.jooq.impl.DSL.`val`
import java.util.*

class EnvironmentDao(override val dslContext: DSLContext): BaseDao<DmEnvironment, UUID> {
    override fun insert(data: DmEnvironment): DmEnvironment? =
        dslContext.insertInto(DM_ENVIRONMENT, DM_ENVIRONMENT.ID, DM_ENVIRONMENT.NAME, DM_ENVIRONMENT.FK_PROJECT_REF)
            .values(
                defaultValue(DM_ENVIRONMENT.ID),
                `val`(data.name),
                `val`(data.fkProjectRef)
            ).returningResult(DM_ENVIRONMENT.ID, DM_ENVIRONMENT.NAME, DM_ENVIRONMENT.FK_PROJECT_REF)
            .fetchOne()
            ?.into(DmEnvironment::class.java)

    override fun update(data: DmEnvironment): DmEnvironment? {
        TODO("Not yet implemented")
    }

    override fun delete(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun findOneById(id: UUID): DmEnvironment? =
        dslContext.fetchOne(DM_ENVIRONMENT, DM_ENVIRONMENT.ID.eq(id))
            ?.into(DmEnvironment::class.java)
}