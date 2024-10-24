plugins {
    alias(libs.plugins.convention.androidLibraryCompose)
    alias(libs.plugins.convention.mapbox)
}

android.namespace = "dev.flavius.botw.main"

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(libs.androidx.compose.material3)
}
