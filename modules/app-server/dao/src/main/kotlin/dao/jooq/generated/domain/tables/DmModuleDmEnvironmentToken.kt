/*
 * This file is generated by jOOQ.
 */
package dao.jooq.generated.domain.tables


import dao.jooq.generated.domain.Public
import dao.jooq.generated.domain.indexes.MODULE_ENVIRONMENT
import dao.jooq.generated.domain.keys.DM_MODULE_DM_ENVIRONMENT_TOKEN_PKEY
import dao.jooq.generated.domain.keys.DM_MODULE_DM_ENVIRONMENT_TOKEN__DM_MODULE_DM_ENVIRONMENT_TOKEN_FK_ENVIRONMENT_REF_FKEY
import dao.jooq.generated.domain.keys.DM_MODULE_DM_ENVIRONMENT_TOKEN__DM_MODULE_DM_ENVIRONMENT_TOKEN_FK_MODULE_REF_FKEY
import dao.jooq.generated.domain.tables.DmEnvironment.DmEnvironmentPath
import dao.jooq.generated.domain.tables.DmModule.DmModulePath
import dao.jooq.generated.domain.tables.records.DmModuleDmEnvironmentTokenRecord

import java.util.UUID

import kotlin.collections.Collection
import kotlin.collections.List

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
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
open class DmModuleDmEnvironmentToken(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, DmModuleDmEnvironmentTokenRecord>?,
    parentPath: InverseForeignKey<out Record, DmModuleDmEnvironmentTokenRecord>?,
    aliased: Table<DmModuleDmEnvironmentTokenRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<DmModuleDmEnvironmentTokenRecord>(
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
         * <code>public.dm_module_dm_environment_token</code>
         */
        val DM_MODULE_DM_ENVIRONMENT_TOKEN: DmModuleDmEnvironmentToken = DmModuleDmEnvironmentToken()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<DmModuleDmEnvironmentTokenRecord> = DmModuleDmEnvironmentTokenRecord::class.java

    /**
     * The column
     * <code>public.dm_module_dm_environment_token.fk_module_ref</code>.
     */
    val FK_MODULE_REF: TableField<DmModuleDmEnvironmentTokenRecord, UUID?> = createField(DSL.name("fk_module_ref"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>public.dm_module_dm_environment_token.fk_environment_ref</code>.
     */
    val FK_ENVIRONMENT_REF: TableField<DmModuleDmEnvironmentTokenRecord, UUID?> = createField(DSL.name("fk_environment_ref"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>public.dm_module_dm_environment_token.token_value</code>.
     */
    val TOKEN_VALUE: TableField<DmModuleDmEnvironmentTokenRecord, UUID?> = createField(DSL.name("token_value"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "")

    private constructor(alias: Name, aliased: Table<DmModuleDmEnvironmentTokenRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<DmModuleDmEnvironmentTokenRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<DmModuleDmEnvironmentTokenRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>public.dm_module_dm_environment_token</code>
     * table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.dm_module_dm_environment_token</code>
     * table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.dm_module_dm_environment_token</code> table
     * reference
     */
    constructor(): this(DSL.name("dm_module_dm_environment_token"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DmModuleDmEnvironmentTokenRecord>?, parentPath: InverseForeignKey<out Record, DmModuleDmEnvironmentTokenRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, DM_MODULE_DM_ENVIRONMENT_TOKEN, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class DmModuleDmEnvironmentTokenPath : DmModuleDmEnvironmentToken, Path<DmModuleDmEnvironmentTokenRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DmModuleDmEnvironmentTokenRecord>?, parentPath: InverseForeignKey<out Record, DmModuleDmEnvironmentTokenRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<DmModuleDmEnvironmentTokenRecord>): super(alias, aliased)
        override fun `as`(alias: String): DmModuleDmEnvironmentTokenPath = DmModuleDmEnvironmentTokenPath(DSL.name(alias), this)
        override fun `as`(alias: Name): DmModuleDmEnvironmentTokenPath = DmModuleDmEnvironmentTokenPath(alias, this)
        override fun `as`(alias: Table<*>): DmModuleDmEnvironmentTokenPath = DmModuleDmEnvironmentTokenPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getIndexes(): List<Index> = listOf(MODULE_ENVIRONMENT)
    override fun getPrimaryKey(): UniqueKey<DmModuleDmEnvironmentTokenRecord> = DM_MODULE_DM_ENVIRONMENT_TOKEN_PKEY
    override fun getReferences(): List<ForeignKey<DmModuleDmEnvironmentTokenRecord, *>> = listOf(DM_MODULE_DM_ENVIRONMENT_TOKEN__DM_MODULE_DM_ENVIRONMENT_TOKEN_FK_ENVIRONMENT_REF_FKEY, DM_MODULE_DM_ENVIRONMENT_TOKEN__DM_MODULE_DM_ENVIRONMENT_TOKEN_FK_MODULE_REF_FKEY)

    private lateinit var _dmEnvironment: DmEnvironmentPath

    /**
     * Get the implicit join path to the <code>public.dm_environment</code>
     * table.
     */
    fun dmEnvironment(): DmEnvironmentPath {
        if (!this::_dmEnvironment.isInitialized)
            _dmEnvironment = DmEnvironmentPath(this, DM_MODULE_DM_ENVIRONMENT_TOKEN__DM_MODULE_DM_ENVIRONMENT_TOKEN_FK_ENVIRONMENT_REF_FKEY, null)

        return _dmEnvironment;
    }

    val dmEnvironment: DmEnvironmentPath
        get(): DmEnvironmentPath = dmEnvironment()

    private lateinit var _dmModule: DmModulePath

    /**
     * Get the implicit join path to the <code>public.dm_module</code> table.
     */
    fun dmModule(): DmModulePath {
        if (!this::_dmModule.isInitialized)
            _dmModule = DmModulePath(this, DM_MODULE_DM_ENVIRONMENT_TOKEN__DM_MODULE_DM_ENVIRONMENT_TOKEN_FK_MODULE_REF_FKEY, null)

        return _dmModule;
    }

    val dmModule: DmModulePath
        get(): DmModulePath = dmModule()
    override fun `as`(alias: String): DmModuleDmEnvironmentToken = DmModuleDmEnvironmentToken(DSL.name(alias), this)
    override fun `as`(alias: Name): DmModuleDmEnvironmentToken = DmModuleDmEnvironmentToken(alias, this)
    override fun `as`(alias: Table<*>): DmModuleDmEnvironmentToken = DmModuleDmEnvironmentToken(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): DmModuleDmEnvironmentToken = DmModuleDmEnvironmentToken(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): DmModuleDmEnvironmentToken = DmModuleDmEnvironmentToken(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): DmModuleDmEnvironmentToken = DmModuleDmEnvironmentToken(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): DmModuleDmEnvironmentToken = DmModuleDmEnvironmentToken(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): DmModuleDmEnvironmentToken = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): DmModuleDmEnvironmentToken = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): DmModuleDmEnvironmentToken = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): DmModuleDmEnvironmentToken = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): DmModuleDmEnvironmentToken = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): DmModuleDmEnvironmentToken = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): DmModuleDmEnvironmentToken = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): DmModuleDmEnvironmentToken = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): DmModuleDmEnvironmentToken = where(DSL.notExists(select))
}