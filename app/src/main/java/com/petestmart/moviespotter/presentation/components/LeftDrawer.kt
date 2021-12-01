package com.petestmart.moviespotter.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.petestmart.moviespotter.BuildConfig
import com.petestmart.moviespotter.util.DEFAULT_MOVIE_IMAGE


@Composable
fun LeftDrawer() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Surface(
            modifier = Modifier,
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                val image: Painter = painterResource(id = DEFAULT_MOVIE_IMAGE)
                Image(
                    painter = image,
                    contentDescription = "Film Projector",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = "MovieSpotter",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier
                        .padding(8.dp),
                )
            }
        }
        Text(
            text = "MovieSpotter Version ${BuildConfig.VERSION_NAME} / Build #${BuildConfig.VERSION_CODE}",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.align(BottomStart)
        )
    }
}