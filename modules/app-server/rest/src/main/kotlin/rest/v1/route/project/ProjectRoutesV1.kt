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
    get("/projects/{$projectId}") {
        call.respondNullable(projectService.findOneById(call.projectId())?.toDtoV1())
    }

    post("/projects") {
        val projectCreationRequest = call.receive<ProjectCreationRequest>()
        call.respond(projectService.insert(projectCreationRequest))
    }

    put("/projects/{$projectId}/name") {
        val projectNameUpdateRequest =  call.receive<ProjectNameUpdateRequest>()
        call.respondNullable(projectService.updateProjectName(call.projectId(), projectNameUpdateRequest))
    }
}