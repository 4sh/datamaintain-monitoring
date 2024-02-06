/*
 * This file is generated by jOOQ.
 */
package generated.domain.tables.pojos


import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

import java.io.Serializable
import java.util.UUID


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class DmScriptExecutionDmTag(
    @get:NotNull
    val fkScriptExecutionRef: UUID,
    @get:NotNull
    @get:Size(max = 255)
    val fkTagRef: String
): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: DmScriptExecutionDmTag = other as DmScriptExecutionDmTag
        if (this.fkScriptExecutionRef != o.fkScriptExecutionRef)
            return false
        if (this.fkTagRef != o.fkTagRef)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + this.fkScriptExecutionRef.hashCode()
        result = prime * result + this.fkTagRef.hashCode()
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("DmScriptExecutionDmTag (")

        sb.append(fkScriptExecutionRef)
        sb.append(", ").append(fkTagRef)

        sb.append(")")
        return sb.toString()
    }
}
