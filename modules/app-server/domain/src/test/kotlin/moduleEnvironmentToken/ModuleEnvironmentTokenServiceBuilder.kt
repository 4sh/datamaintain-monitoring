package moduleEnvironmentToken

fun buildModuleEnvironmentTokenService(
    moduleEnvironmentTokenDao: ModuleEnvironmentTokenDaoInterface = FakeModuleEnvironmentTokenDao()
) = ModuleEnvironmentTokenService(dao = moduleEnvironmentTokenDao)