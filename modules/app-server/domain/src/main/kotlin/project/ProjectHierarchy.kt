package project

import environment.EnvironmentHierarchy
import java.util.*

data class ProjectHierarchy(
    val envs: List<EnvironmentHierarchy>,
    override val id: UUID,
    override val name: String
) : Project(id, name)