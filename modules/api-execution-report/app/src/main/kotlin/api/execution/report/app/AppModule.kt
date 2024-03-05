package api.execution.report.app

import api.execution.report.rest.configureRouting
import api.execution.report.rest.configureSerialization
import io.grpc.ManagedChannelBuilder
import io.ktor.server.application.*

val port = 50051

val channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build()

fun Application.module() {
    configureSerialization()
    configureRouting()
}