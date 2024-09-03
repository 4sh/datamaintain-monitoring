plugins {
    kotlin("jvm")
    id("org.jooq.jooq-codegen-gradle") version "3.19.11"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("org.jooq:jooq:3.19.11")
    implementation(project(":modules:app-server:domain"))
    implementation("org.postgresql:postgresql:42.5.1")

    jooqCodegen("org.postgresql:postgresql:42.5.1")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    testImplementation("io.strikt:strikt-core:0.34.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.7.0")

    testImplementation("org.testcontainers:testcontainers:1.18.0")
    testImplementation("org.testcontainers:junit-jupiter:1.18.0")
    testImplementation("org.testcontainers:postgresql:1.18.0")
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

tasks.named("compileKotlin") {
    dependsOn(tasks.named("jooqCodegen"))
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