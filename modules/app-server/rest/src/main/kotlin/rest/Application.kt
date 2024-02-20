package rest

import environment.EnvironmentService
import execution.batch.BatchExecutionService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import module.ModuleService
import project.ProjectService
import rest.v1.route.routeV1
import script.ScriptService
import execution.script.ScriptExecutionService
import moduleEnvironmentToken.ModuleEnvironmentTokenService

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(
    projectService: ProjectService,
    moduleService: ModuleService,
    environmentService: EnvironmentService,
    scriptService: ScriptService,
    batchExecutionService: BatchExecutionService,
    scriptExecutionService: ScriptExecutionService,
    moduleEnvironmentTokenService: ModuleEnvironmentTokenService
) {
    configureSerialization()
    configureRouting(
        projectService = projectService,
        moduleService = moduleService,
        environmentService = environmentService,
        scriptService = scriptService,
        batchExecutionService = batchExecutionService,
        scriptExecutionService = scriptExecutionService,
        moduleEnvironmentTokenService = moduleEnvironmentTokenService
    )
}

fun Application.configureRouting(
    projectService: ProjectService,
    moduleService: ModuleService,
    environmentService: EnvironmentService,
    scriptService: ScriptService,
    batchExecutionService: BatchExecutionService,
    scriptExecutionService: ScriptExecutionService,
    moduleEnvironmentTokenService: ModuleEnvironmentTokenService
) {
    routing {
        route("/api") {
            routeV1(
                projectService = projectService,
                moduleService = moduleService,
                environmentService = environmentService,
                scriptService = scriptService,
                batchExecutionService = batchExecutionService,
                scriptExecutionService = scriptExecutionService,
                moduleEnvironmentTokenService = moduleEnvironmentTokenService
            )
        }
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}