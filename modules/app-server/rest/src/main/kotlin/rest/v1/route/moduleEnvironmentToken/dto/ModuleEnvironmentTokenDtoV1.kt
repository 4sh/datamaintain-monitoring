package rest.v1.route.moduleEnvironmentToken.dto

import kotlinx.serialization.Serializable
import moduleEnvironmentToken.ModuleEnvironmentToken
import rest.UUIDSerializer
import java.util.*

@Serializable
data class ModuleEnvironmentTokenDtoV1(
    @Serializable(UUIDSerializer::class)
    val moduleRef: UUID,
    @Serializable(UUIDSerializer::class)
    val environmentRef: UUID,
    @Serializable(UUIDSerializer::class)
    val tokenValue: UUID
)

fun ModuleEnvironmentToken.toDtoV1() = ModuleEnvironmentTokenDtoV1(
    moduleRef = fkModuleRef,
    environmentRef = fkEnvironmentRef,
    tokenValue = tokenValue
)