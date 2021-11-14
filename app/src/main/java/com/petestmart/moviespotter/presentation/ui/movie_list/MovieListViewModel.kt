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
import kotlin.reflect.KMutableProperty0

@HiltViewModel
class MovieListViewModel
@Inject
constructor(
    private val repository: MovieRepository,
    @Named("api_key") private val token: String,
) : ViewModel() {

    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

    val selectedCategory: MutableState<MovieCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Int = 0

    var categoryScrollOffsetPosition: Int = 0

    init {
        newCategorySearch(null)
    }

    fun newSearch() {
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                includeAdult = false,
                includeVideo = false,
                query = query.value,
                page = 1,
            )
            movies.value = result
        }
    }

    fun newCategorySearch(genreId: Int?) {
        viewModelScope.launch {
            val result = repository.category(
                token = token,
                language = "en-US",
                sortBy = "popularity.desc",
                includeAdult = false,
                includeVideo = false,
                page = 1,
                genreId = genreId,
            )
            movies.value = result
        }
    }
    fun onSelectedCategoryChanged(category: Int?) {
        val newCategory = getMovieCategory(category)
        selectedCategory.value = newCategory
        newCategorySearch(category)
    }

    // Retains value/state when changed or rotated
    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onChangeCategoryPosition(position: Int, offset: Int) {
        categoryScrollPosition = position
        categoryScrollOffsetPosition = offset
    }
}

