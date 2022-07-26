plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":modules:app-server:rest"))

    implementation("io.github.microutils:kotlin-logging:1.8.0.1")
    implementation("org.http4k:http4k-server-netty:4.3.4.1")
}