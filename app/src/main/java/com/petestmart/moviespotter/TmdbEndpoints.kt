package com.petestmart.moviespotter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbEndpoints {

    @GET("/3/movie/popular")
    fun getPopularMovies(@Query("api_key") key: String): Call<MoviesData>

    @GET("3/search/movie")
    fun getSearchMovies(
        @Query("api_key") key: String,
        @Query("query") query: String?
    ): Call<MoviesData>
}