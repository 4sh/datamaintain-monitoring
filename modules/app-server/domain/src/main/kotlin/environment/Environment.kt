package environment

import java.util.*

open class Environment(
    open val id: UUID,
    open val name: String,
    open val fkProjectRef: UUID,
    open val smallName: String
)