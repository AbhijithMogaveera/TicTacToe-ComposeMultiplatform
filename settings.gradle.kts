enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KMMSample"
include(":androidApp")
include(":shared:auth")
include(":shared:foundation")
include(":shared:fourm")
include(":androidApp:auth")
include(":androidApp:forum")
