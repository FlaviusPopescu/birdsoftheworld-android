plugins {
    alias(libs.plugins.convention.androidApplicationCompose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.convention.mapbox)
}

fun getQuotedStringProperty(gradlePropertyName: String) =
    runCatching { property(gradlePropertyName).toString().let { "\"$it\"" } }
        .getOrDefault("\"\"")

android {
    namespace = "dev.flavius.botw"
    buildFeatures.buildConfig = true
    defaultConfig {
        buildConfigField(
            "String", "MAPBOX_ACCESS_TOKEN", getQuotedStringProperty("mapbox_access_token")
        )
        buildConfigField(
            "String", "EBIRD_ACCESS_TOKEN", getQuotedStringProperty("ebird_access_token")
        )
    }
    packaging {
        resources {
            excludes.addAll(
                listOf(
                    "META-INF/INDEX.LIST",
                )
            )
        }
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.di)
    implementation(projects.core.networkBirds)
    implementation(projects.core.networkPlaces)
    implementation(projects.core.storage)
    implementation(projects.feature.nearby)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.kotlinx.serializationJson)
    implementation(libs.ktor.clientAndroid)
    implementation(libs.ktor.clientContentNegotiation)
    implementation(libs.ktor.serializationKotlinxJson)
    implementation(libs.ktor.clientResources)
    implementation(libs.ktor.clientLogging)
}
