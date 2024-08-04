import org.jetbrains.compose.ComposeCompilerKotlinSupportPlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
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
            baseName = "shared"
            isStatic = true
            binaryOptions["bundleId"] = "com.ttt"
            export(projects.shared.featureAuth)
            export(projects.shared.foundationKotlin)
            export(projects.shared.platformCommon)
            export(projects.shared.featureTicTacToe)
        }
    }
    task("testClasses")
    sourceSets {
        commonMain.dependencies {
            api(projects.shared.featureAuth)
            api(projects.shared.foundationKotlin)
            api(projects.shared.platformCommon)
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
    buildFeatures {
        compose =  true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
project.extensions.findByType(KotlinMultiplatformExtension::class.java)?.apply {
    targets
        .filterIsInstance<KotlinNativeTarget>()
        .flatMap { it.binaries }
        .forEach { compilationUnit -> compilationUnit.linkerOpts("-lsqlite3") }
}
plugins.removeAll { it is ComposeCompilerKotlinSupportPlugin }
/*
class ComposeNoNativePlugin : KotlinCompilerPluginSupportPlugin by ComposeCompilerKotlinSupportPlugin() {
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
        return when (kotlinCompilation.target.platformType) {
            KotlinPlatformType.native -> false
            else -> true
        }
    }
}
apply<ComposeNoNativePlugin>()
*/
