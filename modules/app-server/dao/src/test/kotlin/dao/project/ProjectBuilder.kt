package dao.project

import project.ProjectCreationRequest
import project.ProjectNameUpdateRequest
fun buildProjectCreationRequest(
    name: String = "myName",
    smallName: String = "mySmallName"
) = ProjectCreationRequest(name, smallName)

fun buildProjectNameUpdateRequest(
    name: String = "myName",
    smallName: String = "mySmallName"
) = ProjectNameUpdateRequest(name = name, smallName = smallName)