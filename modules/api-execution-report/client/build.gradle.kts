plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(project(":modules:proto"))
    implementation(project(":modules:api-execution-report:domain"))

    implementation(libs.grpc.kotlin.stub)
    implementation(libs.grpc.protobuf)
    implementation(libs.protobuf.java.util)
    implementation(libs.protobuf.kotlin)

    runtimeOnly(libs.grpc.netty)
}
