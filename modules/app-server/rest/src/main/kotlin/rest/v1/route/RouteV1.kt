package rest.v1.route

import environment.EnvironmentService
import execution.batch.BatchExecutionService
import execution.script.ScriptExecutionService
import io.ktor.server.routing.*
import module.ModuleService
import moduleEnvironmentToken.ModuleEnvironmentTokenService
import project.ProjectService
import rest.v1.route.environment.environmentV1Routes
import rest.v1.route.execution.batch.batchExecutionV1Routes
import rest.v1.route.execution.script.scriptExecutionV1Routes
import rest.v1.route.module.moduleV1Routes
import rest.v1.route.moduleEnvironmentToken.moduleEnvironmentTokenV1Routes
import rest.v1.route.project.projectV1Routes
import rest.v1.route.script.scriptV1Routes
import script.ScriptService

fun Route.routeV1(
    projectService: ProjectService,
    moduleService: ModuleService,
    environmentService: EnvironmentService,
    scriptService: ScriptService,
    batchExecutionService: BatchExecutionService,
    scriptExecutionService: ScriptExecutionService,
    moduleEnvironmentTokenService: ModuleEnvironmentTokenService
) {
    route("/v1") {
        projectV1Routes(projectService)
        moduleV1Routes(moduleService)
        environmentV1Routes(environmentService)
        scriptV1Routes(scriptService)
        batchExecutionV1Routes(batchExecutionService)
        scriptExecutionV1Routes(scriptExecutionService)
        moduleEnvironmentTokenV1Routes(moduleEnvironmentTokenService)
    }
}