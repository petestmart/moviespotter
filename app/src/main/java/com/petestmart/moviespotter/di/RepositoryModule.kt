package com.petestmart.moviespotter.di

import com.petestmart.moviespotter.network.MovieService
import com.petestmart.moviespotter.network.model.MovieDtoMapper
import com.petestmart.moviespotter.repository.MovieRepository
import com.petestmart.moviespotter.repository.MovieRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieService: MovieService,
        movieDtoMapper: MovieDtoMapper
    ): MovieRepository {
        println("BIGCHECK RepositoryModule: provideMovieRepository")
        return MovieRepository_Impl(movieService, movieDtoMapper)
    }
}