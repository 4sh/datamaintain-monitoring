package dao.utils

import execution.Status
import execution.batch.BatchExecution
import generated.domain.enums.BatchExecutionOrigin
import generated.domain.enums.BatchExecutionType
import generated.domain.enums.ExecutionStatus

fun BatchExecution.Origin.toDto(): BatchExecutionOrigin =
    when (this) {
        BatchExecution.Origin.CLIENT -> BatchExecutionOrigin.CLIENT
        BatchExecution.Origin.SERVER -> BatchExecutionOrigin.SERVER
        BatchExecution.Origin.TIER -> BatchExecutionOrigin.TIER
    }

fun BatchExecution.Type.toDto(): BatchExecutionType =
    when (this) {
        BatchExecution.Type.ON_DEMAND -> BatchExecutionType.ON_DEMAND
        BatchExecution.Type.PLANNED -> BatchExecutionType.PLANNED
    }

fun Status.toDto(): ExecutionStatus =
    when (this) {
        Status.PENDING -> ExecutionStatus.PENDING
        Status.IN_PROGRESS -> ExecutionStatus.IN_PROGRESS
        Status.COMPLETED -> ExecutionStatus.COMPLETED
        Status.ERROR -> ExecutionStatus.ERROR
    }