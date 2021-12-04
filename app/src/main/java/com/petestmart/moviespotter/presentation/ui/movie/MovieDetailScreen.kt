package com.petestmart.moviespotter.presentation.ui.movie

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.petestmart.moviespotter.util.TAG

@Composable
fun MovieDetailScreen (
    isDarkTheme: Boolean,
    movieId: Int?,
    detailViewModel: MovieDetailViewModel,
){
    Log.d(TAG, "MovieDetailScreen: ${detailViewModel}")
    Text("Movie Id: ${movieId}", style = MaterialTheme.typography.h2)
//    val loading = detailViewModel.loading.value
//    val movie = detailViewModel.movie.value
//    val scaffoldState = rememberScaffoldState()
//    val darkTheme = application.isDark.value
//
//    AppTheme(
//        darkTheme = darkTheme,
//        displayProgressBar = loading,
//        scaffoldState = scaffoldState,
//    ) {
//        Scaffold(
//            scaffoldState = scaffoldState,
//            snackbarHost = {
//                scaffoldState.snackbarHostState
//            }
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(color = if(!darkTheme) Grey1 else Color.Black)
//            ){
//                Surface(
//                    shape = MaterialTheme.shapes.medium,
//                    color = MaterialTheme.colors.surface,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(4.dp)
//                ){
//                    if(loading && movie == null){
//                        ShimmerMovieItem(imageHeight = IMAGE_HEIGHT.dp)
//                    } else {
//                        movie?.let {
//                            if(it.id == null){
//                                snackbarController.showSnackbar(
//                                    scaffoldState = scaffoldState,
//                                    message = "An error has occurred with this movie selection",
//                                    actionLabel = "OK"
//                                )
//                            } else {
//                                MovieView(movie = it)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}