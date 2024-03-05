package api.execution.report.rest.v1

import api.execution.report.rest.v1.route.executionsV1Routes
import io.ktor.server.routing.*

fun Route.routeV1() {
        route("/v1") {
            executionsV1Routes()
        }
}