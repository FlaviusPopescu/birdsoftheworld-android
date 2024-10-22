package dev.flavius.build

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

internal inline fun <reified T : CommonExtension<*, *, *, *, *, *>> Project.androidSdkLevels() {
    extensions.configure(T::class) {
        apply {
            compileSdk = AndroidSdkConstants.COMPILE_SDK
            defaultConfig {
                minSdk = AndroidSdkConstants.MINIMUM_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }
    }
}

/**
 * Adds Hilt dependencies for Dependency Injection (DI). [compilerAlias] and [hiltAndroidAlias] need
 * to match the aliases defined in the default version catalog (i.e. named "libs"). If they are not
 * defined, then the build should fail.
 */
internal fun Project.hilt(
    compilerAlias: String = "dagger-hiltCompiler",
    hiltAndroidAlias: String = "dagger-hiltAndroid",
    hiltNavigationCompose: String = "androidx-hilt-navigationCompose",
) {
    dependencies {
        add("ksp", libs.findLibrary(compilerAlias).get())
        add("implementation", libs.findLibrary(hiltAndroidAlias).get())
        add("implementation", libs.findLibrary(hiltNavigationCompose).get())
    }
}

internal fun Project.composeBom(
    composeBomAlias: String = "androidx-compose-bom",
    composeUiToolingAlias: String = "androidx-compose-uiTooling",
    composeUiToolingPreviewAlias: String = "androidx-compose-uiToolingPreview",
) {
    dependencies {
        val bom = libs.findLibrary(composeBomAlias).get()
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))
        add("implementation", libs.findLibrary(composeUiToolingPreviewAlias).get())
        add("debugImplementation", libs.findLibrary(composeUiToolingAlias).get())
    }
}

internal fun Project.testing(
    uiTestJunit4Alias: String = "androidx-compose-uiTestJunit4",
    uiTestJunitManifest: String = "androidx-compose-uiTestManifest",
) {
    dependencies {
        add("testImplementation", kotlin("test"))
        add("androidTestImplementation", libs.findLibrary(uiTestJunit4Alias).get())
        add("debugImplementation", libs.findLibrary(uiTestJunitManifest).get())
    }
}
