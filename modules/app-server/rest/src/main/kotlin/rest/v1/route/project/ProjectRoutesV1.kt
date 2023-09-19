package rest.v1.route.project

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import project.ProjectCreationRequest
import project.ProjectNameUpdateRequest
import project.ProjectService
import java.util.*

internal const val projectId = "projectId"

internal fun ApplicationCall.projectId() = UUID.fromString(this.parameters[projectId])

internal fun Route.projectV1Routes(projectService: ProjectService) {
    get("/projects/hierarchies") {
        call.respondNullable(projectService.findProjectHierarchies().map { it.toHierarchyDtoV1() })
    }

    get("/projects/{$projectId}") {
        call.respondNullable(projectService.findOneById(call.projectId())?.toDtoV1())
    }

    post("/projects") {
        val projectCreationRequest = call.receive<ProjectCreationRequestDtoV1>()
        call.respond(projectService.insert(projectCreationRequest.toDomain()).toDtoV1())
    }

    put("/projects/{$projectId}/name") {
        val projectNameUpdateRequest =  call.receive<ProjectNameUpdateRequestDtoV1>()
        call.respondNullable(projectService.updateProjectName(call.projectId(), projectNameUpdateRequest.toDomain())?.toDtoV1())
    }
}