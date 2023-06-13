package datamaintain.monitoring.api.execution.report.api

import kotlinx.serialization.Serializable

typealias ExecutionId = Int

@Serializable
data class ExecutionStartResponse(val executionId: ExecutionId)