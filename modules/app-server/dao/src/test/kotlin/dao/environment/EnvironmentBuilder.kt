package dao.environment

import jooq.generated.domain.tables.pojos.DmEnvironment
import java.util.UUID

fun buildDmEnvironment(
    id: UUID? = null,
    name: String = "environmentName",
    fkProjectRef: UUID
) = DmEnvironment(
    id = id,
    name = name,
    fkProjectRef = fkProjectRef
)