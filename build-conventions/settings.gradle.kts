@file:Suppress("UnstableApiUsage")

rootProject.name = "build-conventions"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        mavenLocal()
    }
    versionCatalogs {
        create("libs") {
            from("dev.flavius:version-catalog:+")
        }
    }
}
