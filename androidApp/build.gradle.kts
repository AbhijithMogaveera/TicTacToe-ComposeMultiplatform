plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
}

android {
    namespace = "com.example.kmmsample.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.kmmsample.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared.foundation)
    implementation(projects.shared.ticTacToe)
    implementation(projects.shared.auth)
    implementation(projects.shared.profile)
    implementation(project(":shared:foundation-multiplatform-compose"))
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.compose.material3)
}