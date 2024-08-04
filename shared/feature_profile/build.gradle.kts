plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.cashsqldelight)
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
            baseName = "tic_tac_toe"
            isStatic = true
            binaryOptions["bundleId"] = "com.shared.profile"
        }
    }
    task("testClasses")
    sourceSets {
        androidMain.dependencies {
            implementation(libs.cashsqldeligh.android)
        }
        commonMain.dependencies {
            implementation(projects.shared.foundationCompose)
            implementation(projects.shared.featureAuth)
            implementation(libs.file.picker)
            implementation(libs.cashsqldeligh.coroutine)
            implementation("io.coil-kt.coil3:coil:3.0.0-alpha01")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        iosMain.dependencies {
            implementation(libs.cashsqldeligh.native)
            implementation(compose.foundation)
        }
    }
}

android {
    namespace = "com.shared.profile"
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
sqldelight {
    databases {
        create("Database") {
            packageName.set("com.shared.profile.sql")
        }
    }
}

