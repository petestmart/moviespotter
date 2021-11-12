package com.petestmart.moviespotter.presentation.ui.movie_list

import com.petestmart.moviespotter.presentation.ui.movie_list.MovieCategory.*

enum class MovieCategory(val value: String) {
    POPULAR("Popular"),
    NEWRELEASE("New Release"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    COMEDY("Comedy"),
    FANTASY("Fantasy"),
    DOCUMENTARY("Documentary"),
    FAMILY("Family"),
    HORROR("Horror"),
    ACTION("Action")
}

fun getAllMovieCategories(): List<MovieCategory>{
    return listOf(
        POPULAR,
        NEWRELEASE,
        ADVENTURE,
        ANIMATION,
        COMEDY,
        FANTASY,
        DOCUMENTARY,
        FAMILY,
        HORROR,
        ACTION
    )
}

fun getMovieCategory(value: String): MovieCategory?{
    val map = MovieCategory.values().associateBy(MovieCategory::value)
    return map[value]
}