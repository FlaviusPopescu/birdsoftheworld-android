package dev.flavius.botw.data.network.places.config

import dev.flavius.botw.data.network.places.PlacesApi.Endpoints.API_HOST
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.serialization
import kotlinx.serialization.json.Json

fun HttpClientConfig<*>.placesApiConfig(
    logger: Logger,
    accessToken: String,
) {
    install(ContentNegotiation) {
        serialization(ContentType.Application.Json, Json { ignoreUnknownKeys = true })
    }
    install(Resources)
    install(Logging) {
        this.logger = logger
        level = LogLevel.HEADERS
    }
    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = API_HOST
            parameters.append("access_token", accessToken)
        }
    }
}
