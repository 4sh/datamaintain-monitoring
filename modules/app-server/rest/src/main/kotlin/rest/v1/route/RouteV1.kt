package rest.v1.route

import io.ktor.server.routing.*

fun Route.routeV1() {
    route("/v1") {
        helloWorld()
    }
}