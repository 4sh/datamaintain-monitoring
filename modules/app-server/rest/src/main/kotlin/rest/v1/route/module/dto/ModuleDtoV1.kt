package rest.v1.route.module.dto

import kotlinx.serialization.Serializable
import module.Module
import rest.UUIDSerializer
import java.util.UUID

@Serializable
data class ModuleDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String
)

fun Module.toDtoV1() = ModuleDtoV1(
    id = this.id,
    name = this.name
)
