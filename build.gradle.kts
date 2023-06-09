@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.symbol.processing)
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(11)

    target {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += "-Xcontext-receivers "
                freeCompilerArgs += "-opt-in=org.komapper.annotation.KomapperExperimentalAssociation "
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

    platform("org.komapper:komapper-platform:1.10.0").let { // TODO: How to implement this in the toml file?
        implementation(it)
        ksp(it)
    }
    ksp("org.komapper:komapper-processor")

    implementation(libs.komapper.starter.r2dbc)
    implementation(libs.komapper.dialect.h2.r2dbc)
}