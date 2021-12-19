package com.petestmart.moviespotter.network.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieDto(

    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("tagline")
    var tagline: String,

    @SerializedName("overview")
    var overview: String,

    @SerializedName("poster_path")
    var posterPath: String,

    @SerializedName("release_date")
    var releaseDate: String,

    @SerializedName("vote_average")
    var voteAverage: Double,

    @SerializedName("runtime")
    var runtime: Int,

    @SerializedName("budget")
    var budget: Int,

    @SerializedName("status")
    var status: String,
)