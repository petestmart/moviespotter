package com.petestmart.moviespotter.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.petestmart.moviespotter.cache.MovieDao
import com.petestmart.moviespotter.cache.model.MovieEntityMapper
import com.petestmart.moviespotter.presentation.BaseApplication
import com.petestmart.moviespotter.repository.WatchlistRepository
import com.petestmart.moviespotter.repository.WatchlistRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideMovieEntityMapper(): MovieEntityMapper {
        return MovieEntityMapper()
    }

    @Singleton
    @Provides
    fun provideWatchlistRepository(
        movieDao: MovieDao,
        mapper: MovieEntityMapper,
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): WatchlistRepository {
        return WatchlistRepository_Impl(
            movieDao = movieDao,
            mapper = mapper,
            firestore = firestore,
            auth = auth
        )
    }
}