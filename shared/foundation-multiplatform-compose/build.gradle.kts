plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
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
            binaryOptions["bundleId"] = "com.shared.compose_foundation"
        }
    }
    task("testClasses")
    sourceSets {

        commonMain.dependencies {
            compose.apply {
                api(runtime)
                api(foundation)
                api(material3)
                api(ui)
                api(components.resources)
            }
//            api("io.coil-kt.coil3:coil-compose:3.0.0-alpha03")
//            api("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha03")
            libs.apply {
                api(projects.shared.foundation)
                api(libs.file.picker)
//                api("io.coil-kt:coil-compose:2.5.0")
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.shared.compose_foundation"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

