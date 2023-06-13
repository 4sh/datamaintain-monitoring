import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm")
    id("com.google.protobuf") version "0.9.3"
    `java-library`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("io.grpc:grpc-protobuf:1.55.1")
    implementation("com.google.protobuf:protobuf-java-util:3.22.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.23.0")
    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("io.grpc:grpc-stub:1.55.1") {
        because("https://github.com/grpc/grpc-java/issues/8523")
    }
}

kotlin {
    sourceSets.getByName("main").resources.srcDir("src/main/proto")
}

kotlin {
    listOf("kotlin", "grpc", "grpckt").forEach {
        sourceSets.getByName("main").kotlin.srcDir("build/generated/source/proto/main/$it")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}

tasks.withType<Jar>().configureEach {
    archiveBaseName.set("proto-stub")
}

protobuf {
    protoc {
        artifact =
            "com.google.protobuf:protoc:3.23.0"
    }
    plugins {
        id("grpc") {
            artifact =
                "io.grpc:protoc-gen-grpc-java:1.55.1"
        }
        id("grpckt") {
            artifact =
                "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
            it.generateDescriptorSet = true
            it.descriptorSetOptions.includeSourceInfo = true
            it.descriptorSetOptions.includeImports = true
            it.descriptorSetOptions.path =
                "$buildDir/resources/main/META-INF/armeria/grpc/service-name.dsc"
        }
    }
}


