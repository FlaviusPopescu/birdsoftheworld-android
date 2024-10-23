import dev.flavius.build.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MapboxConventionPlugin : Plugin<Project> {
    private val mapboxAndroidLibraryAlias = "mapbox-android"
    private val mapboxComposeLibraryAlias = "mapbox-extension-compose"

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libs.findLibrary(mapboxAndroidLibraryAlias).get())
                add("implementation", libs.findLibrary(mapboxComposeLibraryAlias).get())
            }
        }
    }
}
