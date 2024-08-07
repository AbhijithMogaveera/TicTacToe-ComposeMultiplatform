plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "auth"
            isStatic = true
            binaryOptions["bundleId"] = "com.shared.auth"
        }
    }
    task("testClasses")
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.compose.android)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(projects.shared.foundationKotlin)
            implementation(projects.shared.libUiComponents)
            implementation(libs.koin.core)
            implementation(projects.shared.foundationCompose)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(compose.components.resources)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
    androidTarget{}
}

android {
    namespace = "com.shared.auth"
    compileSdk = 34
    defaultConfig { minSdk = 24 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}
dependencies {
    implementation(project(":shared:foundation_compose"))
}
