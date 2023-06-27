package rest

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import module.ModuleService
import project.ProjectService
import rest.v1.route.routeV1

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(
    projectService: ProjectService,
    moduleService: ModuleService
) {
    configureSerialization()
    configureRouting(
        projectService = projectService,
        moduleService = moduleService
    )
}

fun Application.configureRouting(
    projectService: ProjectService,
    moduleService: ModuleService
) {
    routing {
        routeV1(
            projectService = projectService,
            moduleService = moduleService
        )
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}