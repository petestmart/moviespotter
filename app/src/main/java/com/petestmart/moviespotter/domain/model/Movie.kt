package com.petestmart.moviespotter.domain.model

data class Movie (
    val id: Int? = null,
    val title: String? = null,
    val tagline: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val runtime: Int? = null,
    val budget: Int? = null,
    val status: String? = null,
)