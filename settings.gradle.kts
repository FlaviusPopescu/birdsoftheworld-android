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
        mavenLocal()
    }
    versionCatalogs {
        create("libs") {
            from("dev.flavius:version-catalog:+")

            // add convention plugins to this catalog (these local plugins do not specify a version)
            fun PluginAliasBuilder.withoutVersion() = version("unspecified")
            plugin("convention-androidApplicationCompose", "android.app.compose").withoutVersion()
            plugin("convention-androidLibraryCompose", "android.lib.compose").withoutVersion()
            plugin("convention-androidLibrary", "android.lib").withoutVersion()
            plugin("convention-jvm", "jvm.lib").withoutVersion()
            plugin("convention-ktorClient", "ktor.client").withoutVersion()
            plugin("convention-mapbox", "mapbox").withoutVersion()
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":core:data")
include(":core:di")
include(":core:model")
include(":core:network-birds")
include(":core:network-places")
include(":core:storage")
include(":feature:nearby")