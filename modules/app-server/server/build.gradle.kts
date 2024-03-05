plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":modules:proto"))
    implementation(project(":modules:app-server:domain"))
    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.23.0")

    runtimeOnly("io.grpc:grpc-netty:1.46.0")
}
