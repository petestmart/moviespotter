package com.petestmart.moviespotter.presentation.components

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.petestmart.moviespotter.R
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.presentation.components.util.SnackbarController
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListEvent
import com.petestmart.moviespotter.presentation.ui.movie_list.PAGE_SIZE
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieList(
    loading: Boolean,
    movies: List<Movie>,
    onChangeMovieScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (MovieListEvent) -> Unit,
    selectedGenreId: Int?,
    scaffoldState: ScaffoldState,
//    snackbarController: SnackbarController,
//    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {

        if (loading && movies.isEmpty()) {
            ShimmerMovieCardItem(
                imageHeight = 450.dp, padding = 8.dp
            )
        } else {
            LazyColumn {
                itemsIndexed(
                    items = movies
                ) { index, movie ->
                    onChangeMovieScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onNextPage(
                            MovieListEvent.NextPageEvent(
                                selectedGenreId
                            )
                        )
                    }
                    MovieCard(
                        movie = movie,
                        onClick = {
                            if(movie.id != null){
                                val bundle = Bundle ()
                                bundle.putInt("movieId", movie.id)
//                                navController.navigate(TODO(), bundle)
                            } else {
//                                snackbarController.getScope().launch {
//                                    snackbarController.showSnackbar(
//                                        scaffoldState = scaffoldState,
//                                        message = "Movie Error",
//                                        actionLabel = "Ok",
//                                    )
//                                }
                            }
                        }
                    )
                }
            }
        }
    }
}