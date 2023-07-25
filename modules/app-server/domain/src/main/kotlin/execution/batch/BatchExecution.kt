package execution.batch

import execution.Status
import java.time.OffsetDateTime
import java.util.*

data class BatchExecution(
    val id: UUID,
    val startDate: OffsetDateTime? = null,
    val endDate: OffsetDateTime? = null,
    val durationInMs: Int? = null,
    val origin: Origin,
    val status: Status,
    val type: Type,
    val fkEnvironmentRef: UUID,
    val fkModuleRef: UUID
) {
    enum class Origin {
        CLIENT,
        SERVER,
        TIER
    }

    enum class Type {
        ON_DEMAND,
        PLANNED
    }
}