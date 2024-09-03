/*
 * This file is generated by jOOQ.
 */
package dao.jooq.generated.domain.keys


import dao.jooq.generated.domain.tables.DmBatchExecution
import dao.jooq.generated.domain.tables.DmBatchExecutionDmFlag
import dao.jooq.generated.domain.tables.DmEnvironment
import dao.jooq.generated.domain.tables.DmFlag
import dao.jooq.generated.domain.tables.DmModule
import dao.jooq.generated.domain.tables.DmModuleDmEnvironmentToken
import dao.jooq.generated.domain.tables.DmProject
import dao.jooq.generated.domain.tables.DmScript
import dao.jooq.generated.domain.tables.DmScriptExecution
import dao.jooq.generated.domain.tables.DmScriptExecutionDmTag
import dao.jooq.generated.domain.tables.DmTag
import dao.jooq.generated.domain.tables.records.DmBatchExecutionDmFlagRecord
import dao.jooq.generated.domain.tables.records.DmBatchExecutionRecord
import dao.jooq.generated.domain.tables.records.DmEnvironmentRecord
import dao.jooq.generated.domain.tables.records.DmFlagRecord
import dao.jooq.generated.domain.tables.records.DmModuleDmEnvironmentTokenRecord
import dao.jooq.generated.domain.tables.records.DmModuleRecord
import dao.jooq.generated.domain.tables.records.DmProjectRecord
import dao.jooq.generated.domain.tables.records.DmScriptExecutionDmTagRecord
import dao.jooq.generated.domain.tables.records.DmScriptExecutionRecord
import dao.jooq.generated.domain.tables.records.DmScriptRecord
import dao.jooq.generated.domain.tables.records.DmTagRecord

import org.jooq.ForeignKey
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val DM_BATCH_EXECUTION_PKEY: UniqueKey<DmBatchExecutionRecord> = Internal.createUniqueKey(DmBatchExecution.DM_BATCH_EXECUTION, DSL.name("dm_batch_execution_pkey"), arrayOf(DmBatchExecution.DM_BATCH_EXECUTION.ID), true)
val DM_BATCH_EXECUTION_DM_FLAG_PKEY: UniqueKey<DmBatchExecutionDmFlagRecord> = Internal.createUniqueKey(DmBatchExecutionDmFlag.DM_BATCH_EXECUTION_DM_FLAG, DSL.name("dm_batch_execution_dm_flag_pkey"), arrayOf(DmBatchExecutionDmFlag.DM_BATCH_EXECUTION_DM_FLAG.FK_BATCH_EXECUTION_REF, DmBatchExecutionDmFlag.DM_BATCH_EXECUTION_DM_FLAG.FK_FLAG_REF), true)
val DM_ENVIRONMENT_PKEY: UniqueKey<DmEnvironmentRecord> = Internal.createUniqueKey(DmEnvironment.DM_ENVIRONMENT, DSL.name("dm_environment_pkey"), arrayOf(DmEnvironment.DM_ENVIRONMENT.ID), true)
val DM_FLAG_PKEY: UniqueKey<DmFlagRecord> = Internal.createUniqueKey(DmFlag.DM_FLAG, DSL.name("dm_flag_pkey"), arrayOf(DmFlag.DM_FLAG.NAME), true)
val DM_MODULE_PKEY: UniqueKey<DmModuleRecord> = Internal.createUniqueKey(DmModule.DM_MODULE, DSL.name("dm_module_pkey"), arrayOf(DmModule.DM_MODULE.ID), true)
val DM_MODULE_DM_ENVIRONMENT_TOKEN_PKEY: UniqueKey<DmModuleDmEnvironmentTokenRecord> = Internal.createUniqueKey(DmModuleDmEnvironmentToken.DM_MODULE_DM_ENVIRONMENT_TOKEN, DSL.name("dm_module_dm_environment_token_pkey"), arrayOf(DmModuleDmEnvironmentToken.DM_MODULE_DM_ENVIRONMENT_TOKEN.TOKEN_VALUE), true)
val DM_PROJECT_PKEY: UniqueKey<DmProjectRecord> = Internal.createUniqueKey(DmProject.DM_PROJECT, DSL.name("dm_project_pkey"), arrayOf(DmProject.DM_PROJECT.ID), true)
val DM_SCRIPT_PKEY: UniqueKey<DmScriptRecord> = Internal.createUniqueKey(DmScript.DM_SCRIPT, DSL.name("dm_script_pkey"), arrayOf(DmScript.DM_SCRIPT.CHECKSUM), true)
val DM_SCRIPT_EXECUTION_PKEY: UniqueKey<DmScriptExecutionRecord> = Internal.createUniqueKey(DmScriptExecution.DM_SCRIPT_EXECUTION, DSL.name("dm_script_execution_pkey"), arrayOf(DmScriptExecution.DM_SCRIPT_EXECUTION.ID), true)
val DM_SCRIPT_EXECUTION_DM_TAG_PKEY: UniqueKey<DmScriptExecutionDmTagRecord> = Internal.createUniqueKey(DmScriptExecutionDmTag.DM_SCRIPT_EXECUTION_DM_TAG, DSL.name("dm_script_execution_dm_tag_pkey"), arrayOf(DmScriptExecutionDmTag.DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF, DmScriptExecutionDmTag.DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF), true)
val DM_TAG_PKEY: UniqueKey<DmTagRecord> = Internal.createUniqueKey(DmTag.DM_TAG, DSL.name("dm_tag_pkey"), arrayOf(DmTag.DM_TAG.NAME), true)

// -------------------------------------------------------------------------
// FOREIGN KEY definitions
// -------------------------------------------------------------------------

val DM_BATCH_EXECUTION__DM_BATCH_EXECUTION_FK_ENVIRONMENT_REF_FKEY: ForeignKey<DmBatchExecutionRecord, DmEnvironmentRecord> = Internal.createForeignKey(DmBatchExecution.DM_BATCH_EXECUTION, DSL.name("dm_batch_execution_fk_environment_ref_fkey"), arrayOf(DmBatchExecution.DM_BATCH_EXECUTION.FK_ENVIRONMENT_REF), dao.jooq.generated.domain.keys.DM_ENVIRONMENT_PKEY, arrayOf(DmEnvironment.DM_ENVIRONMENT.ID), true)
val DM_BATCH_EXECUTION__DM_BATCH_EXECUTION_FK_MODULE_REF_FKEY: ForeignKey<DmBatchExecutionRecord, DmModuleRecord> = Internal.createForeignKey(DmBatchExecution.DM_BATCH_EXECUTION, DSL.name("dm_batch_execution_fk_module_ref_fkey"), arrayOf(DmBatchExecution.DM_BATCH_EXECUTION.FK_MODULE_REF), dao.jooq.generated.domain.keys.DM_MODULE_PKEY, arrayOf(DmModule.DM_MODULE.ID), true)
val DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_BATCH_EXECUTION_REF_FKEY: ForeignKey<DmBatchExecutionDmFlagRecord, DmBatchExecutionRecord> = Internal.createForeignKey(DmBatchExecutionDmFlag.DM_BATCH_EXECUTION_DM_FLAG, DSL.name("dm_batch_execution_dm_flag_fk_batch_execution_ref_fkey"), arrayOf(DmBatchExecutionDmFlag.DM_BATCH_EXECUTION_DM_FLAG.FK_BATCH_EXECUTION_REF), dao.jooq.generated.domain.keys.DM_BATCH_EXECUTION_PKEY, arrayOf(DmBatchExecution.DM_BATCH_EXECUTION.ID), true)
val DM_BATCH_EXECUTION_DM_FLAG__DM_BATCH_EXECUTION_DM_FLAG_FK_FLAG_REF_FKEY: ForeignKey<DmBatchExecutionDmFlagRecord, DmFlagRecord> = Internal.createForeignKey(DmBatchExecutionDmFlag.DM_BATCH_EXECUTION_DM_FLAG, DSL.name("dm_batch_execution_dm_flag_fk_flag_ref_fkey"), arrayOf(DmBatchExecutionDmFlag.DM_BATCH_EXECUTION_DM_FLAG.FK_FLAG_REF), dao.jooq.generated.domain.keys.DM_FLAG_PKEY, arrayOf(DmFlag.DM_FLAG.NAME), true)
val DM_ENVIRONMENT__DM_ENVIRONMENT_FK_PROJECT_REF_FKEY: ForeignKey<DmEnvironmentRecord, DmProjectRecord> = Internal.createForeignKey(DmEnvironment.DM_ENVIRONMENT, DSL.name("dm_environment_fk_project_ref_fkey"), arrayOf(DmEnvironment.DM_ENVIRONMENT.FK_PROJECT_REF), dao.jooq.generated.domain.keys.DM_PROJECT_PKEY, arrayOf(DmProject.DM_PROJECT.ID), true)
val DM_MODULE__DM_MODULE_FK_PROJECT_REF_FKEY: ForeignKey<DmModuleRecord, DmProjectRecord> = Internal.createForeignKey(DmModule.DM_MODULE, DSL.name("dm_module_fk_project_ref_fkey"), arrayOf(DmModule.DM_MODULE.FK_PROJECT_REF), dao.jooq.generated.domain.keys.DM_PROJECT_PKEY, arrayOf(DmProject.DM_PROJECT.ID), true)
val DM_MODULE_DM_ENVIRONMENT_TOKEN__DM_MODULE_DM_ENVIRONMENT_TOKEN_FK_ENVIRONMENT_REF_FKEY: ForeignKey<DmModuleDmEnvironmentTokenRecord, DmEnvironmentRecord> = Internal.createForeignKey(DmModuleDmEnvironmentToken.DM_MODULE_DM_ENVIRONMENT_TOKEN, DSL.name("dm_module_dm_environment_token_fk_environment_ref_fkey"), arrayOf(DmModuleDmEnvironmentToken.DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_ENVIRONMENT_REF), dao.jooq.generated.domain.keys.DM_ENVIRONMENT_PKEY, arrayOf(DmEnvironment.DM_ENVIRONMENT.ID), true)
val DM_MODULE_DM_ENVIRONMENT_TOKEN__DM_MODULE_DM_ENVIRONMENT_TOKEN_FK_MODULE_REF_FKEY: ForeignKey<DmModuleDmEnvironmentTokenRecord, DmModuleRecord> = Internal.createForeignKey(DmModuleDmEnvironmentToken.DM_MODULE_DM_ENVIRONMENT_TOKEN, DSL.name("dm_module_dm_environment_token_fk_module_ref_fkey"), arrayOf(DmModuleDmEnvironmentToken.DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_MODULE_REF), dao.jooq.generated.domain.keys.DM_MODULE_PKEY, arrayOf(DmModule.DM_MODULE.ID), true)
val DM_SCRIPT_EXECUTION__DM_SCRIPT_EXECUTION_FK_BATCH_EXECUTION_REF_FKEY: ForeignKey<DmScriptExecutionRecord, DmBatchExecutionRecord> = Internal.createForeignKey(DmScriptExecution.DM_SCRIPT_EXECUTION, DSL.name("dm_script_execution_fk_batch_execution_ref_fkey"), arrayOf(DmScriptExecution.DM_SCRIPT_EXECUTION.FK_BATCH_EXECUTION_REF), dao.jooq.generated.domain.keys.DM_BATCH_EXECUTION_PKEY, arrayOf(DmBatchExecution.DM_BATCH_EXECUTION.ID), true)
val DM_SCRIPT_EXECUTION__DM_SCRIPT_EXECUTION_FK_SCRIPT_REF_FKEY: ForeignKey<DmScriptExecutionRecord, DmScriptRecord> = Internal.createForeignKey(DmScriptExecution.DM_SCRIPT_EXECUTION, DSL.name("dm_script_execution_fk_script_ref_fkey"), arrayOf(DmScriptExecution.DM_SCRIPT_EXECUTION.FK_SCRIPT_REF), dao.jooq.generated.domain.keys.DM_SCRIPT_PKEY, arrayOf(DmScript.DM_SCRIPT.CHECKSUM), true)
val DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_SCRIPT_EXECUTION_REF_FKEY: ForeignKey<DmScriptExecutionDmTagRecord, DmScriptExecutionRecord> = Internal.createForeignKey(DmScriptExecutionDmTag.DM_SCRIPT_EXECUTION_DM_TAG, DSL.name("dm_script_execution_dm_tag_fk_script_execution_ref_fkey"), arrayOf(DmScriptExecutionDmTag.DM_SCRIPT_EXECUTION_DM_TAG.FK_SCRIPT_EXECUTION_REF), dao.jooq.generated.domain.keys.DM_SCRIPT_EXECUTION_PKEY, arrayOf(DmScriptExecution.DM_SCRIPT_EXECUTION.ID), true)
val DM_SCRIPT_EXECUTION_DM_TAG__DM_SCRIPT_EXECUTION_DM_TAG_FK_TAG_REF_FKEY: ForeignKey<DmScriptExecutionDmTagRecord, DmTagRecord> = Internal.createForeignKey(DmScriptExecutionDmTag.DM_SCRIPT_EXECUTION_DM_TAG, DSL.name("dm_script_execution_dm_tag_fk_tag_ref_fkey"), arrayOf(DmScriptExecutionDmTag.DM_SCRIPT_EXECUTION_DM_TAG.FK_TAG_REF), dao.jooq.generated.domain.keys.DM_TAG_PKEY, arrayOf(DmTag.DM_TAG.NAME), true)