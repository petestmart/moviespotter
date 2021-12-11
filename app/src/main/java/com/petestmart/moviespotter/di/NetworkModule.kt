package com.petestmart.moviespotter.di

import com.google.gson.GsonBuilder
import com.petestmart.moviespotter.BuildConfig
import com.petestmart.moviespotter.network.MovieService
import com.petestmart.moviespotter.network.model.MovieDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
//import process.env.TMDB_API_KEY

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMovieMapper(): MovieDtoMapper {
        return MovieDtoMapper()
    }

    @Singleton
    @Provides
    fun provideMovieService(): MovieService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(MovieService::class.java)
    }

    @Singleton
    @Provides
    @Named("api_key")
    fun provideAuthToken(): String {
        return BuildConfig.TMDB_API_KEY
    }
}
