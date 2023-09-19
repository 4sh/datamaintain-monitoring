package rest.v1.route

import environment.EnvironmentService
import execution.batch.BatchExecutionService
import io.ktor.server.routing.*
import module.ModuleService
import project.ProjectService
import rest.v1.route.environment.environmentV1Routes
import rest.v1.route.execution.executionV1Routes
import rest.v1.route.module.moduleV1Routes
import rest.v1.route.project.projectV1Routes

fun Route.routeV1(
    projectService: ProjectService,
    moduleService: ModuleService,
    environmentService: EnvironmentService,
    batchExecutionService: BatchExecutionService
) {
    route("/v1") {
        projectV1Routes(projectService)
        moduleV1Routes(moduleService)
        environmentV1Routes(environmentService)
        executionV1Routes(batchExecutionService)
    }
}