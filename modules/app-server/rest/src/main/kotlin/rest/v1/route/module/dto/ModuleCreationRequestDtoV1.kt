package rest.v1.route.module.dto

import kotlinx.serialization.Serializable
import module.ModuleCreationRequest
import rest.UUIDSerializer
import java.util.*

@Serializable
data class ModuleCreationRequestDtoV1(
    val name: String,
    @Serializable(with = UUIDSerializer::class)
    val projectRef: UUID
)

fun ModuleCreationRequestDtoV1.toDomain(): ModuleCreationRequest = ModuleCreationRequest(
    name = this.name,
    fkProjectRef = this.projectRef
)
