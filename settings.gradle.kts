enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://dl.bintray.com/ekito/koin")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://dl.bintray.com/ekito/koin")
    }
}

rootProject.name = "KMMSample"
include(":shared")
include(":shared:platform_common")
include(":androidApp")
include(":shared:feature_auth")
include(":shared:foundation_kotlin")
include(":shared:feature_profile")
include(":shared:feature_tic_tac_toe")
include(":shared:lib_material3")
include(":shared:foundation_compose")
