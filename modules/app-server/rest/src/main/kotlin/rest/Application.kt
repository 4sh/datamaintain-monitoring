package rest

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import project.ProjectService
import rest.v1.route.routeV1

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(
    projectService: ProjectService
) {
    configureSerialization()
    configureRouting(projectService)
}

fun Application.configureRouting(projectService: ProjectService) {
    routing {
        routeV1(projectService)
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}