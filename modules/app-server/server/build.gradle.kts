plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":modules:proto"))
    implementation("io.grpc:grpc-kotlin-stub:1.3.0")

    runtimeOnly("io.grpc:grpc-netty:1.46.0")
}
