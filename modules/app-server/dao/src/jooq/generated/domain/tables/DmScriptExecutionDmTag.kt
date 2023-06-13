/*
 * This file is generated by jOOQ.
 */
package generated.domain.tables


import generated.domain.Public
import generated.domain.keys.DM_SCRIPT_EXECUTION_DM_TAG_PKEY
import generated.domain.keys.DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_SCRIPT_EXECUTION_REF_FKEY
import generated.domain.keys.DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_TAG_REF_FKEY
import generated.domain.tables.records.DmScriptExecutionDmTagRecord

import java.util.UUID
import java.util.function.Function

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row2
import org.jooq.Schema
import org.jooq.SelectField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class DmScriptExecutionDmTag(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, DmScriptExecutionDmTagRecord>?,
    aliased: Table<DmScriptExecutionDmTagRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<DmScriptExecutionDmTagRecord>(
    alias,
    Public.PUBLIC,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of
         * <code>public.dm_script_execution_dm_tag</code>
         */
        val DM_SCRIPT_EXECUTION_DM_TAG: DmScriptExecutionDmTag = DmScriptExecutionDmTag()
    }

    /**
     * The class holding records for this type
     */
    public override fun getRecordType(): Class<DmScriptExecutionDmTagRecord> = DmScriptExecutionDmTagRecord::class.java

    /**
     * The column
     * <code>public.dm_script_execution_dm_tag.fk_script_execution_ref</code>.
     */
    val FK_SCRIPT_EXECUTION_REF: TableField<DmScriptExecutionDmTagRecord, UUID?> = createField(DSL.name("fk_script_execution_ref"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>public.dm_script_execution_dm_tag.fk_tag_ref</code>.
     */
    val FK_TAG_REF: TableField<DmScriptExecutionDmTagRecord, String?> = createField(DSL.name("fk_tag_ref"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<DmScriptExecutionDmTagRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<DmScriptExecutionDmTagRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.dm_script_execution_dm_tag</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.dm_script_execution_dm_tag</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.dm_script_execution_dm_tag</code> table reference
     */
    constructor(): this(DSL.name("dm_script_execution_dm_tag"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, DmScriptExecutionDmTagRecord>): this(Internal.createPathAlias(child, key), child, key, DM_SCRIPT_EXECUTION_DM_TAG, null)
    public override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    public override fun getPrimaryKey(): UniqueKey<DmScriptExecutionDmTagRecord> = DM_SCRIPT_EXECUTION_DM_TAG_PKEY
    public override fun getReferences(): List<ForeignKey<DmScriptExecutionDmTagRecord, *>> = listOf(DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_SCRIPT_EXECUTION_REF_FKEY, DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_TAG_REF_FKEY)

    private lateinit var _dmScriptExecution: DmScriptExecution
    private lateinit var _dmTag: DmTag

    /**
     * Get the implicit join path to the <code>public.dm_script_execution</code>
     * table.
     */
    fun dmScriptExecution(): DmScriptExecution {
        if (!this::_dmScriptExecution.isInitialized)
            _dmScriptExecution = DmScriptExecution(this, DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_SCRIPT_EXECUTION_REF_FKEY)

        return _dmScriptExecution;
    }

    val dmScriptExecution: DmScriptExecution
        get(): DmScriptExecution = dmScriptExecution()

    /**
     * Get the implicit join path to the <code>public.dm_tag</code> table.
     */
    fun dmTag(): DmTag {
        if (!this::_dmTag.isInitialized)
            _dmTag = DmTag(this, DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_TAG_REF_FKEY)

        return _dmTag;
    }

    val dmTag: DmTag
        get(): DmTag = dmTag()
    public override fun `as`(alias: String): DmScriptExecutionDmTag = DmScriptExecutionDmTag(DSL.name(alias), this)
    public override fun `as`(alias: Name): DmScriptExecutionDmTag = DmScriptExecutionDmTag(alias, this)
    public override fun `as`(alias: Table<*>): DmScriptExecutionDmTag = DmScriptExecutionDmTag(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    public override fun rename(name: String): DmScriptExecutionDmTag = DmScriptExecutionDmTag(DSL.name(name), null)

    /**
     * Rename this table
     */
    public override fun rename(name: Name): DmScriptExecutionDmTag = DmScriptExecutionDmTag(name, null)

    /**
     * Rename this table
     */
    public override fun rename(name: Table<*>): DmScriptExecutionDmTag = DmScriptExecutionDmTag(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------
    public override fun fieldsRow(): Row2<UUID?, String?> = super.fieldsRow() as Row2<UUID?, String?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, String?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, String?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}