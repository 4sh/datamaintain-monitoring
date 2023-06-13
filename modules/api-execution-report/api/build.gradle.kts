plugins {
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"
}

dependencies {
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.4")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }
    }
}