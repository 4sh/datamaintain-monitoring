package api.execution.report.rest.v1

import api.execution.report.client.HelloWorldClient
import api.execution.report.rest.v1.route.ExecutionsRoute
import api.execution.report.rest.v1.route.HelloWorld
import io.ktor.server.routing.*

class RouteV1(private val client: HelloWorldClient) {
    fun routeV1(route :Route) {
        route.route("/v1") {
            HelloWorld(client = client).helloWorld(this)
            ExecutionsRoute().route(this)
        }
    }
}