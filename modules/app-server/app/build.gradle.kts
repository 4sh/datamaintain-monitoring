plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":modules:app-server:rest"))

    implementation("io.github.microutils:kotlin-logging:1.8.0.1")
    implementation(http4k("server-netty"))
}