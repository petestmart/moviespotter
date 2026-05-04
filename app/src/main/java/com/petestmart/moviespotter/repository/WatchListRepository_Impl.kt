package com.petestmart.moviespotter.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.petestmart.moviespotter.cache.MovieDao
import com.petestmart.moviespotter.cache.model.MovieEntityMapper
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.util.TAG
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
        Log.d(TAG, "toggleSaved: called for movie ${movie.id} - ${movie.title}")
        val newSaved = !movie.isSaved
        val timestamp = if (newSaved) System.currentTimeMillis() else null

        // Ensure movie exists in Room before updating
        val existing = movieDao.getMovieById(movie.id ?: return)
        Log.d(TAG, "toggleSaved: existing in Room = $existing")
        if (existing == null) {
            Log.d(TAG, "toggleSaved: inserting movie into Room")
            movieDao.insertMovie(mapper.mapFromDomainModel(movie))
        }

        movieDao.updateSaved(
            movieId = movie.id,
            saved = newSaved,
            timestamp = timestamp
        )
        Log.d(TAG, "toggleSaved: Room updated, isSaved = $newSaved")

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
            Log.d(TAG, "toggleSaved: Firestore updated")
        }
    }

    override suspend fun toggleWatched(movie: Movie) {
        Log.d(TAG, "toggleWatched: called for movie ${movie.id} - ${movie.title}")
        val newWatched = !movie.isWatched
        val timestamp = if (newWatched) System.currentTimeMillis() else null

        // Ensure movie exists in Room before updating
        val existing = movieDao.getMovieById(movie.id ?: return)
        Log.d(TAG, "toggleWatched: existing in Room = $existing")
        if (existing == null) {
            Log.d(TAG, "toggleWatched: inserting movie into Room")
            movieDao.insertMovie(mapper.mapFromDomainModel(movie))
        }

        movieDao.updateWatched(
            movieId = movie.id,
            watched = newWatched,
            timestamp = timestamp
        )
        Log.d(TAG, "toggleWatched: Room updated, isWatched = $newWatched")

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
            Log.d(TAG, "toggleWatched: Firestore updated")
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