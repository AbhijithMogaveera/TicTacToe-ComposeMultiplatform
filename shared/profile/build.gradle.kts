plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.cashsqldelight)
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
            binaryOptions["bundleId"] = "com.shared.profile"
        }
    }
    task("testClasses")
    sourceSets {
        androidMain.dependencies {
            implementation(libs.cashsqldeligh.android)
        }
        commonMain.dependencies {
            implementation(projects.shared.foundationMultiplatformCompose)
            implementation(projects.shared.auth)
            implementation(libs.file.picker)
            implementation(libs.cashsqldeligh.coroutine)
//            implementation("io.coil-kt.coil3:coil:3.0.0-alpha01")
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
}
dependencies {
    implementation(project(":shared:auth"))
}
sqldelight {
    databases {
        create("Database") {
            packageName.set("com.shared.profile.sql")
        }
    }
}

