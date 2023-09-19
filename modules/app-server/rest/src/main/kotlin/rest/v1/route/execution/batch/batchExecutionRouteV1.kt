package rest.v1.route.execution.batch

import execution.batch.BatchExecutionService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

internal const val batchExecutionId = "batchExecutionId"

internal fun ApplicationCall.batchExecutionId() = UUID.fromString(this.parameters[batchExecutionId])

internal fun Route.batchExecutionV1Routes(batchExecutionService: BatchExecutionService) {
    route("/batchExecutions") {
        get("{$batchExecutionId}") {
            call.respondNullable(batchExecutionService.findOneById(call.batchExecutionId())?.toDtoV1())
        }
    }
}