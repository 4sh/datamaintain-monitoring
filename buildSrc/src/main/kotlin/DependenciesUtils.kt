import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

fun http4k(module: String) = "org.http4k:http4k-$module:${Versions.http4k}"
fun logging() = "io.github.microutils:kotlin-logging:${Versions.logging}"

fun Project.restProject() {
    dependencies {
        "implementation"(http4k("core"))
        "implementation"(http4k("contract"))
        "implementation"(http4k("format-jackson"))
        "implementation"(logging())
    }
}

fun Project.appProject() {
    dependencies {
        "implementation"(logging())
        "implementation"(http4k("server-netty"))
    }
}