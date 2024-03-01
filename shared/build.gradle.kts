import org.jetbrains.compose.ComposeCompilerKotlinSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
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
            export(projects.shared.auth)
            export(projects.shared.foundation)
            export(projects.shared.ticTacToe)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.auth)
            api(projects.shared.foundation)
            api(projects.shared.ticTacToe)
            api(libs.koin.core)
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
plugins.removeAll { it is ComposeCompilerKotlinSupportPlugin }
class ComposeNoNativePlugin : KotlinCompilerPluginSupportPlugin by ComposeCompilerKotlinSupportPlugin() {
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
        return when (kotlinCompilation.target.platformType) {
            KotlinPlatformType.native -> false
            else -> true
        }
    }
}
apply<ComposeNoNativePlugin>()