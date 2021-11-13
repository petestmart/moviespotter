package com.petestmart.moviespotter.presentation.ui.movie_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    val query = mutableStateOf("")

    init {
//        newSearch(query.value)
        newCategorySearch(null)
    }

    fun newSearch(query: String) {
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                query = query,
                page = 1,
            )
            movies.value = result
        }
    }

    fun newCategorySearch(genreId: Int? ) {
        viewModelScope.launch {
            val result = repository.category(
                token = token,
                page = 1,
                genreId = genreId,
            )
            movies.value = result
        }
    }

    // Retains value/state when changed or rotated
    fun onQueryChanged(query: String){
        this.query.value = query
    }
}

