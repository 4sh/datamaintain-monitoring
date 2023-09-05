package execution
enum class Status {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    ERROR
}

val INITIAL_STATUS = Status.PENDING
