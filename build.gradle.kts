plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.palantir.git)
    application
}

group = "io.github.4sh.datamaintain-monitoring"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

allprojects {
    apply<com.palantir.gradle.gitversion.GitVersionPlugin>()

    val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
    val lastTag = versionDetails().lastTag

    val computedVersion: String = if (lastTag != "") {
        lastTag
    } else {
        "SNAPSHOT"
    }

    group = "io.github.4sh.datamaintain-monitoring"
    version = computedVersion

    repositories {
        mavenCentral()
    }
}