package dao.script

import jooq.generated.domain.tables.pojos.DmScript

fun buildDmScript(
    name: String = "myName",
    checksum: String = "checksum",
    content: String = "content"
) = DmScript(name = name, checksum = checksum, content = content)