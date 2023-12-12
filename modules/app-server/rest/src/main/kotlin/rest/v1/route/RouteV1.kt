package rest.v1.route

import environment.EnvironmentService
import execution.batch.BatchExecutionService
import io.ktor.server.routing.*
import module.ModuleService
import project.ProjectService
import rest.v1.route.environment.environmentV1Routes
import rest.v1.route.execution.batch.batchExecutionV1Routes
import rest.v1.route.module.moduleV1Routes
import rest.v1.route.project.projectV1Routes
import rest.v1.route.script.scriptV1Routes
import script.ScriptService

fun Route.routeV1(
    projectService: ProjectService,
    moduleService: ModuleService,
    environmentService: EnvironmentService,
    scriptService: ScriptService,
    batchExecutionService: BatchExecutionService
) {
    route("/v1") {
        projectV1Routes(projectService)
        moduleV1Routes(moduleService)
        environmentV1Routes(environmentService)
        scriptV1Routes(scriptService)
        batchExecutionV1Routes(batchExecutionService)
    }
}