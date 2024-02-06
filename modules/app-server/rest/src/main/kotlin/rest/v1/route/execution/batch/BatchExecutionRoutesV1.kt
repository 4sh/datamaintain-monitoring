package rest.v1.route.execution.batch

import execution.Status
import execution.batch.BatchExecutionSearchRequest
import execution.batch.BatchExecutionService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rest.v1.route.execution.batch.dto.toDtoV1
import java.util.UUID

internal const val batchExecutionId = "batchExecutionId"

internal fun ApplicationCall.batchExecutionId() = UUID.fromString(this.parameters[batchExecutionId])

internal fun Route.batchExecutionV1Routes(batchExecutionService: BatchExecutionService) {
    route("/batchExecutions") {
        get {
            val batchExecutionSearchRequest =
                BatchExecutionSearchRequest(
                    status = call.parameters["status"]?.let { Status.valueOf(it) },
                    projectRef = call.parameters["projectRef"]?.let { UUID.fromString(it) },
                    moduleRef = call.parameters["moduleRef"]?.let { UUID.fromString(it) },
                    environmentRef = call.parameters["environmentRef"]?.let { UUID.fromString(it) }
                )
            call.respond(batchExecutionService.find(batchExecutionSearchRequest).map { it.toDtoV1() })
        }

        get("/{$batchExecutionId}") {
            call.respondNullable(call.batchExecutionId()?.let { batchExecutionService.findOneById(it)?.toDtoV1() })
        }
    }
}