package environment

import java.util.*

class EnvironmentService(private val environmentDaoInterface: EnvironmentDaoInterface) {
    fun insert(data: EnvironmentCreationRequest): Environment = environmentDaoInterface.insert(data)

    fun updateEnvironmentName(environmentId: UUID, updateRequest: EnvironmentNameUpdateRequest): Environment? =
        environmentDaoInterface.updateEnvironmentName(environmentId, updateRequest)

    fun delete(id: UUID) = environmentDaoInterface.delete(id)

    fun findOneById(id: UUID): Environment? = environmentDaoInterface.findOneById(id)
}