plugins {
    alias(libs.plugins.convention.androidLibrary)
}

android.namespace = "dev.flavius.botw.core.data"

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.networkBirds)
    implementation(projects.core.networkPlaces)
    implementation(projects.core.storage)
}
