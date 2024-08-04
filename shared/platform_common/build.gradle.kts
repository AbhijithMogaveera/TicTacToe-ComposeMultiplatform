//import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
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
            baseName = "shared"
            isStatic = true
            binaryOptions["bundleId"] = "com.shared.iosApp"
        }
    }
    task("testClasses")
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.featureTicTacToe)
            implementation(projects.shared.featureAuth)
            implementation(projects.shared.featureProfile)

            implementation(projects.shared.foundationKotlin)
            implementation(projects.shared.foundationCompose)

            implementation(libs.koin.core)
        }
    }
    androidTarget()
}

android {
    namespace = "com.shared.iosApp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}