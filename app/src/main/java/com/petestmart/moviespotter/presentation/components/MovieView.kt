package com.petestmart.moviespotter.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

const val IMAGE_HEIGHT = 260

@Composable
fun MovieView(
    movie: Movie,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
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
                .height(IMAGE_HEIGHT.dp),
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            movie.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.80f)
                            .wrapContentWidth(Alignment.Start),
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        Column(){
                            var unformattedReleaseDate = movie.releaseDate
                            var parsedDate = LocalDate.parse(unformattedReleaseDate.toString())
                            var formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

                            val releaseDate = formatter.format(parsedDate)
                            val runtime = movie.runtime.toString()
                            val status = movie.status.toString()
                            Text(
                                text =
                                    "Release Date: $releaseDate",
                                modifier = Modifier
                                    .fillMaxWidth(0.90f)
                                    .wrapContentWidth(Alignment.Start),
                                style = MaterialTheme.typography.body2,
                            )
                            Text(
                                text = "Runtime: $runtime minutes | Status: $status",
                                modifier = Modifier
                                    .fillMaxWidth(0.90f)
                                    .wrapContentWidth(Alignment.Start)
                                    .padding(bottom = 8.dp),
                                style = MaterialTheme.typography.body2,
                            )
                        }
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
                movie.overview?.let { overview ->
                    Text(
                        text = overview,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
}