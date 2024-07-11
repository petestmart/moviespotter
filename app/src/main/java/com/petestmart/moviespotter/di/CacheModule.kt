package com.petestmart.moviespotter.di

import androidx.room.Room
import com.petestmart.moviespotter.cache.MovieDao
import com.petestmart.moviespotter.cache.database.MovieDatabase
import com.petestmart.moviespotter.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): MovieDatabase{
        return Room
            .databaseBuilder(app, MovieDatabase::class.java, MovieDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration() // TODO(Replace with Migration Path)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(app: MovieDatabase): MovieDao {
        return app.movieDao()
    }
}