package com.petestmart.moviespotter.presentation.ui.watchlist

import com.petestmart.moviespotter.domain.model.Movie

sealed class WatchlistEvent {

    object LoadSavedMoviesEvent : WatchlistEvent()

    object LoadWatchedMoviesEvent : WatchlistEvent()

    object SyncFromFirestoreEvent : WatchlistEvent()

    data class ToggleSavedEvent(val movie: Movie) : WatchlistEvent()

    data class ToggleWatchedEvent(val movie: Movie) : WatchlistEvent()
}