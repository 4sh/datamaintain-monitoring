package rest.v1.route.execution.batch

import execution.batch.BatchExecution
import kotlinx.serialization.Serializable
import rest.serializer.OffSetDateTimeSerializer
import rest.serializer.UUIDSerializer
import rest.v1.route.execution.StatusDtoV1
import rest.v1.route.execution.toDtoV1
import java.time.OffsetDateTime
import java.util.UUID

@Serializable
data class BatchExecutionDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = OffSetDateTimeSerializer::class)
    val startDate: OffsetDateTime?,
    @Serializable(with = OffSetDateTimeSerializer::class)
    val endDate: OffsetDateTime?,
    val durationInMs: Int?,
    val origin: OriginDtoV1,
    val status: StatusDtoV1,
    val type: TypeDtoV1,
    @Serializable(with = UUIDSerializer::class)
    val fkEnvironmentRef: UUID,
    @Serializable(with = UUIDSerializer::class)
    val fkModuleRef: UUID
) {
    enum class OriginDtoV1 {
        CLIENT,
        SERVER,
        TIER
    }

    enum class TypeDtoV1 {
        ON_DEMAND,
        PLANNED
    }
}

fun BatchExecution.toDtoV1() = BatchExecutionDtoV1(
    id = this.id,
    startDate = this.startDate,
    endDate = this.endDate,
    durationInMs = this.durationInMs,
    origin = this.origin.toDtoV1(),
    status = this.status.toDtoV1(),
    type = this.type.toDtoV1(),
    fkEnvironmentRef = this.fkEnvironmentRef,
    fkModuleRef = this.fkModuleRef
)

fun BatchExecution.Origin.toDtoV1() =
    when (this) {
        BatchExecution.Origin.CLIENT -> BatchExecutionDtoV1.OriginDtoV1.CLIENT
        BatchExecution.Origin.SERVER -> BatchExecutionDtoV1.OriginDtoV1.SERVER
        BatchExecution.Origin.TIER -> BatchExecutionDtoV1.OriginDtoV1.TIER
    }

fun BatchExecution.Type.toDtoV1() =
    when (this) {
        BatchExecution.Type.ON_DEMAND -> BatchExecutionDtoV1.TypeDtoV1.ON_DEMAND
        BatchExecution.Type.PLANNED -> BatchExecutionDtoV1.TypeDtoV1.PLANNED
    }