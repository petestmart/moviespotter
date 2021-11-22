package com.petestmart.moviespotter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListEvent
import com.petestmart.moviespotter.presentation.ui.movie_list.PAGE_SIZE

@Composable
fun MovieList(
    loading: Boolean,
    movies: List<Movie>,
    onChangeMovieScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerEvent: (MovieListEvent) -> Unit,
    selectedGenreId: Int?
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {

        if (loading && movies.isEmpty()) {
            ShimmerMovieCardItem(
                imageHeight = 250.dp, padding = 8.dp
            )
        } else {
            LazyColumn {
                itemsIndexed(
                    items = movies
                ) { index, movie ->
                    onChangeMovieScrollPosition(index)
                    if((index + 1) >= (page * PAGE_SIZE) && !loading){
                        onTriggerEvent(MovieListEvent.NextPageEvent(selectedGenreId))
                    }
                    MovieCard(
                        movie = movie,
                        onClick = {
//                            if() {
//
//                            } else {
//
//                            }
                        }
                    )
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)
    }
}