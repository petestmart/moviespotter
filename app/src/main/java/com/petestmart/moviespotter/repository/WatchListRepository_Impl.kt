package com.petestmart.moviespotter.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.petestmart.moviespotter.cache.MovieDao
import com.petestmart.moviespotter.cache.model.MovieEntityMapper
import com.petestmart.moviespotter.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WatchlistRepository_Impl @Inject constructor(
    private val movieDao: MovieDao,
    private val mapper: MovieEntityMapper,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : WatchlistRepository {

    private val userId get() = auth.currentUser?.uid

    override fun getSavedMovies(): Flow<List<Movie>> {
        return movieDao.getSavedMovies().map { entities ->
            mapper.toDomainList(entities)
        }
    }

    override fun getWatchedMovies(): Flow<List<Movie>> {
        return movieDao.getWatchedMovies().map { entities ->
            mapper.toDomainList(entities)
        }
    }

    override suspend fun toggleSaved(movie: Movie) {
        val newSaved = !movie.isSaved
        val timestamp = if (newSaved) System.currentTimeMillis() else null

        // Write to Room immediately (offline-first)
        movieDao.updateSaved(
            movieId = movie.id ?: return,
            saved = newSaved,
            timestamp = timestamp
        )

        // Sync to Firestore
        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("watchlist").document(movie.id.toString())
                .set(
                    mapOf(
                        "isSaved" to newSaved,
                        "savedAt" to timestamp,
                        "title" to movie.title,
                        "posterPath" to movie.posterPath
                    ),
                    SetOptions.merge()
                )
        }
    }

    override suspend fun toggleWatched(movie: Movie) {
        val newWatched = !movie.isWatched
        val timestamp = if (newWatched) System.currentTimeMillis() else null

        // Write to Room immediately (offline-first)
        movieDao.updateWatched(
            movieId = movie.id ?: return,
            watched = newWatched,
            timestamp = timestamp
        )

        // Sync to Firestore
        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("watchlist").document(movie.id.toString())
                .set(
                    mapOf(
                        "isWatched" to newWatched,
                        "watchedAt" to timestamp
                    ),
                    SetOptions.merge()
                )
        }
    }

    override suspend fun syncFromFirestore() {
        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("watchlist").get().await()
                .documents.forEach { doc ->
                    val movieId = doc.id.toIntOrNull() ?: return@forEach
                    movieDao.updateSaved(
                        movieId = movieId,
                        saved = doc.getBoolean("isSaved") ?: false,
                        timestamp = doc.getLong("savedAt")
                    )
                    movieDao.updateWatched(
                        movieId = movieId,
                        watched = doc.getBoolean("isWatched") ?: false,
                        timestamp = doc.getLong("watchedAt")
                    )
                }
        }
    }
}