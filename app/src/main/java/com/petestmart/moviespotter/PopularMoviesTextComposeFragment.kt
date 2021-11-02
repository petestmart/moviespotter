package com.petestmart.moviespotter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
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
                        Column() {
                            var isExpanded by remember { mutableStateOf(false) }

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
                            if (selectedOption == "Search Movies") {
                                isExpanded = true
                            } else {
                                isExpanded = false
                            }
                            if (isExpanded) {
                                var text by remember { mutableStateOf("") }

                                OutlinedTextField(
                                    value = text,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 3.dp)
                                        .padding(bottom = 5.dp),
                                    onValueChange = { text = it },
                                    label = { Text("Enter Movie Info") }
                                )
                            }
                        }
//                        (activity as MainActivity?)?.viewSelection(selectedOption)
                    }
                    Surface {
                        MaterialButtonToggleGroup()
                    }
                }
            }
        }
        return view
    }
}