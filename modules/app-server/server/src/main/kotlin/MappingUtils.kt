import com.google.protobuf.Timestamp
import java.time.Instant

fun Timestamp.toInstant(): Instant =
    Instant.ofEpochSecond(seconds, nanos.toLong())
