package dev.flavius.build

import com.android.build.api.dsl.CommonExtension
import dev.flavius.build.AndroidLibraryAliases.Companion.Defaults
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
 * Used to configure the aliases of libraries defined in the version catalog
 */
interface AndroidLibraryAliases {
    val composeBom: String
    val composeUiTooling: String
    val composeUiToolingPreview: String
    val hiltAndroid: String
    val hiltAndroidTesting: String
    val hiltCompiler: String
    val hiltNavigationCompose: String
    val mockitoAndroid: String
    val uiTestJunit4: String
    val uiTestJunitManifest: String

    companion object {
        /**
         * The aliases defined by the default version catalog (i.e. named `libs`).
         */
        val Defaults = object : AndroidLibraryAliases {
            override val composeBom = "androidx-compose-bom"
            override val composeUiTooling = "androidx-compose-uiTooling"
            override val composeUiToolingPreview = "androidx-compose-uiToolingPreview"
            override val hiltAndroid = "dagger-hiltAndroid"
            override val hiltAndroidTesting = "dagger-hiltAndroidTesting"
            override val hiltCompiler = "dagger-hiltCompiler"
            override val hiltNavigationCompose = "androidx-hilt-navigationCompose"
            override val mockitoAndroid = "mockito-android"
            override val uiTestJunit4 = "androidx-compose-uiTestJunit4"
            override val uiTestJunitManifest = "androidx-compose-uiTestManifest"
        }
    }
}

internal fun Project.hilt(aliases: AndroidLibraryAliases = Defaults) {
    dependencies {
        add("ksp", libs.findLibrary(aliases.hiltCompiler).get())
        add("implementation", libs.findLibrary(aliases.hiltAndroid).get())
        add("implementation", libs.findLibrary(aliases.hiltNavigationCompose).get())
    }
}

internal fun Project.hiltTesting(aliases: AndroidLibraryAliases = Defaults) {
    dependencies {
        add("testImplementation", libs.findLibrary(aliases.hiltAndroidTesting).get())
        add("kspTest", libs.findLibrary(aliases.hiltCompiler).get())
        add("androidTestImplementation", libs.findLibrary(aliases.hiltAndroidTesting).get())
        add("kspAndroidTest", libs.findLibrary(aliases.hiltCompiler).get())
    }
}

internal fun Project.composeBom(aliases: AndroidLibraryAliases = Defaults) {
    dependencies {
        val bom = libs.findLibrary(aliases.composeBom).get()
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))
        add("implementation", libs.findLibrary(aliases.composeUiToolingPreview).get())
        add("debugImplementation", libs.findLibrary(aliases.composeUiTooling).get())
    }
}

internal fun Project.composeTesting(aliases: AndroidLibraryAliases = Defaults) {
    dependencies {
        add("testImplementation", kotlin("test"))
        add("androidTestImplementation", libs.findLibrary(aliases.uiTestJunit4).get())
        add("debugImplementation", libs.findLibrary(aliases.uiTestJunitManifest).get())
    }
}
