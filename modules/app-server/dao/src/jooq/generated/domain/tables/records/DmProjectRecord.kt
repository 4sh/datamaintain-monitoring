/*
 * This file is generated by jOOQ.
 */
package generated.domain.tables.records


import generated.domain.tables.DmProject

import jakarta.validation.constraints.Size

import java.util.UUID

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record3
import org.jooq.Row3
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class DmProjectRecord() : UpdatableRecordImpl<DmProjectRecord>(DmProject.DM_PROJECT), Record3<UUID?, String?, String?> {

    open var id: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    @get:Size(max = 255)
    open var name: String?
        set(value): Unit = set(1, value)
        get(): String? = get(1) as String?

    @get:Size(max = 255)
    open var smallName: String?
        set(value): Unit = set(2, value)
        get(): String? = get(2) as String?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    public override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    public override fun fieldsRow(): Row3<UUID?, String?, String?> = super.fieldsRow() as Row3<UUID?, String?, String?>
    public override fun valuesRow(): Row3<UUID?, String?, String?> = super.valuesRow() as Row3<UUID?, String?, String?>
    public override fun field1(): Field<UUID?> = DmProject.DM_PROJECT.ID
    public override fun field2(): Field<String?> = DmProject.DM_PROJECT.NAME
    public override fun field3(): Field<String?> = DmProject.DM_PROJECT.SMALL_NAME
    public override fun component1(): UUID? = id
    public override fun component2(): String? = name
    public override fun component3(): String? = smallName
    public override fun value1(): UUID? = id
    public override fun value2(): String? = name
    public override fun value3(): String? = smallName

    public override fun value1(value: UUID?): DmProjectRecord {
        set(0, value)
        return this
    }

    public override fun value2(value: String?): DmProjectRecord {
        set(1, value)
        return this
    }

    public override fun value3(value: String?): DmProjectRecord {
        set(2, value)
        return this
    }

    public override fun values(value1: UUID?, value2: String?, value3: String?): DmProjectRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        return this
    }

    /**
     * Create a detached, initialised DmProjectRecord
     */
    constructor(id: UUID? = null, name: String? = null, smallName: String? = null): this() {
        this.id = id
        this.name = name
        this.smallName = smallName
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised DmProjectRecord
     */
    constructor(value: generated.domain.tables.pojos.DmProject?): this() {
        if (value != null) {
            this.id = value.id
            this.name = value.name
            this.smallName = value.smallName
            resetChangedOnNotNull()
        }
    }
}
