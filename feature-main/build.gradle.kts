plugins {
    alias(libs.plugins.convention.composeAndroidLibrary)
}

android.namespace = "dev.flavius.botw.main"

dependencies {
    implementation(libs.androidx.compose.material3)
}
