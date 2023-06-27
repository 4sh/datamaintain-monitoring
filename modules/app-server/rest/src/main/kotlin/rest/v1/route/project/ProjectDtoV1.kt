package rest.v1.route.project

import project.Project
import rest.UUIDSerializer
import java.util.*

@kotlinx.serialization.Serializable
data class ProjectDtoV1(
    @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String
)

fun Project.toDtoV1() = ProjectDtoV1(
    id = id,
    name = name,
)