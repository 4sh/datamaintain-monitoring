package rest.v1.route.execution.batch.dto

import execution.batch.BatchExecutionListItem
import kotlinx.serialization.Serializable
import rest.OffsetDateTimeSerializer
import rest.UUIDSerializer
import rest.v1.route.environment.EnvironmentDtoV1
import rest.v1.route.environment.toDtoV1
import rest.v1.route.execution.dto.StatusDtoV1
import rest.v1.route.execution.dto.toDtoV1
import rest.v1.route.module.dto.ModuleDtoV1
import rest.v1.route.module.dto.toDtoV1
import rest.v1.route.project.dto.ProjectDtoV1
import rest.v1.route.project.dto.toDtoV1
import java.time.OffsetDateTime
import java.util.*

@Serializable
data class BatchExecutionListItemDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val startDate: OffsetDateTime?,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val endDate: OffsetDateTime?,
    val durationInMs: Int?,
    val origin: OriginDtoV1,
    val status: StatusDtoV1,
    val type: TypeDtoV1,
    val project: ProjectDtoV1?,
    val module: ModuleDtoV1,
    val environment: EnvironmentDtoV1
)

fun BatchExecutionListItem.toDtoV1() = BatchExecutionListItemDtoV1(
    id = this.id,
    startDate = this.startDate,
    endDate = this.endDate,
    durationInMs = this.durationInMs,
    origin = this.origin.toDtoV1(),
    status = this.status.toDtoV1(),
    type = this.type.toDtoV1(),
    project = this.project.toDtoV1(),
    module = this.module.toDtoV1(),
    environment = this.environment.toDtoV1()
)
