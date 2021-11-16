package com.petestmart.moviespotter.deprecated

//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.MaterialTheme.typography
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Clear
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.ComposeView
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import com.petestmart.moviespotter.presentation.MainActivity
//import com.petestmart.moviespotter.ui.theme.MovieSpotterTheme
//
//class PopularMoviesTextComposeFragment : Fragment() {
//
//    private val viewModel: MovieSearchViewModel by activityViewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.movie_item_compose, container, false)
//
//        view.findViewById<ComposeView>(R.id.compose_view).setContent {
//            MovieSpotterTheme() {
//
//                @Composable
//                fun SearchIcon() {
//                    Icon(Icons.Filled.Search, "search")
//                }
//
//                @Composable
//                fun MaterialButtonToggleGroup() {
//                    val options = listOf(
//                        "Popular Movies",
//                        "Search Movies"
//                    )
//                    var selectedOption by remember {
//                        mutableStateOf("")
//                    }
//                    val onSelectionChange = { text: String ->
//                        selectedOption = text
//                    }
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        var isExpanded by remember { mutableStateOf(false) }
//                        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
//                        val apiKey = getString(R.string.api_key)
//                        val defaultCall = request.getPopularMovies(apiKey)
//                        Row(
//                            horizontalArrangement = Arrangement.SpaceEvenly,
//                            modifier = Modifier.fillMaxWidth(),
//                        ) {
//                            options.forEach { text ->
//                                Row(
//                                    modifier = Modifier
//                                        .padding(
//                                            vertical = 8.dp,
//                                        ),
//                                ) {
//                                    Text(
//                                        text = text,
//                                        style = typography.body1.merge(),
//                                        color = Color.White,
//                                        modifier = Modifier
//                                            .clip(
//                                                shape = RoundedCornerShape(
//                                                    size = 18.dp,
//                                                ),
//                                            )
//                                            .clickable {
//                                                onSelectionChange(text)
//                                            }
//                                            .background(
//                                                if (text == selectedOption) {
//                                                    Color.Magenta
//                                                } else {
//                                                    Color.Gray
//                                                }
//                                            )
//                                            .padding(
//                                                vertical = 12.dp,
//                                                horizontal = 16.dp,
//                                            ),
//                                    )
//                                }
//                            }
//                        }
//                        if (selectedOption == "Popular Movies") {
//                            (activity as MainActivity?)?.defaultSelection(defaultCall)
//                        }
//                        if (selectedOption == "Search Movies") {
//                            isExpanded = true
//                        } else {
//                            isExpanded = false
//                        }
//                        if (isExpanded) {
//                            val focusManager = LocalFocusManager.current
//                            var query by remember { mutableStateOf("") }
//                            OutlinedTextField(
//                                value = query,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(horizontal = 8.dp)
//                                    .padding(bottom = 8.dp),
//                                onValueChange = { query = it },
//                                label = { Text("Enter Movie Info") },
//                                singleLine = true,
//                                keyboardOptions = KeyboardOptions.Default.copy(
//                                    keyboardType = KeyboardType.Text,
//                                    imeAction = ImeAction.Search
//                                ),
//                                keyboardActions = KeyboardActions(onSearch = {
//                                    viewModel.setSearchTerm(query);
//                                    focusManager.clearFocus()
//                                }),
//                                leadingIcon = { SearchIcon() },
//                                trailingIcon = {
//                                    Icon(Icons.Filled.Clear,
//                                        contentDescription = "clear text",
//                                        modifier = Modifier
//                                            .clickable {
//                                                query = ""
//                                            }
//                                    )
//                                }
//                            )
//                        }
//                    }
//                }
//                Surface {
//                    MaterialButtonToggleGroup()
//                }
//            }
//        }
//        return view
//    }
//}