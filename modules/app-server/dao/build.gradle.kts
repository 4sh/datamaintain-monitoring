plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq:3.18.2")
    implementation(project(":modules:app-server:domain"))
}