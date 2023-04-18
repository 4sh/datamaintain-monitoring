package dao.batch.execution

import jooq.generated.domain.tables.pojos.DmBatchExecution
import java.util.*

fun buildDmBatchExecution(
    id: UUID? = null,
    fkEnvironmentRef: UUID = UUID.randomUUID(),
    fkModuleRef: UUID = UUID.randomUUID()
) = DmBatchExecution(
    id = id, fkEnvironmentRef = fkEnvironmentRef, fkModuleRef = fkModuleRef
)