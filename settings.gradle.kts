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
include(":androidApp")
include(":shared:auth")
include(":shared:foundation")
include(":shared:profile")
include(":shared:tic_tac_toe")
include(":shared:material3")
include(":shared:foundation-multiplatform-compose")
