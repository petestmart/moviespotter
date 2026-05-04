package com.petestmart.moviespotter.presentation.ui.movie_list

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.petestmart.moviespotter.presentation.components.LeftDrawer
import com.petestmart.moviespotter.presentation.components.MovieList
import com.petestmart.moviespotter.presentation.components.SearchAppBar
import com.petestmart.moviespotter.presentation.components.util.SnackbarController
import com.petestmart.moviespotter.presentation.theme.AppTheme
import com.petestmart.moviespotter.presentation.ui.watchlist.WatchlistEvent
import com.petestmart.moviespotter.presentation.ui.watchlist.WatchlistViewModel
import com.petestmart.moviespotter.util.TAG

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@Composable
fun MovieListScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onNavigateToMovieDetailScreen: (String) -> Unit,
    viewModel: MovieListViewModel,
    snackbarController: SnackbarController,
    watchlistViewModel: WatchlistViewModel = hiltViewModel(),
    onNavigateToWatchlist: () -> Unit,
) {
    Log.d(TAG, "MovieListScreen: ${viewModel}")
    val movies = viewModel.movies.value
    val query = viewModel.query.value
    val selectedCategory = viewModel.selectedCategory.value
    val selectedGenreId = viewModel.selectedGenreId
    val loading = viewModel.loading.value
    val page = viewModel.page.value
    val scaffoldState = rememberScaffoldState()

    AppTheme(
        darkTheme = isDarkTheme,
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChanged = viewModel::onQueryChanged,
                    onExecuteSearch = {
                        viewModel.onTriggerEvent(MovieListEvent.NewSearchEvent)
                    },
                    categoryScrollPosition = viewModel.categoryScrollPosition,
                    selectedCategory = selectedCategory,
                    selectedGenreId = selectedGenreId,
                    onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                    onChangeCategoryPosition = viewModel::onChangeCategoryPosition,
                    newCategorySearch = viewModel::newCategorySearch,
                    onToggleTheme = {
                        onToggleTheme()
                    },
                    snackbarController = snackbarController,
                    scaffoldState = scaffoldState,
                )
            },
            drawerContent = {
                LeftDrawer(
                    onToggleTheme = onToggleTheme,
                    onNavigateToWatchlist = onNavigateToWatchlist,
                )
            },
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }
        ) { paddingValues ->
            MovieList(
                loading = loading,
                movies = movies,
                onChangeMovieScrollPosition = viewModel::onChangeMovieScrollPosition,
                page = page,
                onNextPage = {
                    viewModel.onTriggerEvent(MovieListEvent.NextPageEvent(selectedGenreId))
                },
                selectedGenreId = selectedGenreId,
                scaffoldState = scaffoldState,
                onNavigateToMovieDetailScreen = onNavigateToMovieDetailScreen,
                onToggleSaved = { movie ->
                    watchlistViewModel.onTriggerEvent(WatchlistEvent.ToggleSavedEvent(movie))
                },
                onToggleWatched = { movie ->
                    watchlistViewModel.onTriggerEvent(WatchlistEvent.ToggleWatchedEvent(movie))
                },
                contentPadding = paddingValues,
            )
        }
    }
}