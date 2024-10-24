plugins {
    alias(libs.plugins.convention.androidLibrary)
}

android.namespace = "dev.flavius.botw.core.data"

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.storage)
}
