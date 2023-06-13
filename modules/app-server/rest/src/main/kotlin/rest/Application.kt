package rest

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import rest.v1.route.routeV1

fun Application.configureRouting() {
    routing {
        routeV1()
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}