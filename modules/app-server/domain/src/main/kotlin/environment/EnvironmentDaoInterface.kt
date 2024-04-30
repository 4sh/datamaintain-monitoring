package environment

import java.util.*

interface EnvironmentDaoInterface {
    fun insert(data: EnvironmentCreationRequest): Environment
    fun updateEnvironmentName(environmentId: UUID, updateRequest: EnvironmentNameUpdateRequest): Environment?
    fun delete(id: UUID)
    fun findOneById(id: UUID): Environment?
    fun findAllForProject(projectRef: UUID): List<Environment>
}