package dao.utils

import execution.Status
import execution.batch.Origin
import execution.batch.Type
import dao.jooq.generated.domain.enums.BatchExecutionOrigin
import dao.jooq.generated.domain.enums.BatchExecutionType
import dao.jooq.generated.domain.enums.ExecutionStatus
import java.sql.ResultSet
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

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

/**
 * According to Postgresql documentation (https://www.postgresql.org/docs/current/datatype-datetime.html) :
 *
 * For timestamp with time zone, the internally stored value is always in UTC (Universal Coordinated Time, traditionally known as Greenwich Mean Time, GMT). An input value that has an explicit time zone specified is converted to UTC using the appropriate offset for that time zone. If no time zone is stated in the input string, then it is assumed to be in the time zone indicated by the system's TimeZone parameter, and is converted to UTC using the offset for the timezone zone.
 * When a timestamp with time zone value is output, it is always converted from UTC to the current timezone zone, and displayed as local time in that zone. To see the time in another time zone, either change timezone or use the AT TIME ZONE construct (see Section 9.9.4).
 * Conversions between timestamp without time zone and timestamp with time zone normally assume that the timestamp without time zone value should be taken or given as timezone local time. A different time zone can be specified for the conversion using AT TIME ZONE.
 */
fun Instant.toOffsetDateTime(): OffsetDateTime {
    return OffsetDateTime.ofInstant(this, ZoneId.of("UTC").normalized())
}


fun ResultSet.getOffsetDateTime(key: String): OffsetDateTime? {
    val date = this.getTimestamp(key) ?: return null

    return date.toInstant().toOffsetDateTime()
}

fun ResultSet.getUUID(key: String): UUID = UUID.fromString(this.getString(key))

fun ResultSet.getIntOrNull(name: String): Int? {
    return this.getString(name)?.toInt()
}