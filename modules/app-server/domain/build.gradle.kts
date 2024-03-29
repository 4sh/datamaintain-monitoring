import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.strikt:strikt-core:0.34.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("io.mockk:mockk:1.12.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.7.0")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = Versions.java
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = Versions.java
}

tasks.getByPath("test").doFirst {
    with(this as Test) {
        useJUnitPlatform()
    }
}