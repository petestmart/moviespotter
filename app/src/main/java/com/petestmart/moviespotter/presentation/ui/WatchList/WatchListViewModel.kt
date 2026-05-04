package com.petestmart.moviespotter.presentation.ui.watchlist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.repository.WatchlistRepository
import com.petestmart.moviespotter.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
) : ViewModel() {

    val savedMovies: MutableState<List<Movie>> = mutableStateOf(ArrayList())
    val watchedMovies: MutableState<List<Movie>> = mutableStateOf(ArrayList())
    val loading = mutableStateOf(false)

    init {
        onTriggerEvent(WatchlistEvent.LoadSavedMoviesEvent)
        onTriggerEvent(WatchlistEvent.LoadWatchedMoviesEvent)
        onTriggerEvent(WatchlistEvent.SyncFromFirestoreEvent)
    }

    fun onTriggerEvent(event: WatchlistEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is WatchlistEvent.LoadSavedMoviesEvent -> {
                        loadSavedMovies()
                    }
                    is WatchlistEvent.LoadWatchedMoviesEvent -> {
                        loadWatchedMovies()
                    }
                    is WatchlistEvent.ToggleSavedEvent -> {
                        toggleSaved(event.movie)
                    }
                    is WatchlistEvent.ToggleWatchedEvent -> {
                        toggleWatched(event.movie)
                    }
                    is WatchlistEvent.SyncFromFirestoreEvent -> {
                        syncFromFirestore()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: $e, ${e.cause}")
            }
        }
    }

    private fun loadSavedMovies() {
        watchlistRepository.getSavedMovies().onEach { movies ->
            savedMovies.value = movies
        }.launchIn(viewModelScope)
    }

    private fun loadWatchedMovies() {
        watchlistRepository.getWatchedMovies().onEach { movies ->
            watchedMovies.value = movies
        }.launchIn(viewModelScope)
    }

    private suspend fun toggleSaved(movie: Movie) {
        loading.value = true
        watchlistRepository.toggleSaved(movie)
        loading.value = false
    }

    private suspend fun toggleWatched(movie: Movie) {
        loading.value = true
        watchlistRepository.toggleWatched(movie)
        loading.value = false
    }

    private suspend fun syncFromFirestore() {
        loading.value = true
        watchlistRepository.syncFromFirestore()
        loading.value = false
    }
}