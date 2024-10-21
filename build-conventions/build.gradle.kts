plugins {
    `kotlin-dsl`
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
        val composeApplication = "application.android.compose"
        val composeLibrary = "library.android.compose"

        fun createPlugin(className: String, pluginName: String) {
            create(pluginName) {
                id = "$group.$pluginName"
                implementationClass = className
            }
        }

        createPlugin("AndroidApplicationComposeConventionPlugin", composeApplication)
        createPlugin("AndroidLibraryComposeConventionPlugin", composeLibrary)
    }
}
