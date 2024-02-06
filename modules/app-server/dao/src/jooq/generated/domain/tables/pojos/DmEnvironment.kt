/*
 * This file is generated by jOOQ.
 */
package generated.domain.tables.pojos


import jakarta.validation.constraints.Size

import java.io.Serializable
import java.util.UUID


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class DmEnvironment(
    val id: UUID? = null,
    @get:Size(max = 255)
    val name: String? = null,
    @get:Size(max = 255)
    val smallName: String? = null,
    val fkProjectRef: UUID? = null
): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: DmEnvironment = other as DmEnvironment
        if (this.id == null) {
            if (o.id != null)
                return false
        }
        else if (this.id != o.id)
            return false
        if (this.name == null) {
            if (o.name != null)
                return false
        }
        else if (this.name != o.name)
            return false
        if (this.smallName == null) {
            if (o.smallName != null)
                return false
        }
        else if (this.smallName != o.smallName)
            return false
        if (this.fkProjectRef == null) {
            if (o.fkProjectRef != null)
                return false
        }
        else if (this.fkProjectRef != o.fkProjectRef)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.id == null) 0 else this.id.hashCode())
        result = prime * result + (if (this.name == null) 0 else this.name.hashCode())
        result = prime * result + (if (this.smallName == null) 0 else this.smallName.hashCode())
        result = prime * result + (if (this.fkProjectRef == null) 0 else this.fkProjectRef.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("DmEnvironment (")

        sb.append(id)
        sb.append(", ").append(name)
        sb.append(", ").append(smallName)
        sb.append(", ").append(fkProjectRef)

        sb.append(")")
        return sb.toString()
    }
}
