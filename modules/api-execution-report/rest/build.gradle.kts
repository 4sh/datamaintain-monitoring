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
    implementation(project(":modules:api-execution-report:client"))

    implementation("io.ktor:ktor-server-core:2.2.4")
    implementation("io.ktor:ktor-server-netty:2.2.4")
    implementation("io.ktor:ktor-server-content-negotiation:2.2.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.4")
    implementation("ch.qos.logback:logback-classic:1.4.7")

//    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("io.grpc:grpc-protobuf:1.55.1")
//    implementation("com.google.protobuf:protobuf-java-util:3.22.0")
//    implementation("com.google.protobuf:protobuf-kotlin:3.23.0")

//    runtimeOnly("io.grpc:grpc-netty:1.46.0")
}