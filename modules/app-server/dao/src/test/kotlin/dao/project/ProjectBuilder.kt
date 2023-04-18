package dao.project

import jooq.generated.domain.tables.pojos.DmProject

fun buildDmProject(name: String = "myName") = DmProject(
    name = name
)