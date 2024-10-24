import dev.flavius.build.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KtorClientConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                add("implementation", project(":core:di"))
                add("implementation", libs.findLibrary("ktor-clientContentNegotiation").get())
                add("implementation", libs.findLibrary("ktor-clientCore").get())
                add("implementation", libs.findLibrary("ktor-clientLogging").get())
                add("implementation", libs.findLibrary("ktor-clientResources").get())
                add("implementation", libs.findLibrary("ktor-serializationKotlinxJson").get())
                add("testImplementation", libs.findLibrary("ktor-clientMock").get())
                add("testImplementation", libs.findLibrary("ktor-serverTestHost").get())
                add("testImplementation", libs.findLibrary("logbackClassic").get())
            }
        }
    }
}
