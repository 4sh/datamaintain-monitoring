package dao.project

import jooq.generated.domain.tables.pojos.DmProject
import java.util.UUID

fun buildDmProject(
    id: UUID? = null,
    name: String = "myName"
) = DmProject(
    id = id,
    name = name
)