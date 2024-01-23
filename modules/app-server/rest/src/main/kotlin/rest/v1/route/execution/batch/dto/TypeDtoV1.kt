package rest.v1.route.execution.batch.dto

import execution.batch.Type

enum class TypeDtoV1 {
    ON_DEMAND,
    PLANNED
}

fun Type.toDtoV1() =
    when (this) {
        Type.ON_DEMAND -> TypeDtoV1.ON_DEMAND
        Type.PLANNED -> TypeDtoV1.PLANNED
    }