plugins {
    alias(libs.plugins.convention.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.ktor.clientContentNegotiation)
    implementation(libs.ktor.clientCore)
    implementation(libs.ktor.clientLogging)
    implementation(libs.ktor.clientResources)
    implementation(libs.ktor.serializationKotlinxJson)
    testImplementation(libs.ktor.clientMock)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.logbackClassic)
}
