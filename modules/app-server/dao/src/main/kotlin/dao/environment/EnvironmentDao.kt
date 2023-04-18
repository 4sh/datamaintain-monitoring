package dao.environment

import dao.BaseDao
import jooq.generated.domain.tables.pojos.DmEnvironment
import org.jooq.DSLContext
import java.util.*

class EnvironmentDao(override val dslContext: DSLContext): BaseDao<DmEnvironment, UUID> {
    override fun insert(data: DmEnvironment): DmEnvironment? {
        TODO("Not yet implemented")
    }

    override fun update(data: DmEnvironment): DmEnvironment? {
        TODO("Not yet implemented")
    }

    override fun delete(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun findOneById(id: UUID): DmEnvironment? {
        TODO("Not yet implemented")
    }
}