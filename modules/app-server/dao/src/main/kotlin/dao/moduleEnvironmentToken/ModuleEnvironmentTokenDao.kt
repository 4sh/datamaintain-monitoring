package dao.moduleEnvironmentToken

import dao.jooq.generated.domain.tables.references.DM_MODULE_DM_ENVIRONMENT_TOKEN
import moduleEnvironmentToken.ModuleEnvironmentToken
import moduleEnvironmentToken.ModuleEnvironmentTokenCreationRequest
import moduleEnvironmentToken.ModuleEnvironmentTokenDaoInterface
import org.jooq.DSLContext
import org.jooq.impl.DSL.`val`
import java.util.*

class ModuleEnvironmentTokenDao(val dslContext: DSLContext) : ModuleEnvironmentTokenDaoInterface {
    override fun insert(creationRequest: ModuleEnvironmentTokenCreationRequest): ModuleEnvironmentToken =
        dslContext.insertInto(
            DM_MODULE_DM_ENVIRONMENT_TOKEN,
            DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_MODULE_REF,
            DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_ENVIRONMENT_REF
        ).values(
            `val`(creationRequest.moduleRef),
            `val`(creationRequest.environmentRef)
        ).returning()
            .fetchSingleInto(ModuleEnvironmentToken::class.java)

    override fun getTokenByValue(tokenValue: UUID): ModuleEnvironmentToken? =
        dslContext.fetchOne(DM_MODULE_DM_ENVIRONMENT_TOKEN, DM_MODULE_DM_ENVIRONMENT_TOKEN.TOKEN_VALUE.eq(tokenValue))
            ?.into(ModuleEnvironmentToken::class.java)

    override fun getTokenByModuleAndEnvironmentRef(moduleRef: UUID, environmentRef: UUID): ModuleEnvironmentToken? =
        dslContext.selectFrom(DM_MODULE_DM_ENVIRONMENT_TOKEN)
            .where(
                DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_MODULE_REF.eq(moduleRef)
                    .and(DM_MODULE_DM_ENVIRONMENT_TOKEN.FK_ENVIRONMENT_REF.eq(environmentRef))
            )
            .fetchOneInto(ModuleEnvironmentToken::class.java)

    override fun deleteToken(tokenValue: UUID) {
        dslContext.delete(DM_MODULE_DM_ENVIRONMENT_TOKEN)
            .where(DM_MODULE_DM_ENVIRONMENT_TOKEN.TOKEN_VALUE.eq(tokenValue))
            .execute()
    }
}