/*
 * This file is generated by jOOQ.
 */
package generated.domain


import generated.domain.tables.DmBatchExecution
import generated.domain.tables.DmEnvironment
import generated.domain.tables.DmModule
import generated.domain.tables.DmProject
import generated.domain.tables.DmScript
import generated.domain.tables.DmScriptExecution
import generated.domain.tables.DmScriptExecutionDmTag
import generated.domain.tables.DmTag

import kotlin.collections.List

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Public : SchemaImpl("public", DefaultCatalog.DEFAULT_CATALOG) {
    public companion object {

        /**
         * The reference instance of <code>public</code>
         */
        val PUBLIC: Public = Public()
    }

    /**
     * The table <code>public.dm_batch_execution</code>.
     */
    val DM_BATCH_EXECUTION: DmBatchExecution get() = DmBatchExecution.DM_BATCH_EXECUTION

    /**
     * The table <code>public.dm_environment</code>.
     */
    val DM_ENVIRONMENT: DmEnvironment get() = DmEnvironment.DM_ENVIRONMENT

    /**
     * The table <code>public.dm_module</code>.
     */
    val DM_MODULE: DmModule get() = DmModule.DM_MODULE

    /**
     * The table <code>public.dm_project</code>.
     */
    val DM_PROJECT: DmProject get() = DmProject.DM_PROJECT

    /**
     * The table <code>public.dm_script</code>.
     */
    val DM_SCRIPT: DmScript get() = DmScript.DM_SCRIPT

    /**
     * The table <code>public.dm_script_execution</code>.
     */
    val DM_SCRIPT_EXECUTION: DmScriptExecution get() = DmScriptExecution.DM_SCRIPT_EXECUTION

    /**
     * The table <code>public.dm_script_execution_dm_tag</code>.
     */
    val DM_SCRIPT_EXECUTION_DM_TAG: DmScriptExecutionDmTag get() = DmScriptExecutionDmTag.DM_SCRIPT_EXECUTION_DM_TAG

    /**
     * The table <code>public.dm_tag</code>.
     */
    val DM_TAG: DmTag get() = DmTag.DM_TAG

    public override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    public override fun getTables(): List<Table<*>> = listOf(
        DmBatchExecution.DM_BATCH_EXECUTION,
        DmEnvironment.DM_ENVIRONMENT,
        DmModule.DM_MODULE,
        DmProject.DM_PROJECT,
        DmScript.DM_SCRIPT,
        DmScriptExecution.DM_SCRIPT_EXECUTION,
        DmScriptExecutionDmTag.DM_SCRIPT_EXECUTION_DM_TAG,
        DmTag.DM_TAG
    )
}