plugins {
    kotlin("jvm")
    id("org.jooq.jooq-codegen-gradle") version "3.19.11"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    }
}

dependencies {
    implementation(libs.jooq)
    implementation(project(":modules:app-server:domain"))
    implementation(libs.postgresql)

    jooqCodegen(libs.postgresql)
    implementation(libs.jakarta.validation.api)

    testImplementation(libs.strikt.core)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)

    testImplementation(libs.testcontainers.testcontainers)
    testImplementation(libs.testcontainers.junit)
    testImplementation(libs.testcontainers.postgresql)
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/datamaintain"
            user = "datamaintain"
            password = "datamaintain"
        }
        generator {
            name = "org.jooq.codegen.KotlinGenerator"
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
            }
            generate {
                isDeprecated = false
                isRecords = true
                isImmutablePojos = true
                isFluentSetters = true
                isImplicitJoinPathsAsKotlinProperties = true
                isKotlinSetterJvmNameAnnotationsOnIsPrefix = true
                isPojosAsKotlinDataClasses = true
                isValidationAnnotations = true
            }
            target {
                packageName = "generated.domain"
                directory = "${project.rootDir}/modules/app-server/dao/src/jooq"

            }
            strategy {
                name = "org.jooq.codegen.DefaultGeneratorStrategy"
            }
        }
    }
}

tasks.named("clean") {
    doLast {
        file("src/jooq").deleteRecursively()
    }
}

tasks.getByPath("test").doFirst {
    with(this as Test) {
        useJUnitPlatform()
    }
}