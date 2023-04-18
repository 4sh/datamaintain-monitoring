package dao.batch.execution

import dao.BaseDao
import jooq.generated.domain.tables.pojos.DmBatchExecution
import org.jooq.DSLContext
import java.util.UUID

class BatchExecutionDao(private val dslContext: DSLContext) {
    fun insert(data: DmBatchExecution): DmBatchExecution? {
        TODO("Not yet implemented")
    }

    fun delete(id: UUID) {
        TODO("Not yet implemented")
    }
}