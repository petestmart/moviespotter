package com.petestmart.moviespotter

import android.content.ClipData
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.petestmart.moviespotter.ui.theme.MovieSpotterTheme


class PopularMoviesTextComposeFragment : Fragment() {

    private val viewModel: MovieSearchViewModel by activityViewModels()

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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
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
                                var query by remember { mutableStateOf("") }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                ) {
                                    OutlinedTextField(
                                        value = query,
                                        modifier = Modifier
                                            .padding(horizontal = 3.dp)
                                            .padding(bottom = 5.dp),
                                        onValueChange = { query = it },
                                        label = { Text("Enter Movie Info") }
                                    )
                                    OutlinedButton(
                                        onClick = {
                                            viewModel.setSearchTerm(query)
                                            println("button clicked: " + query)
                                        },

                                        ) {
                                        Text("Search")
                                    }
                                }
                            }
                        }
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