package com.petestmart.moviespotter.presentation.ui.movie_list

sealed class MovieListEvent{

    object NewSearchEvent: MovieListEvent()

    class NewCategorySearchEvent (genreId: Int?) : MovieListEvent()

    object NextPageEvent: MovieListEvent()

    // restore after process death
    object RestoreStateEvent: MovieListEvent()
}