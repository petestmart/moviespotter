package com.petestmart.moviespotter.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.util.DEFAULT_MOVIE_IMAGE
import com.petestmart.moviespotter.util.POSTER_BASE_URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(POSTER_BASE_URL + movie.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = "Movie Poster",
                placeholder = painterResource(DEFAULT_MOVIE_IMAGE),
                error = painterResource(DEFAULT_MOVIE_IMAGE),
                fallback = painterResource(DEFAULT_MOVIE_IMAGE),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                contentScale = ContentScale.Fit
            )

            movie.title?.let { title ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 12.dp,
                            horizontal = 12.dp
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.80f)
                            .wrapContentWidth(Alignment.Start),
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.h2
                        )
                        var unformattedReleaseDate = movie.releaseDate
                        var parsedDate = LocalDate.parse(unformattedReleaseDate.toString())
                        var formatter = DateTimeFormatter.ofPattern("yyyy")

                        val releaseDate = formatter.format(parsedDate)
                        Text(
                            text = "($releaseDate)",
                            modifier = Modifier
                                .fillMaxWidth(0.90f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme
                                .typography.body2,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .padding(
                                top = 12.dp
                            )
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                        Text(
                            text = movie.voteAverage.toString(),
                            style = MaterialTheme
                                .typography.body2,
                        )
                        Text(
                            text = "User Score",
                            style = MaterialTheme
                                .typography.h6,
                        )
                    }
                }
            }
        }
    }
}