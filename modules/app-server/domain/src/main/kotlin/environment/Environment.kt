package environment

import java.util.*

data class Environment(
    val id: UUID,
    val name: String,
    val fkProjectRef: UUID
)