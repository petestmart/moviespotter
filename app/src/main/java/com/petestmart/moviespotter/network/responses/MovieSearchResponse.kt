package com.petestmart.moviespotter.network.responses

import com.google.gson.annotations.SerializedName
import com.petestmart.moviespotter.network.model.MovieDto

data class MovieSearchResponse(

    @SerializedName("count")
        var count: Int,

    @SerializedName("results")
        var movies: List<MovieDto>,
)