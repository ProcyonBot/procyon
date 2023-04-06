@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(11)

    target {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += "-Xcontext-receivers"
            }
        }
    }
}

dependencies {
    implementation(libs.kord.core)
    implementation(libs.logback.classic)
    implementation(libs.kaml)

    implementation(libs.bundles.koin)

    implementation(libs.kotlin.reflect)
}