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
include(":androidApp")
include(":shared:auth")
include(":shared:foundation")
include(":shared:fourm")
include(":androidApp:auth")
include(":androidApp:forum")
