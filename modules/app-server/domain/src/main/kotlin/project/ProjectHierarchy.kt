package project

import environment.EnvironmentHierarchy
import java.util.*

data class ProjectHierarchy(
    val envs: List<EnvironmentHierarchy>,
    override val id: UUID,
    override val name: String,
    override val smallName: String
) : Project(id, name, smallName)
