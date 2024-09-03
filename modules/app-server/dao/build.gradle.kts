plugins {
    kotlin("jvm")
    id("nu.studer.jooq") version "8.1"
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
    implementation("org.jooq:jooq:3.18.2")
    implementation(project(":modules:app-server:domain"))
    implementation("org.postgresql:postgresql:42.5.1")

    jooqGenerator("org.postgresql:postgresql:42.5.1")
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
    configurations {
        create("main") {  // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/datamaintain"
                    user = "datamaintain"
                    password = "datamaintain"
                }
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                        isImplicitJoinPathsAsKotlinProperties = true
                        isKotlinSetterJvmNameAnnotationsOnIsPrefix = true
                        isPojosAsKotlinDataClasses = true
                        isValidationAnnotations = true
                    }
                    target.apply {
                        packageName = "generated.domain"
                        directory = "${project.rootDir}/modules/app-server/dao/src/jooq"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

buildscript {
    configurations["classpath"].resolutionStrategy.eachDependency {
        if (requested.group == "org.jooq") {
            useVersion("3.19.11")
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