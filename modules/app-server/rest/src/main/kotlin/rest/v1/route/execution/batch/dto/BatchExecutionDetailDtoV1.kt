package rest.v1.route.execution.batch.dto

import execution.batch.BatchExecutionDetail
import kotlinx.serialization.Serializable
import rest.InstantSerializer
import rest.UUIDSerializer
import rest.v1.route.environment.EnvironmentDtoV1
import rest.v1.route.environment.toDtoV1
import rest.v1.route.execution.dto.StatusDtoV1
import rest.v1.route.execution.dto.toDtoV1
import rest.v1.route.execution.script.dto.ScriptExecutionDetailDtoV1
import rest.v1.route.execution.script.dto.toDtoV1
import rest.v1.route.module.dto.ModuleDtoV1
import rest.v1.route.module.dto.toDtoV1
import rest.v1.route.project.dto.ProjectDtoV1
import rest.v1.route.project.dto.toDtoV1
import java.time.Instant
import java.util.*

@Serializable
data class BatchExecutionDetailDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = InstantSerializer::class)
    val startDate: Instant? = null,
    @Serializable(with = InstantSerializer::class)
    val endDate: Instant? = null,
    val durationInMs: Int? = null,
    val origin: OriginDtoV1,
    val status: StatusDtoV1,
    val type: TypeDtoV1,
    val project: ProjectDtoV1,
    val module: ModuleDtoV1,
    val environment: EnvironmentDtoV1,
    val scriptsExecutions: List<ScriptExecutionDetailDtoV1>
)

fun BatchExecutionDetail.toDtoV1() = BatchExecutionDetailDtoV1(
    id = this.id,
    startDate = this.startDate?.toInstant(),
    endDate = this.endDate?.toInstant(),
    durationInMs = this.durationInMs,
    origin = this.origin.toDtoV1(),
    status = this.status.toDtoV1(),
    type = this.type.toDtoV1(),
    project = this.project.toDtoV1(),
    module = this.module.toDtoV1(),
    environment = this.environment.toDtoV1(),
    scriptsExecutions = this.scriptsExecutions.map { it.toDtoV1() }
)
