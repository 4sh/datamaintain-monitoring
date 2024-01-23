package dao.utils

import execution.Status
import execution.batch.Origin
import execution.batch.Type
import generated.domain.enums.BatchExecutionOrigin
import generated.domain.enums.BatchExecutionType
import generated.domain.enums.ExecutionStatus

fun Origin.toDto(): BatchExecutionOrigin =
    when (this) {
        Origin.CLIENT -> BatchExecutionOrigin.CLIENT
        Origin.SERVER -> BatchExecutionOrigin.SERVER
        Origin.TIER -> BatchExecutionOrigin.TIER
    }

fun Type.toDto(): BatchExecutionType =
    when (this) {
        Type.ON_DEMAND -> BatchExecutionType.ON_DEMAND
        Type.PLANNED -> BatchExecutionType.PLANNED
    }

fun Status.toDto(): ExecutionStatus =
    when (this) {
        Status.PENDING -> ExecutionStatus.PENDING
        Status.IN_PROGRESS -> ExecutionStatus.IN_PROGRESS
        Status.COMPLETED -> ExecutionStatus.COMPLETED
        Status.ERROR -> ExecutionStatus.ERROR
    }