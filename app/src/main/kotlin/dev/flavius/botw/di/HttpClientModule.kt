package dev.flavius.botw.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.flavius.botw.BuildConfig
import dev.flavius.botw.core.di.httpclient.BirdApi
import dev.flavius.botw.core.di.httpclient.PlacesApi
import dev.flavius.botw.data.network.birds.config.birdsApiConfig
import dev.flavius.botw.data.network.places.config.placesApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    @Singleton
    @Provides
    @BirdApi
    fun provideBirdsApiHttpClient() = HttpClient {
        birdsApiConfig(Logger.ANDROID, BuildConfig.EBIRD_ACCESS_TOKEN)
    }

    @Singleton
    @Provides
    @PlacesApi
    fun providePlacesApiHttpClient() = HttpClient {
        placesApiConfig(Logger.ANDROID, BuildConfig.MAPBOX_ACCESS_TOKEN)
    }
}
