plugins {
    alias(libs.plugins.convention.androidApplicationCompose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.convention.mapbox)
}

android.namespace = "dev.flavius.botw"

android {
    buildFeatures.buildConfig = true
    defaultConfig.buildConfigField(
        "String",
        "MAPBOX_ACCESS_TOKEN",
        runCatching {
            property("mapbox_access_token").toString().let { "\"$it\"" }
        }.getOrDefault("\"\"")
    )
}

dependencies {
    implementation(projects.featureMain)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serializationJson)
    implementation(libs.ktor.clientAndroid)
}
