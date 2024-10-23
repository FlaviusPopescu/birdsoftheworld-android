import com.android.build.api.dsl.LibraryExtension
import dev.flavius.build.JAVA_VERSION
import dev.flavius.build.androidSdkLevels
import dev.flavius.build.hilt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "com.google.devtools.ksp")
            apply(plugin = "com.google.dagger.hilt.android")

            kotlinExtension.apply { jvmToolchain(JAVA_VERSION) }

            androidSdkLevels<LibraryExtension>()
            hilt()
        }
    }
}
