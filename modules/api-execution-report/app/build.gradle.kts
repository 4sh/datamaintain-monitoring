plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":modules:api-execution-report:rest"))
    implementation(project(":modules:api-execution-report:client"))
    implementation(project(":modules:api-execution-report:domain"))
    implementation(project(":modules:proto"))

    implementation(libs.logging)
    implementation(libs.grpc.protobuf)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
}