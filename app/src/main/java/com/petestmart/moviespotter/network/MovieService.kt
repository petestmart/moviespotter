package com.petestmart.moviespotter.network

import com.petestmart.moviespotter.network.model.MovieDto
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

    @GET("3/movie/popular")
    suspend fun popular(
        @Query("api_key") token: String,
    ) : MovieSearchResponse
}