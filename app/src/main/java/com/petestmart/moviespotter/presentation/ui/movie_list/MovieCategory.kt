package com.petestmart.moviespotter.presentation.ui.movie_list

import com.petestmart.moviespotter.presentation.ui.movie_list.MovieCategory.*

enum class MovieCategory(val value: String, val id: Int?) {
    POPULAR("Popular", null),
    ADVENTURE("Adventure", 12),
    ANIMATION("Animation", 16),
    COMEDY("Comedy", 35),
    FANTASY("Fantasy", 14),
    DOCUMENTARY("Documentary", 99),
    FAMILY("Family", 10751),
    HORROR("Horror", 27),
    ACTION("Action", 28)
}

fun getAllMovieCategories(): List<MovieCategory>{
    return listOf(
        POPULAR,
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

fun getMovieCategory(value: String, id: Int?): MovieCategory?{
    val map = MovieCategory.values().associateBy(MovieCategory::value)
    return map[value]
}