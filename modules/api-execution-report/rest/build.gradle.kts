plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"
    application
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(project(":modules:api-execution-report:api"))
    implementation(project(":modules:proto"))
    implementation(project(":modules:api-execution-report:domain"))

    implementation("io.ktor:ktor-server-core:2.2.4")
    implementation("io.ktor:ktor-server-netty:2.2.4")
    implementation("io.ktor:ktor-server-content-negotiation:2.2.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.4")
    implementation("ch.qos.logback:logback-classic:1.4.7")

    implementation("io.grpc:grpc-protobuf:1.55.1")
}