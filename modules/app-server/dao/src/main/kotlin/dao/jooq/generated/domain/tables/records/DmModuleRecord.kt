/*
 * This file is generated by jOOQ.
 */
package dao.jooq.generated.domain.tables.records


import dao.jooq.generated.domain.tables.DmModule

import jakarta.validation.constraints.Size

import java.util.UUID

import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class DmModuleRecord() : UpdatableRecordImpl<DmModuleRecord>(DmModule.DM_MODULE) {

    open var id: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    @get:Size(max = 255)
    open var name: String?
        set(value): Unit = set(1, value)
        get(): String? = get(1) as String?

    open var fkProjectRef: UUID?
        set(value): Unit = set(2, value)
        get(): UUID? = get(2) as UUID?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    /**
     * Create a detached, initialised DmModuleRecord
     */
    constructor(id: UUID? = null, name: String? = null, fkProjectRef: UUID? = null): this() {
        this.id = id
        this.name = name
        this.fkProjectRef = fkProjectRef
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised DmModuleRecord
     */
    constructor(value: dao.jooq.generated.domain.tables.pojos.DmModule?): this() {
        if (value != null) {
            this.id = value.id
            this.name = value.name
            this.fkProjectRef = value.fkProjectRef
            resetChangedOnNotNull()
        }
    }
}
