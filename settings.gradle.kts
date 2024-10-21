@file:Suppress("UnstableApiUsage")

import org.gradle.api.initialization.dsl.VersionCatalogBuilder.PluginAliasBuilder


rootProject.name = "botw-android"

pluginManagement {
    includeBuild("build-conventions")
    repositories {
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            credentials.username = "mapbox"
            credentials.password = providers.gradleProperty("mapbox_download_token").get()
            authentication.create<BasicAuthentication>("basic")
        }
    }
    versionCatalogs {
        create("libs") {
            from("dev.flavius:version-catalog:+")
            // define simplified accessors for convention plugins; the version is not required.
            fun PluginAliasBuilder.withoutVersion() = version("unspecified")
            plugin("convention-composeAndroidApplication", "dev.flavius.build.convention.application.android.compose")
                .withoutVersion()
            plugin("convention-composeAndroidLibrary", "dev.flavius.build.convention.library.android.compose")
                .withoutVersion()
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":feature-main")