plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":modules:app-server:rest"))

    implementation(logging())
    implementation(http4k("server-netty"))
}