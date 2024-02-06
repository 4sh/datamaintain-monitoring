package rest.v1.route.execution

import execution.Status
import io.ktor.server.application.*

internal fun ApplicationCall.status() = this.parameters["status"]?.let { Status.valueOf(it) }