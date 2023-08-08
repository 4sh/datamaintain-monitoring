package dao.environment

import environment.EnvironmentCreationRequest
import environment.EnvironmentNameUpdateRequest
import java.util.*

fun buildEnvironmentCreationRequest(
    name: String = "environmentName",
    smallName: String = "en",
    fkProjectRef: UUID
) = EnvironmentCreationRequest(
    name = name,
    fkProjectRef = fkProjectRef,
    smallName = smallName
)

fun buildEnvironmentNameUpdateRequest(
    name: String = "environmentName",
    smallName: String = "en"
) = EnvironmentNameUpdateRequest(
    name = name,
    smallName = smallName
)