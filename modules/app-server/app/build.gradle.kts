plugins {
    id("org.jetbrains.kotlin.jvm")
}

appProject()

dependencies {
    implementation(project(":modules:app-server:rest"))

    implementation("io.ktor:ktor-server-netty:2.2.4")
}