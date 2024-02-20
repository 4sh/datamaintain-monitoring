package rest.v1.route.execution.script.dto

import kotlinx.serialization.Serializable
import rest.UUIDSerializer
import rest.v1.route.execution.dto.StatusDtoV1
import rest.v1.route.execution.dto.toDtoV1
import execution.script.ScriptExecution
import rest.InstantSerializer
import java.time.Instant
import java.util.UUID

@Serializable
data class ScriptExecutionDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = InstantSerializer::class)
    val startDate: Instant? = null,
    @Serializable(with = InstantSerializer::class)
    val endDate: Instant? = null,
    val durationInMs: Int? = null,
    val executionOrderIndex: Int,
    val output: String? = null,
    val status: StatusDtoV1? = null
)

fun ScriptExecution.toDtoV1() = ScriptExecutionDtoV1(
    id = this.id,
    startDate = this.startDate?.toInstant(),
    endDate = this.endDate?.toInstant(),
    durationInMs = this.durationInMs,
    executionOrderIndex = this.executionOrderIndex,
    output = this.output,
    status = this.status?.toDtoV1()
)
