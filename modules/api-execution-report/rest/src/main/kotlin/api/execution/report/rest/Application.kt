package api.execution.report.rest

import api.execution.report.client.HelloWorldClient
import api.execution.report.rest.v1.RouteV1
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.configureRouting(client: HelloWorldClient) {
    routing {
        route("/api") {
            RouteV1(client).routeV1(this)
        }
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}