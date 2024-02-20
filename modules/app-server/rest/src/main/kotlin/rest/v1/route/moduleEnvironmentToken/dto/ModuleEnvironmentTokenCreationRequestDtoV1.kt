package rest.v1.route.moduleEnvironmentToken.dto

import kotlinx.serialization.Serializable
import moduleEnvironmentToken.ModuleEnvironmentTokenCreationRequest
import rest.UUIDSerializer
import java.util.*

@Serializable
data class ModuleEnvironmentTokenCreationRequestDtoV1(
    @Serializable(UUIDSerializer::class)
    val moduleRef: UUID,
    @Serializable(UUIDSerializer::class)
    val environmentRef: UUID
) {
    fun toDomain() = ModuleEnvironmentTokenCreationRequest(
        moduleRef = moduleRef,
        environmentRef = environmentRef
    )
}