import dev.flavius.build.JAVA_VERSION
import dev.flavius.build.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.jvm")

            extensions.configure<KotlinJvmProjectExtension> {
                jvmToolchain(JAVA_VERSION)
            }

            dependencies {
                add("implementation", libs.findLibrary("dagger-hiltCore").get())
                add("testImplementation", kotlin("test"))
            }
        }
    }
}
