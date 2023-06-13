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
data class DmScript(
    @get:Size(max = 255)
    val name: String? = null,
    @get:NotNull
    @get:Size(max = 255)
    val checksum: String? = null,
    @get:Size(max = 255)
    val content: String? = null
): Serializable {

    public override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: DmScript = other as DmScript
        if (this.name == null) {
            if (o.name != null)
                return false
        }
        else if (this.name != o.name)
            return false
        if (this.checksum == null) {
            if (o.checksum != null)
                return false
        }
        else if (this.checksum != o.checksum)
            return false
        if (this.content == null) {
            if (o.content != null)
                return false
        }
        else if (this.content != o.content)
            return false
        return true
    }

    public override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.name == null) 0 else this.name.hashCode())
        result = prime * result + (if (this.checksum == null) 0 else this.checksum.hashCode())
        result = prime * result + (if (this.content == null) 0 else this.content.hashCode())
        return result
    }

    public override fun toString(): String {
        val sb = StringBuilder("DmScript (")

        sb.append(name)
        sb.append(", ").append(checksum)
        sb.append(", ").append(content)

        sb.append(")")
        return sb.toString()
    }
}