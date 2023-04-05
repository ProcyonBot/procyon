@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(libs.kord.core)
    implementation(libs.dotenv.kotlin)
    implementation(libs.logback.classic)

    implementation(libs.koin.core)
    implementation(libs.koin.core.coroutines)
    implementation(kotlin("reflect"))
}