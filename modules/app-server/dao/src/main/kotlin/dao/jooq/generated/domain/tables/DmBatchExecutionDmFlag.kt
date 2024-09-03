/*
 * This file is generated by jOOQ.
 */
package dao.jooq.generated.domain.tables


import dao.jooq.generated.domain.Public
import dao.jooq.generated.domain.keys.DM_BATCH_EXECUTION_DM_FLAG_PKEY
import dao.jooq.generated.domain.keys.DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_BATCH_EXECUTION_REF_FKEY
import dao.jooq.generated.domain.keys.DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_FLAG_REF_FKEY
import dao.jooq.generated.domain.tables.DmBatchExecution.DmBatchExecutionPath
import dao.jooq.generated.domain.tables.DmFlag.DmFlagPath
import dao.jooq.generated.domain.tables.records.DmBatchExecutionDmFlagRecord

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
open class DmBatchExecutionDmFlag(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, DmBatchExecutionDmFlagRecord>?,
    parentPath: InverseForeignKey<out Record, DmBatchExecutionDmFlagRecord>?,
    aliased: Table<DmBatchExecutionDmFlagRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<DmBatchExecutionDmFlagRecord>(
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
         * <code>public.dm_batch_execution_dm_flag</code>
         */
        val DM_BATCH_EXECUTION_DM_FLAG: DmBatchExecutionDmFlag = DmBatchExecutionDmFlag()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<DmBatchExecutionDmFlagRecord> = DmBatchExecutionDmFlagRecord::class.java

    /**
     * The column
     * <code>public.dm_batch_execution_dm_flag.fk_batch_execution_ref</code>.
     */
    val FK_BATCH_EXECUTION_REF: TableField<DmBatchExecutionDmFlagRecord, UUID?> = createField(DSL.name("fk_batch_execution_ref"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>public.dm_batch_execution_dm_flag.fk_flag_ref</code>.
     */
    val FK_FLAG_REF: TableField<DmBatchExecutionDmFlagRecord, String?> = createField(DSL.name("fk_flag_ref"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<DmBatchExecutionDmFlagRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<DmBatchExecutionDmFlagRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<DmBatchExecutionDmFlagRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.dm_batch_execution_dm_flag</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.dm_batch_execution_dm_flag</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.dm_batch_execution_dm_flag</code> table reference
     */
    constructor(): this(DSL.name("dm_batch_execution_dm_flag"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DmBatchExecutionDmFlagRecord>?, parentPath: InverseForeignKey<out Record, DmBatchExecutionDmFlagRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, DM_BATCH_EXECUTION_DM_FLAG, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class DmBatchExecutionDmFlagPath : DmBatchExecutionDmFlag, Path<DmBatchExecutionDmFlagRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DmBatchExecutionDmFlagRecord>?, parentPath: InverseForeignKey<out Record, DmBatchExecutionDmFlagRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<DmBatchExecutionDmFlagRecord>): super(alias, aliased)
        override fun `as`(alias: String): DmBatchExecutionDmFlagPath = DmBatchExecutionDmFlagPath(DSL.name(alias), this)
        override fun `as`(alias: Name): DmBatchExecutionDmFlagPath = DmBatchExecutionDmFlagPath(alias, this)
        override fun `as`(alias: Table<*>): DmBatchExecutionDmFlagPath = DmBatchExecutionDmFlagPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<DmBatchExecutionDmFlagRecord> = DM_BATCH_EXECUTION_DM_FLAG_PKEY
    override fun getReferences(): List<ForeignKey<DmBatchExecutionDmFlagRecord, *>> = listOf(DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_BATCH_EXECUTION_REF_FKEY, DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_FLAG_REF_FKEY)

    private lateinit var _dmBatchExecution: DmBatchExecutionPath

    /**
     * Get the implicit join path to the <code>public.dm_batch_execution</code>
     * table.
     */
    fun dmBatchExecution(): DmBatchExecutionPath {
        if (!this::_dmBatchExecution.isInitialized)
            _dmBatchExecution = DmBatchExecutionPath(this, DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_BATCH_EXECUTION_REF_FKEY, null)

        return _dmBatchExecution;
    }

    val dmBatchExecution: DmBatchExecutionPath
        get(): DmBatchExecutionPath = dmBatchExecution()

    private lateinit var _dmFlag: DmFlagPath

    /**
     * Get the implicit join path to the <code>public.dm_flag</code> table.
     */
    fun dmFlag(): DmFlagPath {
        if (!this::_dmFlag.isInitialized)
            _dmFlag = DmFlagPath(this, DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_FLAG_REF_FKEY, null)

        return _dmFlag;
    }

    val dmFlag: DmFlagPath
        get(): DmFlagPath = dmFlag()
    override fun `as`(alias: String): DmBatchExecutionDmFlag = DmBatchExecutionDmFlag(DSL.name(alias), this)
    override fun `as`(alias: Name): DmBatchExecutionDmFlag = DmBatchExecutionDmFlag(alias, this)
    override fun `as`(alias: Table<*>): DmBatchExecutionDmFlag = DmBatchExecutionDmFlag(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): DmBatchExecutionDmFlag = DmBatchExecutionDmFlag(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): DmBatchExecutionDmFlag = DmBatchExecutionDmFlag(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): DmBatchExecutionDmFlag = DmBatchExecutionDmFlag(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): DmBatchExecutionDmFlag = DmBatchExecutionDmFlag(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): DmBatchExecutionDmFlag = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): DmBatchExecutionDmFlag = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): DmBatchExecutionDmFlag = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): DmBatchExecutionDmFlag = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): DmBatchExecutionDmFlag = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): DmBatchExecutionDmFlag = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): DmBatchExecutionDmFlag = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): DmBatchExecutionDmFlag = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): DmBatchExecutionDmFlag = where(DSL.notExists(select))
}