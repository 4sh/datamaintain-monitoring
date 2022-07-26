plugins {
    id("org.jetbrains.kotlin.jvm")
}

appProject()

dependencies {
    implementation(project(":modules:api-execution-report:rest"))
}