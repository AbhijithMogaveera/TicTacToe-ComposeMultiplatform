import org.jetbrains.compose.ComposeCompilerKotlinSupportPlugin
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

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

//plugins.removeAll { it is ComposeCompilerKotlinSupportPlugin }
//class ComposeNoNativePlugin : KotlinCompilerPluginSupportPlugin by ComposeCompilerKotlinSupportPlugin() {
//    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
//        return when (kotlinCompilation.target.platformType) {
//            KotlinPlatformType.native -> false
//            else -> true
//        }
//    }
//}
//apply<ComposeNoNativePlugin>()
