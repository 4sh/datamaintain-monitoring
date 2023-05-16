package dao.tag

import generated.domain.tables.pojos.DmTag
import tag.TagCreationRequest

fun buildDmTag(
    name: String = "myName"
) = DmTag(name = name)

fun buildTagCreationRequest(
    name: String = "myName"
) = TagCreationRequest(name = name)