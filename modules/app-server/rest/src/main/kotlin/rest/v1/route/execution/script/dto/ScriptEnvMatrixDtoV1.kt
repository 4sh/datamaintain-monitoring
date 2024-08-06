package rest.v1.route.execution.script.dto

import environment.Environment
import execution.script.ModuleScriptsExecutionsInformation
import execution.script.ScriptWithAllExecutions
import kotlinx.serialization.Serializable
import rest.InstantSerializer
import rest.UUIDSerializer
import rest.v1.route.environment.EnvironmentDtoV1
import rest.v1.route.environment.toDtoV1
import java.time.Instant
import java.util.UUID

@Serializable
data class ScriptEnvMatrixDtoV1(
    val entries: List<ScriptEnvEntryDtoV1>,
    val envs: List<EnvironmentDtoV1>
)

fun ModuleScriptsExecutionsInformation.toScriptEnvMatrixDtoV1() = ScriptEnvMatrixDtoV1(
    entries = this.scriptsWithAllExecutions.map{ scriptWithAllExecutions -> scriptWithAllExecutions.toScriptEnvEntryDtoV1(
        environments.associateBy { it.id }
    ) },
    envs = this.environments.map { it.toDtoV1() }
)

fun ScriptWithAllExecutions.toScriptEnvEntryDtoV1(envsMaps: Map<UUID, Environment>): ScriptEnvEntryDtoV1 {
    return ScriptEnvEntryDtoV1(
        scriptId = script.checksum,
        scriptName = script.name,
        envs = this.executions.map {
            EnvExecutedScriptEntryDtoV1(
                envId = it.fkEnvironmentRef,
                execution = ScriptExecutionMetadataDtoV1(
                    executionId = it.id,
                    executionDate = it.startDate?.toInstant(),
                    duration = it.durationInMs?.toLong(),
                    scriptID = this.script.checksum
                )
            )
        }
    )
}

@Serializable
data class ScriptEnvEntryDtoV1(
    val scriptId: String,
    val scriptName: String,
    val envs: List<EnvExecutedScriptEntryDtoV1>
)

@Serializable
data class EnvExecutedScriptEntryDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val envId: UUID,
    val execution : ScriptExecutionMetadataDtoV1
)

@Serializable
data class ScriptExecutionMetadataDtoV1(
    @Serializable(with = UUIDSerializer::class)
    val executionId: UUID,
    @Serializable(with = InstantSerializer::class)
    val executionDate: Instant?,
    val duration: Long?,
    val scriptID: String
)