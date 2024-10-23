plugins {
    alias(libs.plugins.convention.androidLibrary)
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.network)
}
