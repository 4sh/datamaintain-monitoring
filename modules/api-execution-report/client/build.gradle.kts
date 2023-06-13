plugins {
    kotlin("jvm")
    application
}

repositories {

}

dependencies {
    implementation(project(":modules:proto"))

    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("io.grpc:grpc-protobuf:1.55.1")
    implementation("com.google.protobuf:protobuf-java-util:3.22.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.23.0")

    runtimeOnly("io.grpc:grpc-netty:1.46.0")
}
