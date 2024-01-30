plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    kotlin("plugin.serialization") version "1.9.21" apply false
}

buildscript {
    dependencies{
        classpath (libs.buildkonfig.gradle.plugin)
    }
}
