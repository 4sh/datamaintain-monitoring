import org.jooq.exception.IntegrityConstraintViolationException

fun IntegrityConstraintViolationException.isDuplicatedKeyException() = this.message?.contains("duplicate key value") ?: false