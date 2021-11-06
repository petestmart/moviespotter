package com.petestmart.moviespotter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.petestmart.moviespotter.ui.theme.MovieSpotterTheme

class PopularMoviesTextComposeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_item_compose, container, false)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            MovieSpotterTheme() {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    @Composable
                    fun MaterialButtonToggleGroup() {
                        val options = listOf(
                            "Popular Movies",
                            "Search Movies"
                        )
                        var selectedOption by remember {
                            mutableStateOf("")
                        }
                        val onSelectionChange = { text: String ->
                            selectedOption = text
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            options.forEach { text ->
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 8.dp,
                                        ),
                                ) {
                                    Text(
                                        text = text,
                                        style = typography.body1.merge(),
                                        color = Color.White,
                                        modifier = Modifier
                                            .clip(
                                                shape = RoundedCornerShape(
                                                    size = 12.dp,
                                                ),
                                            )
                                            .clickable {
                                                onSelectionChange(text)
                                            }
                                            .background(
                                                if (text == selectedOption) {
                                                    Color.Magenta
                                                } else {
                                                    Color.Gray
                                                }
                                            )
                                            .padding(
                                                vertical = 12.dp,
                                                horizontal = 16.dp,
                                            ),
                                    )
                                }
                            }
                        }
                    }
                    Surface() {
                        MaterialButtonToggleGroup()
                    }
                }
            }
        }
        return view
    }
}