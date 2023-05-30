package dao.module

import generated.domain.tables.pojos.DmModule
import module.ModuleCreationRequest
import module.ModuleNameUpdateRequest
import java.util.UUID

fun buildDmModule(
    id: UUID? = null,
    name: String = "myName",
    fkProjectRef: UUID
) = DmModule(id = id, name = name, fkProjectRef = fkProjectRef)

fun buildModuleCreationRequest(
    name: String = "myName",
    fkProjectRef: UUID
) = ModuleCreationRequest(name = name, fkProjectRef = fkProjectRef)

fun buildModuleNameUpdateRequest(
    name: String = "myName"
) = ModuleNameUpdateRequest(name)