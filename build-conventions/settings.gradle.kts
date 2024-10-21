@file:Suppress("UnstableApiUsage")

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
