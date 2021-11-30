package com.petestmart.moviespotter.presentation.ui.movie_list

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListEvent.*
import com.petestmart.moviespotter.repository.MovieRepository
import com.petestmart.moviespotter.util.TAG
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 20

const val STATE_KEY_PAGE = "movie.state.page.key"
const val STATE_KEY_QUERY = "movie.state.query.key"
const val STATE_KEY_LIST_POSITION = "movie.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "movie.state.query.select_category"

@HiltViewModel
class MovieListViewModel
@Inject
//@AssistedInject
constructor(
    private val repository: MovieRepository,
    @Named("api_key") private val token: String,
//    @Assisted private val savedStateHandle: SavedStateHandle,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<MovieCategory?> = mutableStateOf(null)
    var selectedGenreId: Int? = null
    var categoryScrollPosition: Int = 0
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    var movieListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }
        savedStateHandle.get<MovieCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }

        if(movieListScrollPosition != 0){
            onTriggerEvent(RestoreStateEvent)
        } else {
            onTriggerEvent(NewCategorySearchEvent(null))
        }
    }

    fun onTriggerEvent(event: MovieListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage(selectedGenreId)
                    }
                    is NewCategorySearchEvent -> {
                        newCategorySearch(selectedGenreId)
                    }
                    is RestoreStateEvent -> {
                        restoreState()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: $e, ${e.cause}")
            }
        }
    }

    private suspend fun restoreState() {
        // Querying on restore after process death
        // Future: Query from cache
        loading.value = true
        val results: MutableList<Movie> = mutableListOf()
        for (p in 1..page.value){
            if (query.value == null || query.value == "") {
                val result = repository.category(
                    token = token,
                    language = "en-US",
                    sortBy = "popularity.desc",
                    includeAdult = false,
                    includeVideo = false,
                    page = p,
                    genreId = selectedGenreId,
                )
                results.addAll(result)
                if(p == page.value) {
                    movies.value = results
                    loading.value = false
                }
            } else {
                val result = repository.search(
                    token = token,
                    includeAdult = false,
                    includeVideo = false,
                    query = query.value,
                    page = p,
                )
                results.addAll(result)
                if(p == page.value) {
                    movies.value = results
                    loading.value = false
                }
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

    private suspend fun nextPage(genreId: Int?) {
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
                        "nextPage: categoryIf / " +
                                "selectedCategory: ${selectedCategory.value} / " +
                                "selectedGenreId: ${selectedGenreId} / " +
                                "genreId: $genreId"
                    )
                    val result = repository.category(
                        token = token,
                        language = "en-US",
                        sortBy = "popularity.desc",
                        includeAdult = false,
                        includeVideo = false,
                        page = page.value,
                        genreId = selectedGenreId,
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
        setPage(page.value + 1)
//        page.value = page.value + 1
    }

    fun onChangeMovieScrollPosition(position: Int) {
//        movieListScrollPosition = position
        setListScrollPosition(position = position)
    }

    fun onSelectedCategoryChanged(category: Int?) {
        selectedGenreId = category
        val newCategory = getMovieCategory(category)
        setSelectedCategory(newCategory)
//        selectedCategory.value = newCategory
        newCategorySearch(category)
    }

    private fun resetSearchState() {
        movies.value = listOf()
        page.value = 1
        onChangeMovieScrollPosition(0)
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
//        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
//        if(query.contains("\n") ){
//            query.replace("\n", "")
////            newSearch()
//            println("DEBUG-ONQUERYCHANGED")
//        }
//        this.query.value = query
    }

    fun onChangeCategoryPosition(
        position: Int,
    ) {
        categoryScrollPosition = position
    }

    private fun setListScrollPosition(position: Int){
        movieListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: MovieCategory?){
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String){
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}

