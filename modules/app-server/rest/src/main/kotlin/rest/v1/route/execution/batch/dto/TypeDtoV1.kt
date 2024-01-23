package rest.v1.route.execution.batch.dto

import execution.batch.BatchExecution

enum class TypeDtoV1 {
    ON_DEMAND,
    PLANNED
}

fun BatchExecution.Type.toDtoV1() =
    when (this) {
        BatchExecution.Type.ON_DEMAND -> TypeDtoV1.ON_DEMAND
        BatchExecution.Type.PLANNED -> TypeDtoV1.PLANNED
    }