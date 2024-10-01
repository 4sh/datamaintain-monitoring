plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    }
}

dependencies {
    implementation(project(":modules:app-server:rest"))

    implementation(libs.logging)
    implementation(libs.ktor.server.netty)
    implementation(libs.jooq)
    implementation(project(":modules:app-server:dao"))
    implementation(project(":modules:app-server:domain"))
    implementation(libs.postgresql)
    implementation(project(":modules:app-server:server"))
}

application {
    mainClass.set("app.server.app.AppServerKt")
}
