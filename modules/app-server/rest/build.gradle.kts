plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation("io.github.microutils:kotlin-logging:1.8.0.1")

    implementation(project(":modules:api-execution-report"))
    implementation("org.http4k:http4k-core:4.3.4.1")
    implementation("org.http4k:http4k-contract:4.3.4.1")
    implementation("org.http4k:http4k-format-jackson:4.3.4.1")
}