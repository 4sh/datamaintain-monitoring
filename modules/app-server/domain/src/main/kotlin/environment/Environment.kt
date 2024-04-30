package environment

import java.util.*

open class Environment(
    open val id: UUID,
    open val name: String,
    open val fkProjectRef: UUID,
    open val smallName: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Environment

        if (id != other.id) return false
        if (name != other.name) return false
        if (fkProjectRef != other.fkProjectRef) return false
        if (smallName != other.smallName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + fkProjectRef.hashCode()
        result = 31 * result + smallName.hashCode()
        return result
    }

    override fun toString(): String {
        return "Environment(id=$id, name='$name', fkProjectRef=$fkProjectRef, smallName='$smallName')"
    }
}