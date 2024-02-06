package rest.v1.route.execution.batch

import execution.batch.BatchExecutionSearchRequest
import execution.batch.BatchExecutionService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rest.v1.route.execution.batch.dto.toDtoV1
import rest.v1.route.execution.status
import java.util.UUID

private const val batchExecutionId = "batchExecutionId"

private fun ApplicationCall.batchExecutionId() = UUID.fromString(this.parameters[batchExecutionId])
private fun ApplicationCall.projectRef() = this.parameters["projectRef"]?.let { UUID.fromString(it) }
private fun ApplicationCall.moduleRef() = this.parameters["moduleRef"]?.let { UUID.fromString(it) }
private fun ApplicationCall.environmentRef() = this.parameters["environmentRef"]?.let { UUID.fromString(it) }

internal fun Route.batchExecutionV1Routes(batchExecutionService: BatchExecutionService) {
    route("/batchExecutions") {
        get {
            val batchExecutionSearchRequest =
                BatchExecutionSearchRequest(
                    status = call.status(),
                    projectRef = call.projectRef(),
                    moduleRef = call.moduleRef(),
                    environmentRef = call.environmentRef()
                )
            call.respond(batchExecutionService.find(batchExecutionSearchRequest).map { it.toDtoV1() })
        }

        get("/{$batchExecutionId}") {
            call.respondNullable(call.batchExecutionId()?.let { batchExecutionService.findOneById(it)?.toDtoV1() })
        }
    }
}