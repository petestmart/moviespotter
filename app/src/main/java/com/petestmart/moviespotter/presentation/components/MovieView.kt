package com.petestmart.moviespotter.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.util.DEFAULT_MOVIE_IMAGE
import com.petestmart.moviespotter.util.loadPicture
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val IMAGE_HEIGHT = 260

@RequiresApi(Build.VERSION_CODES.O)
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
        if (movie.posterPath != null) {
            movie.posterPath?.let { url ->
                val image = loadPicture(url = url, defaultImage = DEFAULT_MOVIE_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "Movie Poster",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        } else {
            val image: Painter = painterResource(id = DEFAULT_MOVIE_IMAGE)
            Image(
                painter = image,
                contentDescription = "Film Projector",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
        }
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
                            println("DATEdebug unformattedReleaseDate $unformattedReleaseDate")
                            var parsedDate = LocalDate.parse(unformattedReleaseDate.toString())
                            println("DATEdebug parsedDate $parsedDate")
                            var formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                            println("DATEdebug formatter $formatter")


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
                                text = "Runtime: $runtime minutes  | Status: $status",
                                modifier = Modifier
                                    .fillMaxWidth(0.90f)
                                    .wrapContentWidth(Alignment.Start)
                                    .padding(bottom = 8.dp),
                                style = MaterialTheme.typography.body2,
                            )
                        }
//                        Text(
//                            text = if (releaseDate != null) {
//                                "Release Date: $releaseDate | Runtime: $runtime min | Status: $status"
//                            } else {
//                                "Runtime: $runtime minutes"
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth(0.90f)
//                                .wrapContentWidth(Alignment.Start)
//                                .padding(bottom = 8.dp),
//                            style = MaterialTheme.typography.body2,
//                        )
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