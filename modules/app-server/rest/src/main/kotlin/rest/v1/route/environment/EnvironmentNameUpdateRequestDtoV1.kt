package rest.v1.route.environment

import environment.EnvironmentNameUpdateRequest

data class EnvironmentNameUpdateRequestDtoV1(val name: String)

fun EnvironmentNameUpdateRequestDtoV1.toDomain() = EnvironmentNameUpdateRequest(
    name = this.name
)
