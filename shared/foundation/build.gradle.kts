plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("kotlin-kapt")
    kotlin("plugin.serialization")
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
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
            binaryOptions["bundleId"] = "com.shared.foundation"
        }
    }
    task("testClasses")
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            api(libs.ktor.client.okhttp)
            api(libs.kotlinx.coroutines.android)
            api(libs.core.ktx)
            api(libs.appcompat)
            api(libs.material)
            api(libs.androidx.ui.v154)
            api(libs.androidx.ui.tooling.preview.v154)
            api(libs.compose.material3)
            api(libs.androidx.activity.compose)
            val nav_version = "2.7.6"
            api(libs.androidx.navigation.compose.v276)
//            api(libs.coil.compose.v250)
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
            api(libs.kmm.viewmodel.core)
            implementation(libs.kotlinx.serialization.json)
            api(libs.kotlinx.datetime)
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
    namespace = "com.shared.foundation"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}