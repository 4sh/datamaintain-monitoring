package rest.v1.route.module

import kotlinx.serialization.Serializable
import module.ModuleCreationRequest
import rest.serializer.UUIDSerializer
import java.util.*

@Serializable
data class ModuleCreationRequestDtoV1(
    val name: String,
    @Serializable(with = UUIDSerializer::class)
    val fkProjectRef: UUID
)

fun ModuleCreationRequestDtoV1.toDomain(): ModuleCreationRequest = ModuleCreationRequest(
    name = this.name,
    fkProjectRef = this.fkProjectRef
)
