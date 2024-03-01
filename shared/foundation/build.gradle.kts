plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("kotlin-kapt")
    kotlin("plugin.serialization")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "foundation"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            api(libs.ktor.client.okhttp)
            api(libs.kotlinx.coroutines.android)
            api(libs.core.ktx)
            api(libs.appcompat)
            api(libs.material)
            api("androidx.compose.ui:ui:1.5.4")
            api("androidx.compose.ui:ui-tooling-preview:1.5.4")
            api(libs.compose.material3)
            api(libs.androidx.activity.compose)
            val nav_version = "2.7.6"
            api("androidx.navigation:navigation-compose:$nav_version")
            api("io.coil-kt:coil-compose:2.5.0")
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            api(libs.ktor.client.core)
            api(libs.ktor.client.logging)
            api(libs.kotlinx.coroutines.core)
            api(libs.arrow.core)
            api(libs.arrow.fx.corutine)
            api(libs.ktor.serialization.kotlinx.json)
            api(libs.ktor.client.content.negotiation)
            api(libs.koin.core)
            api("com.rickclephas.kmm:kmm-viewmodel-core:1.0.0-ALPHA-19")
        }
        iosMain.dependencies {
            api(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

android {
    namespace = "com.abhijith.foundation"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

