plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
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
            baseName = "shared"
            isStatic = true
            transitiveExport = true
            export(project(":shared:auth"))
            export(project(":shared:foundation"))
            export(project(":shared:fourm"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":shared:auth"))
            api(project(":shared:foundation"))
            api(project(":shared:fourm"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
