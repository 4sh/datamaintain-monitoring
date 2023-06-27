package rest.v1.route

import io.ktor.server.routing.*
import project.ProjectService
import rest.v1.route.project.projectV1Routes

fun Route.routeV1(projectService: ProjectService) {
    route("/v1") {
        projectV1Routes(projectService)
    }
}