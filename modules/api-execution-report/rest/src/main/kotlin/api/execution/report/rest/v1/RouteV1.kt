package api.execution.report.rest.v1

import api.execution.report.domain.module.batch.execution.BatchExecutionService
import api.execution.report.rest.v1.route.executionsV1Routes
import io.ktor.server.routing.*

fun Route.routeV1(batchExecutionService: BatchExecutionService) {
    route("/v1") {
        executionsV1Routes(batchExecutionService)
    }
}