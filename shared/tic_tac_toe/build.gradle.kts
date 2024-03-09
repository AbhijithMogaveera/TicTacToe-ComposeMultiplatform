plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
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
            baseName = "tic_tac_toe"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            projects.shared.apply {
                implementation(foundationMultiplatformCompose)
                implementation(auth)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        iosMain.dependencies {
            implementation(compose.foundation)
        }
    }
}

android {
    namespace = "com.abhijith.tic_tac_toe"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
