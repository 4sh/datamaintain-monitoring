package dao.project

import generated.domain.tables.pojos.DmProject
import project.ProjectCreationRequest
import project.ProjectNameUpdateRequest
import java.util.UUID

fun buildDmProject(
    id: UUID? = null,
    name: String = "myName"
) = DmProject(
    id = id,
    name = name
)

fun buildProjectCreationRequest(
    name: String = "myName"
) = ProjectCreationRequest(name)

fun buildProjectNameUpdateRequest(
    name: String = "myName"
) = ProjectNameUpdateRequest(name = name)