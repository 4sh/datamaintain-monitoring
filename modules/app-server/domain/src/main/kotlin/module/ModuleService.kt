package module

import java.util.UUID

class ModuleService(private val moduleDao: ModuleDaoInterface) {
    fun insert(data: ModuleCreationRequest): Module = moduleDao.insert(data)

    fun updateModuleName(id: UUID, data: ModuleNameUpdateRequest): Module? =
        moduleDao.updateModuleName(id, data)

    fun delete(id: UUID) = moduleDao.delete(id)

    fun findOneById(id: UUID): Module? = moduleDao.findOneById(id)
}