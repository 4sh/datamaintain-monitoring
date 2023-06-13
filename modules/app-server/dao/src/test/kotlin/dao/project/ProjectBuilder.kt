package dao.project

import project.ProjectCreationRequest
import project.ProjectNameUpdateRequest
fun buildProjectCreationRequest(
    name: String = "myName"
) = ProjectCreationRequest(name)

fun buildProjectNameUpdateRequest(
    name: String = "myName"
) = ProjectNameUpdateRequest(name = name)