plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation("io.github.microutils:kotlin-logging:1.8.0.1")

    implementation(project(":modules:api-execution-report"))
    implementation(http4k("core"))
    implementation(http4k("contract"))
    implementation(http4k("format-jackson"))
}