package rest.v1.route.script.dto

import script.ScriptListItem

@kotlinx.serialization.Serializable
data class ScriptListItemDtoV1(
    val name: String,
    val checksum: String
)

fun ScriptListItem.toDtoV1() = ScriptListItemDtoV1(
    name = this.name,
    checksum = this.checksum
)