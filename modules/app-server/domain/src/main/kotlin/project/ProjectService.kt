package project

import java.util.*

class ProjectService(
    private val projectDao: ProjectDaoInterface,
    private val projectHierarchyDao: ProjectHierarchyDaoInterface
) {
    fun insert(data: ProjectCreationRequest): Project = projectDao.insert(data)
    fun updateProjectName(id: UUID, data: ProjectNameUpdateRequest): Project? =
        projectDao.updateProjectName(id, data)

    fun delete(id: UUID) = projectDao.delete(id)
    fun findOneById(id: UUID): Project? = projectDao.findOneById(id)
    fun findProjectHierarchies(): List<ProjectHierarchy> = projectHierarchyDao.findProjectHierarchies()
}