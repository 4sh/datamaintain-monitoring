package api.execution.report.rest

import api.execution.report.domain.module.batch.execution.BatchExecutionService
import api.execution.report.domain.module.script.execution.ScriptExecutionService
import api.execution.report.rest.v1.routeV1
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    batchExecutionService: BatchExecutionService,
    scriptExecutionService: ScriptExecutionService
) {
    routing {
        route("/api") {
            routeV1(batchExecutionService = batchExecutionService, scriptExecutionService = scriptExecutionService)
        }
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}