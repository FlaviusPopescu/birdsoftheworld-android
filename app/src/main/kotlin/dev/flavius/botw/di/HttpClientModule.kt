package dev.flavius.botw.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.flavius.botw.data.api.config.birdsApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    @Singleton
    @Provides
    fun provideHttpClient() = HttpClient { birdsApiConfig(Logger.ANDROID) }
}
