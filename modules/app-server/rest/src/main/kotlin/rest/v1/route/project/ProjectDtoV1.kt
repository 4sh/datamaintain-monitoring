package rest.v1.route.project

import project.Project
import project.ProjectNameUpdateRequest
import rest.UUIDSerializer
import java.util.*

@kotlinx.serialization.Serializable
data class ProjectDtoV1(
    @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val smallName: String
)

fun Project.toDtoV1() = ProjectDtoV1(
    id = id,
    name = name,
    smallName = smallName
)

@kotlinx.serialization.Serializable
data class ProjectNameUpdateRequestDtoV1(
    val name: String,
    val smallName: String
) {
    fun toDomain(): ProjectNameUpdateRequest =
        ProjectNameUpdateRequest(
            name = name,
            smallName = smallName
        )
}