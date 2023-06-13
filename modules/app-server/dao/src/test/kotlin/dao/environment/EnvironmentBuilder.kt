package dao.environment

import environment.EnvironmentCreationRequest
import environment.EnvironmentNameUpdateRequest
import java.util.*

fun buildEnvironmentCreationRequest(
    name: String = "environmentName",
    fkProjectRef: UUID
) = EnvironmentCreationRequest(
    name = name,
    fkProjectRef = fkProjectRef
)

fun buildEnvironmentNameUpdateRequest(
    name: String = "environmentName"
) = EnvironmentNameUpdateRequest(
    name = name
)