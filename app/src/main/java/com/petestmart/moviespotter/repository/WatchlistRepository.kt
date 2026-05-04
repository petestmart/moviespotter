package com.petestmart.moviespotter.repository

import com.petestmart.moviespotter.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {

    fun getSavedMovies(): Flow<List<Movie>>

    fun getWatchedMovies(): Flow<List<Movie>>

    suspend fun toggleSaved(movie: Movie)

    suspend fun toggleWatched(movie: Movie)

    suspend fun syncFromFirestore()
}