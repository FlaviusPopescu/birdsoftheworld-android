plugins {
    alias(libs.plugins.convention.androidLibrary)
    alias(libs.plugins.androidx.room)
}

android {
    namespace = "dev.flavius.botw.core.storage"
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
}
