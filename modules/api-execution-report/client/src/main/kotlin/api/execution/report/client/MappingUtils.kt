package api.execution.report.client

import com.google.protobuf.Timestamp
import java.time.Instant

fun Instant.toTimestamp(): Timestamp {
    return Timestamp.newBuilder()
        .setSeconds(epochSecond)
        .setNanos(nano)
        .build()
}
