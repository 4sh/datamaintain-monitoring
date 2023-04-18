package dao.module

import jooq.generated.domain.tables.pojos.DmModule
import java.util.UUID

fun buildDmModule(
    id: UUID? = null,
    name: String = "myName",
    fkProjectRef: UUID
) = DmModule(id = id, name = name, fkProjectRef = fkProjectRef)