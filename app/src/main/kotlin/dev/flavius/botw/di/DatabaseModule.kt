package dev.flavius.botw.di

import android.content.Context
import androidx.room.Room
import com.mapbox.common.asReadStream
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.flavius.botw.core.storage.SpeciesDatabase
import io.ktor.util.GZip
import java.io.InputStream
import java.util.zip.GZIPInputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideSpeciesDatabase(@ApplicationContext context: Context): SpeciesDatabase {
        return Room
            .databaseBuilder(context, SpeciesDatabase::class.java, "species.db")
            .createFromAsset("species_with_url.db")
            .build()
    }
}
