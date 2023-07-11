package rest.v1.route.environment

import environment.Environment
import kotlinx.serialization.Serializable
import rest.UUIDSerializer
import java.util.*

@Serializable
data class EnvironmentDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String
)

fun Environment.toDtoV1() = EnvironmentDtoV1(
    id = this.id,
    name = this.name
)
