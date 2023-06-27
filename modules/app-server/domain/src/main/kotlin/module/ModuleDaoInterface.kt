package module

import java.util.UUID

interface ModuleDaoInterface {
    fun insert(data: ModuleCreationRequest): Module
    fun updateModuleName(id: UUID, updateRequest: ModuleNameUpdateRequest): Module?
    fun delete(id: UUID)
    fun findOneById(id: UUID): Module?
}