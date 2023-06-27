package rest.v1.route

import io.ktor.server.routing.*
import module.ModuleService
import project.ProjectService
import rest.v1.route.module.moduleV1Routes
import rest.v1.route.project.projectV1Routes

fun Route.routeV1(
    projectService: ProjectService,
    moduleService: ModuleService
) {
    route("/v1") {
        projectV1Routes(projectService)
        moduleV1Routes(moduleService)
    }
}