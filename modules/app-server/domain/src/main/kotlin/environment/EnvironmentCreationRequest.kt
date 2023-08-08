package environment

import java.util.*

data class EnvironmentCreationRequest(
    val name: String,
    val fkProjectRef: UUID,
    val smallName: String
)