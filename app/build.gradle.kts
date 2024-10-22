plugins {
    alias(libs.plugins.convention.composeAndroidApplication)
    alias(libs.plugins.kotlin.serialization)
}

android.namespace = "dev.flavius.botw"

dependencies {
    implementation(projects.featureMain)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serializationJson)
    implementation(libs.androidx.compose.material3)
}
