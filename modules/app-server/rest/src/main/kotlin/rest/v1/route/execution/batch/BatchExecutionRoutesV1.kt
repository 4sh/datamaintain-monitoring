package rest.v1.route.execution.batch

import execution.batch.BatchExecutionService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rest.v1.route.execution.batch.dto.toDtoV1
import java.util.UUID

internal const val batchExecutionId = "batchExecutionId"

internal fun ApplicationCall.batchExecutionId() = UUID.fromString(this.parameters[batchExecutionId])

internal fun Route.batchExecutionV1Routes(batchExecutionService: BatchExecutionService) {
    get("/batchExecutions/{$batchExecutionId}") {
        call.respondNullable(call.batchExecutionId()?.let { batchExecutionService.findOneById(it)?.toDtoV1() })
    }
}