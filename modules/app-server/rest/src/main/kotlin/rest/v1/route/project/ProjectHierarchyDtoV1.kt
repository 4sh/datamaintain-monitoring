package rest.v1.route.project

import kotlinx.serialization.Serializable
import project.ProjectHierarchy
import rest.UUIDSerializer
import java.util.*

@Serializable
data class ProjectHierarchyDtoV1(
    val envs: List<EnvironmentHierarchyDtoV1>,
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val smallName: String
)

@Serializable
data class EnvironmentHierarchyDtoV1(
    val modules: List<EnvironmentHierarchyModuleDtoV1>,
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val smallName: String
)

@Serializable
data class EnvironmentHierarchyModuleDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String
)

fun ProjectHierarchy.toHierarchyDtoV1() = ProjectHierarchyDtoV1(
    envs = envs.map { env ->
        EnvironmentHierarchyDtoV1(
            modules = env.modules.map {
                EnvironmentHierarchyModuleDtoV1(
                    id = it.id,
                    name = it.name
                )
            },
            id = env.id,
            name = env.name,
            smallName = env.smallName
        )
    },
    id = id,
    name = name,
    smallName = smallName
)