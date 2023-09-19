package rest.v1.route.environment

import environment.EnvironmentNameUpdateRequest

@kotlinx.serialization.Serializable
data class EnvironmentNameUpdateRequestDtoV1(val name: String, val smallName: String)

fun EnvironmentNameUpdateRequestDtoV1.toDomain() = EnvironmentNameUpdateRequest(
    name = this.name,
    smallName = this.smallName
)
