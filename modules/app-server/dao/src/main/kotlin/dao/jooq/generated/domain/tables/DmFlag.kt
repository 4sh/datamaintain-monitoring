/*
 * This file is generated by jOOQ.
 */
package dao.jooq.generated.domain.tables


import dao.jooq.generated.domain.Public
import dao.jooq.generated.domain.keys.DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_FLAG_REF_FKEY
import dao.jooq.generated.domain.keys.DM_FLAG_PKEY
import dao.jooq.generated.domain.tables.DmBatchExecution.DmBatchExecutionPath
import dao.jooq.generated.domain.tables.DmBatchExecutionDmFlag.DmBatchExecutionDmFlagPath
import dao.jooq.generated.domain.tables.records.DmFlagRecord

import kotlin.collections.Collection

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
open class DmFlag(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, DmFlagRecord>?,
    parentPath: InverseForeignKey<out Record, DmFlagRecord>?,
    aliased: Table<DmFlagRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<DmFlagRecord>(
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
         * The reference instance of <code>public.dm_flag</code>
         */
        val DM_FLAG: DmFlag = DmFlag()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<DmFlagRecord> = DmFlagRecord::class.java

    /**
     * The column <code>public.dm_flag.name</code>.
     */
    val NAME: TableField<DmFlagRecord, String?> = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<DmFlagRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<DmFlagRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<DmFlagRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.dm_flag</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.dm_flag</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.dm_flag</code> table reference
     */
    constructor(): this(DSL.name("dm_flag"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DmFlagRecord>?, parentPath: InverseForeignKey<out Record, DmFlagRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, DM_FLAG, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class DmFlagPath : DmFlag, Path<DmFlagRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DmFlagRecord>?, parentPath: InverseForeignKey<out Record, DmFlagRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<DmFlagRecord>): super(alias, aliased)
        override fun `as`(alias: String): DmFlagPath = DmFlagPath(DSL.name(alias), this)
        override fun `as`(alias: Name): DmFlagPath = DmFlagPath(alias, this)
        override fun `as`(alias: Table<*>): DmFlagPath = DmFlagPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<DmFlagRecord> = DM_FLAG_PKEY

    private lateinit var _dmBatchExecutionDmFlag: DmBatchExecutionDmFlagPath

    /**
     * Get the implicit to-many join path to the
     * <code>public.dm_batch_execution_dm_flag</code> table
     */
    fun dmBatchExecutionDmFlag(): DmBatchExecutionDmFlagPath {
        if (!this::_dmBatchExecutionDmFlag.isInitialized)
            _dmBatchExecutionDmFlag = DmBatchExecutionDmFlagPath(this, null, DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_FLAG_REF_FKEY.inverseKey)

        return _dmBatchExecutionDmFlag;
    }

    val dmBatchExecutionDmFlag: DmBatchExecutionDmFlagPath
        get(): DmBatchExecutionDmFlagPath = dmBatchExecutionDmFlag()

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.dm_batch_execution</code> table
     */
    val dmBatchExecution: DmBatchExecutionPath
        get(): DmBatchExecutionPath = dmBatchExecutionDmFlag().dmBatchExecution()
    override fun `as`(alias: String): DmFlag = DmFlag(DSL.name(alias), this)
    override fun `as`(alias: Name): DmFlag = DmFlag(alias, this)
    override fun `as`(alias: Table<*>): DmFlag = DmFlag(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): DmFlag = DmFlag(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): DmFlag = DmFlag(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): DmFlag = DmFlag(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): DmFlag = DmFlag(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): DmFlag = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): DmFlag = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): DmFlag = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): DmFlag = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): DmFlag = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): DmFlag = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): DmFlag = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): DmFlag = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): DmFlag = where(DSL.notExists(select))
}