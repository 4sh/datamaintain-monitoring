plugins {
    id("org.jetbrains.kotlin.jvm")
}

appProject()

dependencies {
    implementation(project(":modules:api-execution-report:rest"))
    implementation(project(":modules:api-execution-report:client"))
    implementation(project(":modules:api-execution-report:domain"))
    implementation(project(":modules:proto"))

    implementation("io.grpc:grpc-protobuf:1.55.1")

    implementation("io.ktor:ktor-server-core:2.2.4")
    implementation("io.ktor:ktor-server-netty:2.2.4")
}