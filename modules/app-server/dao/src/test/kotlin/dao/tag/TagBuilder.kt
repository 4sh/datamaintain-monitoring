package dao.tag

import tag.TagCreationRequest

fun buildTagCreationRequest(
    name: String = "myName"
) = TagCreationRequest(name = name)