package app.server.app

import GrpcServer
import io.ktor.server.application.*
import rest.configureRouting
import rest.configureSerialization

const val grpcServerPort = 50051
val grpcServer = GrpcServer(
    port = grpcServerPort,
    moduleEnvironmentTokenGrpcServiceImpl = moduleEnvironmentTokenGrpcServiceImpl,
    batchExecutionGrpcServiceImpl = batchExecutionGrpcServiceImpl,
    scriptGrpcServiceImpl = scriptGrpcServiceImpl,
    scriptExecutionGrpcServiceImpl = scriptExecutionGrpcServiceImpl
)

fun Application.module() {
    configureRouting(
        projectService = projectService,
        moduleService = moduleService,
        environmentService = environmentService,
        scriptService = scriptService,
        batchExecutionService = batchExecutionService,
        scriptExecutionService = scriptExecutionService,
        moduleEnvironmentTokenService = moduleEnvironmentTokenService
    )
    configureSerialization()
}