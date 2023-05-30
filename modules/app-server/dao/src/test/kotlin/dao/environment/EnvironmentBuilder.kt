package dao.environment

import environment.EnvironmentCreationRequest
import environment.EnvironmentNameUpdateRequest
import generated.domain.tables.pojos.DmEnvironment
import java.util.UUID

fun buildDmEnvironment(
    id: UUID? = null,
    name: String = "environmentName",
    fkProjectRef: UUID
) = DmEnvironment(
    id = id,
    name = name,
    fkProjectRef = fkProjectRef
)

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