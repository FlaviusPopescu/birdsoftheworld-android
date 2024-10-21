import com.android.build.api.dsl.LibraryExtension
import dev.flavius.build.JAVA_VERSION
import dev.flavius.build.addComposeBom
import dev.flavius.build.addHilt
import dev.flavius.build.addTesting
import dev.flavius.build.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            apply(plugin = "com.google.devtools.ksp")
            apply(plugin = "com.google.dagger.hilt.android")

            kotlinExtension.apply { jvmToolchain(JAVA_VERSION) }

            configureAndroid<LibraryExtension>()
            addHilt()
            addComposeBom()
            addTesting()
        }
    }
}
