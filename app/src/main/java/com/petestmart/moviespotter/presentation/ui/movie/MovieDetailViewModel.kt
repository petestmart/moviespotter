package com.petestmart.moviespotter.presentation.ui.movie

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.repository.MovieRepository
import com.petestmart.moviespotter.presentation.ui.movie.MovieEvent.GetMovieEvent
import com.petestmart.moviespotter.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_MOVIE = "movie.state.movie.key"

@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(
    private val movieRepository: MovieRepository,
    @Named("api_key") private val token: String,
    private val state: SavedStateHandle,
): ViewModel() {

    val movie: MutableState<Movie?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    init {
        // restore if process dies
        state.get<Int>(STATE_KEY_MOVIE)?.let { movieId ->
            onTriggerEvent(GetMovieEvent(movieId))
        }
    }

    fun onTriggerEvent(event: MovieEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is GetMovieEvent -> {
                        if(movie.value == null){
                            getMovie(event.id)
                        }
                    }
                }
            } catch (e: Exception){
                Log.e(TAG, "onTriggerEvent: Exception $e, ${e.cause}")
            }
        }
    }

    private suspend fun getMovie(id: Int){
        loading.value = true

        // simulate delay to show loading
        delay(1000)

        val movie = movieRepository.get(
            id=id,
            token = token,
            language = "en-US"
        )
        this.movie.value = movie

        state.set(STATE_KEY_MOVIE, movie.id)

        loading.value = false
    }
}