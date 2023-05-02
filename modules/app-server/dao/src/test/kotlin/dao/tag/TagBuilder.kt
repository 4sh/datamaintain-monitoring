package dao.tag

import jooq.generated.domain.tables.pojos.DmTag

fun buildDmTag(
    name: String = "myName"
) = DmTag(name = name)