package datamaintain.monitoring.api.execution.report.api

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ScriptExecutionStartResponse (
    @Serializable(with = UUIDSerializer::class)
    val scriptExecutionId: UUID
)