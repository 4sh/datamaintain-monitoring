/*
 * This file is generated by jOOQ.
 */
package generated.domain.tables.pojos


import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

import java.io.Serializable


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class DmFlag(
    @get:NotNull
    @get:Size(max = 255)
    val name: String
): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: DmFlag = other as DmFlag
        if (this.name != o.name)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + this.name.hashCode()
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("DmFlag (")

        sb.append(name)

        sb.append(")")
        return sb.toString()
    }
}
