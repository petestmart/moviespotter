package com.petestmart.moviespotter.network

import com.petestmart.moviespotter.network.responses.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("3/search/movie")
    suspend fun search(
        @Query("api_key") token: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ) : MovieSearchResponse

    @GET("3/discover/movie")
    suspend fun category(
        @Query("api_key") token: String,
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int?,
    ) : MovieSearchResponse
}