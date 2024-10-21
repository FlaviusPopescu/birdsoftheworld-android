plugins {
    alias(libs.plugins.convention.composeAndroidApplication)
}

dependencies {
    implementation(projects.featureMain)
    implementation(libs.androidx.activity.compose)
}

android.namespace = "dev.flavius.playground.kotlin"
