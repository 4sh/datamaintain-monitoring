plugins {
    id("org.jetbrains.kotlin.jvm")
}

appProject()

dependencies {
    implementation(project(":modules:app-server:rest"))
}