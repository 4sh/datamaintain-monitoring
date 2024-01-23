package rest.v1.route.execution.batch.dto

import execution.batch.BatchExecution

enum class OriginDtoV1 {
    CLIENT,
    SERVER,
    TIER
}

fun BatchExecution.Origin.toDtoV1() =
    when (this) {
        BatchExecution.Origin.CLIENT -> OriginDtoV1.CLIENT
        BatchExecution.Origin.SERVER -> OriginDtoV1.SERVER
        BatchExecution.Origin.TIER -> OriginDtoV1.TIER
    }