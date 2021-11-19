package com.petestmart.moviespotter.domain.model

data class Movie (
    val id: Int? = null,
    val title: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
)