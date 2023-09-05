package project

import java.util.*

interface ProjectDaoInterface {
    fun insert(data: ProjectCreationRequest): Project
    fun updateProjectName(id: UUID, updateRequest: ProjectNameUpdateRequest): Project?
    fun delete(id: UUID)
    fun findOneById(id: UUID): Project?
}