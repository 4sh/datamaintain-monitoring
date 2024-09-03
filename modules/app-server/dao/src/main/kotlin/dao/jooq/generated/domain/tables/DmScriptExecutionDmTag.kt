/*
 * This file is generated by jOOQ.
 */
package dao.jooq.generated.domain.tables


import dao.jooq.generated.domain.Public
import dao.jooq.generated.domain.keys.DM_SCRIPT_EXECUTION_DM_TAG_PKEY
import dao.jooq.generated.domain.keys.DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_SCRIPT_EXECUTION_REF_FKEY
import dao.jooq.generated.domain.keys.DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_TAG_REF_FKEY
import dao.jooq.generated.domain.tables.DmScriptExecution.DmScriptExecutionPath
import dao.jooq.generated.domain.tables.DmTag.DmTagPath
import dao.jooq.generated.domain.tables.records.DmScriptExecutionDmTagRecord

import java.util.UUID

import kotlin.collections.Collection
import kotlin.collections.List

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.InverseForeignKey
import org.jooq.Name
import org.jooq.Path
import org.jooq.PlainSQL
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SQL
import org.jooq.Schema
import org.jooq.Select
import org.jooq.Stringly
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
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, DmScriptExecutionDmTagRecord>?,
    parentPath: InverseForeignKey<out Record, DmScriptExecutionDmTagRecord>?,
    aliased: Table<DmScriptExecutionDmTagRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<DmScriptExecutionDmTagRecord>(
    alias,
    Public.PUBLIC,
    path,
    childPath,
    parentPath,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table(),
    where,
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
    override fun getRecordType(): Class<DmScriptExecutionDmTagRecord> = DmScriptExecutionDmTagRecord::class.java

    /**
     * The column
     * <code>public.dm_script_execution_dm_tag.fk_script_execution_ref</code>.
     */
    val FK_SCRIPT_EXECUTION_REF: TableField<DmScriptExecutionDmTagRecord, UUID?> = createField(DSL.name("fk_script_execution_ref"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>public.dm_script_execution_dm_tag.fk_tag_ref</code>.
     */
    val FK_TAG_REF: TableField<DmScriptExecutionDmTagRecord, String?> = createField(DSL.name("fk_tag_ref"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<DmScriptExecutionDmTagRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<DmScriptExecutionDmTagRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<DmScriptExecutionDmTagRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

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

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DmScriptExecutionDmTagRecord>?, parentPath: InverseForeignKey<out Record, DmScriptExecutionDmTagRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, DM_SCRIPT_EXECUTION_DM_TAG, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class DmScriptExecutionDmTagPath : DmScriptExecutionDmTag, Path<DmScriptExecutionDmTagRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DmScriptExecutionDmTagRecord>?, parentPath: InverseForeignKey<out Record, DmScriptExecutionDmTagRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<DmScriptExecutionDmTagRecord>): super(alias, aliased)
        override fun `as`(alias: String): DmScriptExecutionDmTagPath = DmScriptExecutionDmTagPath(DSL.name(alias), this)
        override fun `as`(alias: Name): DmScriptExecutionDmTagPath = DmScriptExecutionDmTagPath(alias, this)
        override fun `as`(alias: Table<*>): DmScriptExecutionDmTagPath = DmScriptExecutionDmTagPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<DmScriptExecutionDmTagRecord> = DM_SCRIPT_EXECUTION_DM_TAG_PKEY
    override fun getReferences(): List<ForeignKey<DmScriptExecutionDmTagRecord, *>> = listOf(DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_SCRIPT_EXECUTION_REF_FKEY, DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_TAG_REF_FKEY)

    private lateinit var _dmScriptExecution: DmScriptExecutionPath

    /**
     * Get the implicit join path to the <code>public.dm_script_execution</code>
     * table.
     */
    fun dmScriptExecution(): DmScriptExecutionPath {
        if (!this::_dmScriptExecution.isInitialized)
            _dmScriptExecution = DmScriptExecutionPath(this, DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_SCRIPT_EXECUTION_REF_FKEY, null)

        return _dmScriptExecution;
    }

    val dmScriptExecution: DmScriptExecutionPath
        get(): DmScriptExecutionPath = dmScriptExecution()

    private lateinit var _dmTag: DmTagPath

    /**
     * Get the implicit join path to the <code>public.dm_tag</code> table.
     */
    fun dmTag(): DmTagPath {
        if (!this::_dmTag.isInitialized)
            _dmTag = DmTagPath(this, DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_TAG_REF_FKEY, null)

        return _dmTag;
    }

    val dmTag: DmTagPath
        get(): DmTagPath = dmTag()
    override fun `as`(alias: String): DmScriptExecutionDmTag = DmScriptExecutionDmTag(DSL.name(alias), this)
    override fun `as`(alias: Name): DmScriptExecutionDmTag = DmScriptExecutionDmTag(alias, this)
    override fun `as`(alias: Table<*>): DmScriptExecutionDmTag = DmScriptExecutionDmTag(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): DmScriptExecutionDmTag = DmScriptExecutionDmTag(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): DmScriptExecutionDmTag = DmScriptExecutionDmTag(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): DmScriptExecutionDmTag = DmScriptExecutionDmTag(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): DmScriptExecutionDmTag = DmScriptExecutionDmTag(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): DmScriptExecutionDmTag = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): DmScriptExecutionDmTag = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): DmScriptExecutionDmTag = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): DmScriptExecutionDmTag = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): DmScriptExecutionDmTag = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): DmScriptExecutionDmTag = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): DmScriptExecutionDmTag = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): DmScriptExecutionDmTag = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): DmScriptExecutionDmTag = where(DSL.notExists(select))
}