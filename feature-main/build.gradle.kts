plugins {
    alias(libs.plugins.convention.composeAndroidLibrary)
}

android.namespace = "dev.flavius.kotlin.playground.feature.main"

dependencies {
    implementation(libs.androidx.compose.material3)
}
