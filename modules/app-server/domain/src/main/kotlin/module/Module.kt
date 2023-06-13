package module

import java.util.*

data class Module(val id: UUID, val name: String, val fkProjectRef: UUID)