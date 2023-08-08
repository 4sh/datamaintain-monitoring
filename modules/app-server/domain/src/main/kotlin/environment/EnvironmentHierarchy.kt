package environment

import java.util.*

data class EnvironmentHierarchy(
    val modules: List<module.Module>,
    override val id: UUID,
    override val name: String,
    override val fkProjectRef: UUID,
    override val smallName: String
) : Environment(
    id,
    name,
    fkProjectRef,
    smallName
)