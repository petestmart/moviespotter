package com.petestmart.moviespotter.presentation.ui.movie

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.petestmart.moviespotter.presentation.components.IMAGE_HEIGHT
import com.petestmart.moviespotter.presentation.components.MovieView
import com.petestmart.moviespotter.presentation.components.ShimmerMovieItem
import com.petestmart.moviespotter.presentation.components.util.SnackbarController
import com.petestmart.moviespotter.presentation.theme.AppTheme
import com.petestmart.moviespotter.presentation.theme.Grey1
import androidx.lifecycle.lifecycleScope

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailScreen(
    isDarkTheme: Boolean,
    movieId: Int?,
    viewModel: MovieDetailViewModel,
    snackbarController: SnackbarController
) {
    if (movieId == null) {
        TODO("Show Invalid Movie")
    } else {
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(MovieEvent.GetMovieEvent(movieId))
        }

        val loading = viewModel.loading.value
        val movie = viewModel.movie.value
        val scaffoldState = rememberScaffoldState()

        AppTheme(
            darkTheme = isDarkTheme,
            displayProgressBar = loading,
            scaffoldState = scaffoldState,
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = {
                    scaffoldState.snackbarHostState
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = if (!isDarkTheme) Grey1 else Color.Black)
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colors.surface,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                    ) {
                        if (loading && movie == null) {
                            ShimmerMovieItem(imageHeight = IMAGE_HEIGHT.dp)
                        } else {
                            movie?.let {
                                if (it.id == null) {
                                    snackbarController.showSnackbar(
                                        scaffoldState = scaffoldState,
                                        message = "An error has occurred with this movie selection",
                                        actionLabel = "OK"
                                    )
                                } else {
                                    MovieView(movie = it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}