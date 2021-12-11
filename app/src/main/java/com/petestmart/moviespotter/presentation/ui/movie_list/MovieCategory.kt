package com.petestmart.moviespotter.presentation.ui.movie_list

import com.petestmart.moviespotter.presentation.ui.movie_list.MovieCategory.*

enum class MovieCategory(val value: String, val id: Int?) {
    POPULAR("Popular", null),
    ACTION("Action", 28),
    ADVENTURE("Adventure", 12),
    ANIMATION("Animation", 16),
    COMEDY("Comedy", 35),
    CRIME("Crime", 80),
    DOCUMENTARY("Documentary", 99),
    DRAMA("Drama", 18),
    FAMILY("Family", 10751),
    FANTASY("Fantasy", 14),
    HISTORY("History", 36),
    HORROR("Horror", 27),
    MUSIC("Music", 10402),
    MYSTERY("Mystery", 9648),
    ROMANCE("Romance", 10749),
    SCIENCEFICTION("Sci Fi", 878),
    TVMOVIE("TV Movie", 10770),
    THRILLER("Thriller", 53),
    WAR("War", 10752),
    WESTERN("Western", 37)

}

fun getAllMovieCategories(): List<MovieCategory>{
    return listOf(
        POPULAR,
        ACTION,
        ADVENTURE,
        ANIMATION,
        COMEDY,
        CRIME,
        DOCUMENTARY,
        DRAMA,
        FAMILY,
        FANTASY,
        HISTORY,
        HORROR,
        MUSIC,
        MYSTERY,
        ROMANCE,
        SCIENCEFICTION,
        TVMOVIE,
        THRILLER,
        WAR,
        WESTERN,

    )
}

fun getMovieCategory(id: Int?): MovieCategory? {
    val map = MovieCategory.values().associateBy(MovieCategory::id)
    return map[id]
}