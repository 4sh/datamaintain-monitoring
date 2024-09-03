import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm")
    alias(libs.plugins.protobuf)
    `java-library`
}

repositories {
    mavenCentral()
    google()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))
    }
}

dependencies {
    implementation(libs.grpc.protobuf)
    implementation(libs.protobuf.kotlin)
    implementation(libs.grpc.kotlin.stub)
    implementation(libs.grpc.stub) {
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

tasks.withType<Jar>().configureEach {
    archiveBaseName.set("proto-stub")
}

tasks.withType<ProcessResources>().configureEach {
    // Needed because proto generates duplicates
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    plugins {
        id("grpc") { artifact = libs.grpc.protoc.java.get().toString() }
        id("grpckt") {
            artifact = "${libs.grpc.protoc.kotlin.get().toString()}:jdk8@jar"
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