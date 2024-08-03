plugins {
    alias(libs.plugins.androidApplication) apply (false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)

    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false

    alias(libs.plugins.cashsqldelight) apply false

    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler) apply false

    kotlin("plugin.serialization") version "1.9.22" apply false
    kotlin("native.cocoapods") version "1.9.22" apply false
}
