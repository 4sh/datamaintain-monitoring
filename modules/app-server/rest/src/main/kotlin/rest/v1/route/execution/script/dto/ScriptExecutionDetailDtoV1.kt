package rest.v1.route.execution.script.dto

import kotlinx.serialization.Serializable
import rest.OffsetDateTimeSerializer
import rest.UUIDSerializer
import rest.v1.route.execution.dto.StatusDtoV1
import rest.v1.route.execution.dto.toDtoV1
import rest.v1.route.script.dto.ScriptDtoV1
import rest.v1.route.script.dto.toDtoV1
import execution.script.ScriptExecutionDetail
import java.time.OffsetDateTime
import java.util.UUID

@Serializable
data class ScriptExecutionDetailDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val startDate: OffsetDateTime? = null,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val endDate: OffsetDateTime? = null,
    val durationInMs: Int? = null,
    val executionOrderIndex: Int,
    val output: String? = null,
    val status: StatusDtoV1? = null,
    val script: ScriptDtoV1
)

fun ScriptExecutionDetail.toDtoV1() = ScriptExecutionDetailDtoV1(
    id = this.id,
    startDate = this.startDate,
    endDate = this.endDate,
    durationInMs = this.durationInMs,
    executionOrderIndex = this.executionOrderIndex,
    output = this.output,
    status = this.status?.toDtoV1(),
    script = this.script.toDtoV1()
)
