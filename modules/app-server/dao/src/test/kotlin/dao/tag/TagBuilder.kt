package dao.tag

import generated.domain.tables.pojos.DmTag

fun buildDmTag(
    name: String = "myName"
) = DmTag(name = name)