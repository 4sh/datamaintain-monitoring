package rest.v1.route.execution

import execution.batch.BatchExecutionService
import io.ktor.server.routing.*
import rest.v1.route.execution.batch.batchExecutionV1Routes

internal fun Route.executionV1Routes(
    batchExecutionService: BatchExecutionService
) {
    route("/executions") {
        batchExecutionV1Routes(batchExecutionService)
    }
}