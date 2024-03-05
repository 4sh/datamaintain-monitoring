/*
 * This file is generated by jOOQ.
 */
package generated.domain.indexes


import generated.domain.tables.DmModuleDmEnvironmentToken

import org.jooq.Index
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// INDEX definitions
// -------------------------------------------------------------------------

val MODULE_ENVIRONMENT: Index = Internal.createIndex(DSL.name("module_environment"), DmModuleDmEnvironmentToken.DM_MODULE_DM_ENVIRONMENT_TOKEN, arrayOf(DmModuleDmEnvironmentToken.DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_MODULE_REF, DmModuleDmEnvironmentToken.DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_ENVIRONMENT_REF), true)