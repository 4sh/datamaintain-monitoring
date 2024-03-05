package datamaintain.monitoring.api.execution.report.api

import kotlinx.serialization.Serializable
import java.util.*

typealias ExecutionId = UUID

@Serializable
data class ExecutionStartResponse(
    @Serializable(with = UUIDSerializer::class)
    val executionId: ExecutionId
)