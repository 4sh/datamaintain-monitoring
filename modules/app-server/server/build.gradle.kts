plugins {
    kotlin("jvm")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    }
}

dependencies {
    implementation(project(":modules:proto"))
    implementation(project(":modules:app-server:domain"))
    implementation(libs.grpc.kotlin.stub)
    implementation(libs.protobuf.kotlin)

    runtimeOnly(libs.grpc.netty)
}
