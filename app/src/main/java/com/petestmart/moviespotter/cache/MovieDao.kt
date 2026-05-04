package com.petestmart.moviespotter.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petestmart.moviespotter.cache.model.MovieEntity
import com.petestmart.moviespotter.util.MOVIE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    suspend fun insertMovie(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>): LongArray

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("DELETE FROM movies WHERE id IN (:ids)")
    suspend fun deleteMovies(ids: List<Int>): Int

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM movies WHERE id = :primaryKey")
    suspend fun deleteMovie(primaryKey: Int): Int

    /**
     * Retrieve movies for a particular page.
     * Ex: page = 2 retrieves movies from 20-40.
     * Ex: page = 3 retrieves movies from 40-60
     */
    @Query("""
        SELECT * FROM movies 
        WHERE title LIKE '%' || :query || '%'
        OR genres LIKE '%' || :query || '%'  
        ORDER BY date_updated DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """)
    suspend fun searchMovies(
        query: String,
        page: Int,
        pageSize: Int = MOVIE_PAGINATION_PAGE_SIZE
    ): List<MovieEntity>

    /**
     * Same as 'searchMovies' function, but no query.
     */
    @Query("""
        SELECT * FROM movies 
        ORDER BY date_updated DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """)
    suspend fun getAllMovies(
        page: Int,
        pageSize: Int = MOVIE_PAGINATION_PAGE_SIZE
    ): List<MovieEntity>

    /**
     * Restore Movies after process death
     */
    @Query("""
        SELECT * FROM movies 
        WHERE title LIKE '%' || :query || '%'
        OR genres LIKE '%' || :query || '%' 
        ORDER BY date_updated DESC LIMIT (:page * :pageSize)
        """)
    suspend fun restoreMovies(
        query: String,
        page: Int,
        pageSize: Int = MOVIE_PAGINATION_PAGE_SIZE
    ): List<MovieEntity>

    /**
     * Same as 'restoreMovies' function, but no query.
     */
    @Query("""
        SELECT * FROM movies 
        ORDER BY date_updated DESC LIMIT (:page * :pageSize)
    """)
    suspend fun restoreAllMovies(
        page: Int,
        pageSize: Int = MOVIE_PAGINATION_PAGE_SIZE
    ): List<MovieEntity>

    /**
     * Watchlist queries
     */

    @Query("SELECT * FROM movies WHERE is_saved = 1 ORDER BY saved_at DESC")
    fun getSavedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE is_watched = 1 ORDER BY watched_at DESC")
    fun getWatchedMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET is_saved = :saved, saved_at = :timestamp WHERE id = :movieId")
    suspend fun updateSaved(movieId: Int, saved: Boolean, timestamp: Long?)

    @Query("UPDATE movies SET is_watched = :watched, watched_at = :timestamp WHERE id = :movieId")
    suspend fun updateWatched(movieId: Int, watched: Boolean, timestamp: Long?)
}