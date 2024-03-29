package api.execution.report.rest.v1

import api.execution.report.client.HelloWorldClient
import api.execution.report.rest.v1.route.ExecutionsRoute
import api.execution.report.rest.v1.route.helloWorld
import io.ktor.server.routing.*

fun Route.routeV1(client: HelloWorldClient) {
        route("/v1") {
            helloWorld(client)
            ExecutionsRoute().route(this)
        }
}