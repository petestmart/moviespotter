package com.petestmart.moviespotter.presentation.ui.movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListEvent.*
import com.petestmart.moviespotter.repository.MovieRepository
import com.petestmart.moviespotter.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import junit.runner.Version.id
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.reflect.KMutableProperty0

const val PAGE_SIZE = 20

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
    val selectedGenreId = mutableStateOf(null)
    var categoryScrollPosition: Int = 0
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    var movieListScrollPosition = 0

    init {
        newCategorySearch(null)
    }

    fun onTriggerEvent(event: MovieListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: $e, ${e.cause}")
            }
        }
    }

    private suspend fun newSearch() {
        loading.value = true
        resetSearchState()
        clearSelectedCategory()
        delay(1000)
        val result = repository.search(
            token = token,
            includeAdult = false,
            includeVideo = false,
            query = query.value,
            page = 1,
        )
        movies.value = result
        loading.value = false
    }

    fun newCategorySearch(genreId: Int?) {
        viewModelScope.launch {
            Log.d(
                TAG,
                "nextPage: newCategorySearch / selectedCategory: ${selectedCategory.value} / ${selectedCategory}"
            )
            loading.value = true
            resetSearchState()
            delay(1000)
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
            loading.value = false
        }
    }

    /**
     * Append new movies to the current list of movies
     */
    private fun appendMovies(movies: List<Movie>) {
        val current = ArrayList(this.movies.value)
        current.addAll(movies)
        this.movies.value = current
    }

    private suspend fun nextPage() {
        // prevent duplicate events due to recompose happening too quickly
        if ((movieListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {

            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered ${page.value}")

            delay(1000)

            if (page.value > 1) {
                Log.d(TAG, "nextPage: query = ${query.value}")
                if (query.value == null || query.value == "") {
                    Log.d(
                        TAG,
                        "nextPage: categoryIf / selectedCategory.value = ${selectedCategory.value}"
                    )
                    val result = repository.category(
                        token = token,
                        language = "en-US",
                        sortBy = "popularity.desc",
                        includeAdult = false,
                        includeVideo = false,
                        page = page.value,
                        genreId = selectedGenreId.value,
                    )
                    Log.d(TAG, "nextPage category: $result")
                    appendMovies(result)
                } else {
                    val result = repository.search(
                        token = token,
                        page = page.value,
                        query = query.value,
                        includeAdult = false,
                        includeVideo = false,
                    )
                    Log.d(TAG, "nextPage search: $result")
                    appendMovies(result)
                }
            }
            loading.value = false
        }
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeMovieScrollPosition(position: Int) {
        movieListScrollPosition = position
    }

    fun onSelectedCategoryChanged(category: Int?) {
        val newCategory = getMovieCategory(category)
        selectedCategory.value = newCategory
        newCategorySearch(category)
    }

    private fun resetSearchState() {
        movies.value = listOf()
        page.value = 1
        onChangeMovieScrollPosition(0)
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onChangeCategoryPosition(
        position: Int,
    ) {
        categoryScrollPosition = position
    }
}

