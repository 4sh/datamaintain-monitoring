package api.execution.report.app

import api.execution.report.rest.configureRouting
import api.execution.report.rest.configureSerialization
import io.ktor.server.application.*

fun Application.module() {
    configureSerialization()
    configureRouting(batchExecutionService)
}