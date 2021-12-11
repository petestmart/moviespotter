package com.petestmart.moviespotter.network

import com.petestmart.moviespotter.network.model.MovieDto
import com.petestmart.moviespotter.network.responses.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("3/search/movie")
    suspend fun search(
        @Query("api_key") token: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("query") query: String,
        @Query("page") page: Int
    ) : MovieSearchResponse

    @GET("3/movie/{movie_id}")
    suspend fun get(
        @Path("movie_id") movieId: Int,
        @Query("api_key") token: String,
        @Query("language") language: String,
    ) : MovieDto

    @GET("3/discover/movie")
    suspend fun category(
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int?,
    ) : MovieSearchResponse
}