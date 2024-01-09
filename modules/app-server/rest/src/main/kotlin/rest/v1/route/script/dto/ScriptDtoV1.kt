package rest.v1.route.script.dto

import script.Script

@kotlinx.serialization.Serializable
data class ScriptDtoV1(
    val name: String,
    val checksum: String,
    val content: String
)

fun Script.toDtoV1() = ScriptDtoV1(
    name = this.name,
    checksum = this.checksum,
    content = this.content
)