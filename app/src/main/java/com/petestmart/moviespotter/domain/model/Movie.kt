package com.petestmart.moviespotter.domain.model

import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val genres: String,
    val tagline: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: Date,
    val voteAverage: Double,
    val runtime: Int,
    val budget: Int,
    val status: String,
    val dateAdded: Long,
    val dateUpdated: Long,
    val dateRefreshed: Long,
)