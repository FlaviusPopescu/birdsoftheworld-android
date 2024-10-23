package dev.flavius.botw.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.AndroidClientEngine

@Module
@InstallIn(ActivityComponent::class)
abstract class HttpClientModule {
    @Binds
    abstract fun bindHttpClientEngine(androidClientEngine: AndroidClientEngine): HttpClientEngine
}
