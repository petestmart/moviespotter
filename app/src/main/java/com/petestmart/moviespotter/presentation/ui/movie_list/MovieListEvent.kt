package com.petestmart.moviespotter.presentation.ui.movie_list

sealed class MovieListEvent{

    object NewSearchEvent: MovieListEvent()

    class NewCategorySearchEvent (genreId: Int?) : MovieListEvent()

    class NextPageEvent (genreId: Int?): MovieListEvent()

    // restore after process death
    object RestoreStateEvent: MovieListEvent()
}