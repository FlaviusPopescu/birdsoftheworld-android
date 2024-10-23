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
        create("jvmLib") {
            id = "jvm.lib"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        create("mapbox") {
            id = "mapbox"
            implementationClass = "MapboxConventionPlugin"
        }
    }
}
