plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.jvm)
}

group = "dev.flavius.build.convention"

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("androidAppCompose") {
            id = "android.app.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        create("androidLibCompose") {
            id = "android.lib.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        create("androidLib") {
            id = "android.lib"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        create("jvmLib") {
            id = "jvm.lib"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        create("ktorClient") {
            id = "ktor.client"
            implementationClass = "KtorClientConventionPlugin"
        }
        create("mapbox") {
            id = "mapbox"
            implementationClass = "MapboxConventionPlugin"
        }
    }
}
