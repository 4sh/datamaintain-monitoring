plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":modules:api-execution-report"))

    implementation(logging())
    implementation(http4k("core"))
    implementation(http4k("contract"))
    implementation(http4k("format-jackson"))
}