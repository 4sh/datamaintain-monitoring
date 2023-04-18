package dao.batch.execution

import jooq.generated.domain.tables.pojos.DmBatchExecution
import java.util.*

fun buildDmBatchExecution(
    id: UUID? = null,
    fkEnvironmentRef: UUID,
    fkModuleRef: UUID
) = DmBatchExecution(
    id = id, fkEnvironmentRef = fkEnvironmentRef, fkModuleRef = fkModuleRef
)