package app.server.app

import HelloWorldServer
import io.ktor.server.application.*
import rest.configureRouting
import rest.configureSerialization

const val grpcServerPort = 50051
val helloWorld = HelloWorldServer(grpcServerPort)

fun Application.module() {
    configureRouting(
        projectService = projectService,
        moduleService = moduleService,
        environmentService = environmentService,
        scriptService = scriptService,
        batchExecutionService = batchExecutionService
    )
    configureSerialization()
}