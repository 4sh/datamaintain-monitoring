plugins {
    id("org.jetbrains.kotlin.jvm")
}

appProject()

dependencies {
    implementation(project(":modules:app-server:rest"))

    implementation("io.ktor:ktor-server-netty:2.2.4")
    implementation("org.jooq:jooq:3.18.2")
    implementation(project(":modules:app-server:dao"))
    implementation(project(":modules:app-server:domain"))
    implementation("org.postgresql:postgresql:42.5.1")
}