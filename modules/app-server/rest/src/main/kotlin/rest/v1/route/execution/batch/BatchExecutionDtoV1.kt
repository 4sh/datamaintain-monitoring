package rest.v1.route.execution.batch

import execution.batch.BatchExecution
import kotlinx.serialization.Serializable
import rest.OffsetDateTimeSerializer
import rest.UUIDSerializer
import rest.v1.route.execution.StatusDtoV1
import rest.v1.route.execution.toDtoV1
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class BatchExecutionDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val startDate: OffsetDateTime?,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val endDate: OffsetDateTime?,
    val durationInMs: Int?,
    val origin: OriginDtoV1,
    val status: StatusDtoV1,
    val type: TypeDtoV1
)

fun BatchExecution.toDtoV1() = BatchExecutionDtoV1(
    id = this.id,
    startDate = this.startDate,
    endDate = this.endDate,
    durationInMs = this.durationInMs,
    origin = this.origin.toDtoV1(),
    status = this.status.toDtoV1(),
    type = this.type.toDtoV1()
)
