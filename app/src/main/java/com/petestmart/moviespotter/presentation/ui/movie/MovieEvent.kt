package com.petestmart.moviespotter.presentation.ui.movie

import com.petestmart.moviespotter.domain.model.Movie

sealed class MovieEvent{

    data class GetMovieEvent(
        val id: Int,
    ): MovieEvent()
}