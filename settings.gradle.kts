rootProject.name = "datamaintain-monitoring"

include(
    "modules:api-execution-report:api",
    "modules:api-execution-report:rest",
    "modules:api-execution-report:app",
    "modules:api-execution-report:client",

    "modules:app-server:domain",
    "modules:app-server:app",
    "modules:app-server:rest",
    "modules:app-server:dao",
    "modules:app-server:server",

    "modules:proto"
)