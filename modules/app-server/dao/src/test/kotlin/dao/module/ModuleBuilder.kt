package dao.module

import module.ModuleCreationRequest
import module.ModuleNameUpdateRequest
import java.util.*

fun buildModuleCreationRequest(
    name: String = "myName",
    fkProjectRef: UUID
) = ModuleCreationRequest(name = name, fkProjectRef = fkProjectRef)

fun buildModuleNameUpdateRequest(
    name: String = "myName"
) = ModuleNameUpdateRequest(name)