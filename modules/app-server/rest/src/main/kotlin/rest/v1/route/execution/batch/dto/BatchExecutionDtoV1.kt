package rest.v1.route.execution.batch.dto

import execution.batch.BatchExecution
import kotlinx.serialization.Serializable
import rest.InstantSerializer
import rest.UUIDSerializer
import rest.v1.route.execution.dto.StatusDtoV1
import rest.v1.route.execution.dto.toDtoV1
import java.time.Instant
import java.util.*

@Serializable
data class BatchExecutionDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = InstantSerializer::class)
    val startDate: Instant?,
    @Serializable(with = InstantSerializer::class)
    val endDate: Instant?,
    val durationInMs: Int?,
    val origin: OriginDtoV1,
    val status: StatusDtoV1,
    val type: TypeDtoV1
)

fun BatchExecution.toDtoV1() = BatchExecutionDtoV1(
    id = this.id,
    startDate = this.startDate?.toInstant(),
    endDate = this.endDate?.toInstant(),
    durationInMs = this.durationInMs,
    origin = this.origin.toDtoV1(),
    status = this.status.toDtoV1(),
    type = this.type.toDtoV1()
)
