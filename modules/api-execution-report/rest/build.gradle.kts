plugins {
    id("org.jetbrains.kotlin.jvm")
}

restProject()

dependencies {
    implementation(project(":modules:api-execution-report:api"))
}