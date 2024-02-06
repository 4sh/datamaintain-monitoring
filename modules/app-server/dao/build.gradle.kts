plugins {
    kotlin("jvm")
    id("org.jooq.jooq-codegen-gradle") version "3.19.3"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jooq:jooq:3.19.3")
    implementation(project(":modules:app-server:domain"))
    implementation("org.postgresql:postgresql:42.5.1")

    jooqCodegen("org.postgresql:postgresql:42.5.1")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    testImplementation("io.strikt:strikt-core:0.34.0")
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
            generate {
                isDeprecated = false
                isRecords = true
                isImmutablePojos = true
                isFluentSetters = true
                isImplicitJoinPathsAsKotlinProperties = true
                isKotlinSetterJvmNameAnnotationsOnIsPrefix = true
                isPojosAsKotlinDataClasses = true
                isValidationAnnotations = true
                isKotlinNotNullPojoAttributes = true
            }

            name =  "org.jooq.codegen.KotlinGenerator"
            target {
                packageName = "generated.domain"
                directory = "${project.rootDir}/modules/app-server/dao/src/jooq"
            }

            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
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