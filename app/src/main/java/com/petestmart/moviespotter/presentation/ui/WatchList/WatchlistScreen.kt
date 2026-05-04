package com.petestmart.moviespotter.presentation.ui.watchlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.presentation.components.MovieCard
import com.petestmart.moviespotter.presentation.theme.AppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WatchlistScreen(
    isDarkTheme: Boolean,
    onNavigateToMovieDetailScreen: (String) -> Unit,
    viewModel: WatchlistViewModel = hiltViewModel(),
) {
    val savedMovies = viewModel.savedMovies.value
    val watchedMovies = viewModel.watchedMovies.value
    val loading = viewModel.loading.value
    val scaffoldState = rememberScaffoldState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Want to Watch", "Watched")

    AppTheme(
        darkTheme = isDarkTheme,
        displayProgressBar = loading,
        scaffoldState = scaffoldState,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.background)
                    .padding(paddingValues)
            ) {
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(text = title) }
                        )
                    }
                }

                val movies = if (selectedTab == 0) savedMovies else watchedMovies

                if (movies.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (selectedTab == 0)
                                "No saved movies yet"
                            else
                                "No watched movies yet",
                            style = MaterialTheme.typography.h6,
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(
                            items = movies
                        ) { _, movie ->
                            MovieCard(
                                movie = movie,
                                onClick = {
                                    if (movie.id != null) {
                                        val route = "movieDetail/${movie.id}"
                                        onNavigateToMovieDetailScreen(route)
                                    }
                                },
                                onToggleSaved = {
                                    viewModel.onTriggerEvent(
                                        WatchlistEvent.ToggleSavedEvent(movie)
                                    )
                                },
                                onToggleWatched = {
                                    viewModel.onTriggerEvent(
                                        WatchlistEvent.ToggleWatchedEvent(movie)
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}