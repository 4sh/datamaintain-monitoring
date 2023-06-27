package rest.v1.route.module

import kotlinx.serialization.Serializable
import module.ModuleNameUpdateRequest

@Serializable
data class ModuleNameUpdateRequestDtoV1(val name: String)

fun ModuleNameUpdateRequestDtoV1.toDomain(): ModuleNameUpdateRequest = ModuleNameUpdateRequest(
    name = this.name
)
