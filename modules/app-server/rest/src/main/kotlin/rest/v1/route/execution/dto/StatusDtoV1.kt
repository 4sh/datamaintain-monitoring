package rest.v1.route.execution.dto

import execution.Status

enum class StatusDtoV1 {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    ERROR
}

fun Status.toDtoV1() =
    when (this) {
        Status.PENDING -> StatusDtoV1.PENDING
        Status.IN_PROGRESS -> StatusDtoV1.IN_PROGRESS
        Status.COMPLETED -> StatusDtoV1.COMPLETED
        Status.ERROR -> StatusDtoV1.ERROR
    }