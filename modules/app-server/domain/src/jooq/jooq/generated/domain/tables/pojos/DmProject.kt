/*
 * This file is generated by jOOQ.
 */
package jooq.generated.domain.tables.pojos


import jakarta.validation.constraints.Size

import java.io.Serializable
import java.util.UUID


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class DmProject(
    val id: UUID? = null,
    @get:Size(max = 255)
    val name: String? = null
): Serializable {

    public override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: DmProject = other as DmProject
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
        return true
    }

    public override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.id == null) 0 else this.id.hashCode())
        result = prime * result + (if (this.name == null) 0 else this.name.hashCode())
        return result
    }

    public override fun toString(): String {
        val sb = StringBuilder("DmProject (")

        sb.append(id)
        sb.append(", ").append(name)

        sb.append(")")
        return sb.toString()
    }
}
