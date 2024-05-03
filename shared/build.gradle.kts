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
            binaryOptions["bundleId"] = "com.ttt"
            export(projects.shared.auth)
            export(projects.shared.foundation)
            export(projects.shared.iosApp)
            export(projects.shared.ticTacToe)
        }
    }
    task("testClasses")
    sourceSets {
        commonMain.dependencies {
            api(projects.shared.auth)
            api(projects.shared.foundation)
            api(projects.shared.iosApp)
            api(libs.koin.core)
            api(libs.koin.core)
            api(compose.components.resources)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.ttt"
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