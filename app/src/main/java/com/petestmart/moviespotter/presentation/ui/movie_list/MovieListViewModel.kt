package com.petestmart.moviespotter.presentation.ui.movie_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieListViewModel
@Inject
constructor(
    private val repository: MovieRepository,
    @Named("api_key") private val token: String,
) : ViewModel() {

    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                query = "Boss",
                page = 1,
            )
            movies.value = result
        }
    }
}

