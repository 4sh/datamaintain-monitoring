package rest.v1.route.environment

import environment.EnvironmentCreationRequest
import kotlinx.serialization.Serializable
import rest.UUIDSerializer
import java.util.*

@Serializable
data class EnvironmentCreationRequestDtoV1(
    val name: String,
    val smallName: String,
    @Serializable(with = UUIDSerializer::class)
    val projectRef: UUID
) {
    fun toDomain() = EnvironmentCreationRequest(
        name = this.name,
        smallName = this.smallName,
        fkProjectRef = this.projectRef
    )
}

