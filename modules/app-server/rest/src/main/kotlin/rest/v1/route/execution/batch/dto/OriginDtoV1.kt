package rest.v1.route.execution.batch.dto

import execution.batch.Origin

enum class OriginDtoV1 {
    CLIENT,
    SERVER,
    TIER
}

fun Origin.toDtoV1() =
    when (this) {
        Origin.CLIENT -> OriginDtoV1.CLIENT
        Origin.SERVER -> OriginDtoV1.SERVER
        Origin.TIER -> OriginDtoV1.TIER
    }