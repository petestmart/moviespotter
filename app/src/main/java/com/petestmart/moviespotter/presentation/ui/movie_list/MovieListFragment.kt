package com.petestmart.moviespotter.presentation.ui.movie_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.petestmart.moviespotter.R
import com.petestmart.moviespotter.presentation.components.MovieCard
import com.petestmart.moviespotter.presentation.components.MovieCategoryChip
import com.petestmart.moviespotter.util.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val movies = viewModel.movies.value
                val query = viewModel.query.value
                val keyboardController = LocalSoftwareKeyboardController.current

                Column {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Color.White,
                        elevation = 8.dp
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ) {
                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(8.dp),
                                    value = query,
                                    onValueChange = { newValue ->
                                        viewModel.onQueryChanged(newValue)
                                    },
                                    label = {
                                        Text(text = "Search Movies")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Search
                                    ),
                                    leadingIcon = { Icon(Icons.Filled.Search, "Search") },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.Clear,
                                            contentDescription = "clear text",
                                            modifier = Modifier
                                                .clickable {
                                                    viewModel.onQueryChanged("")
                                                }
                                        )
                                    },
                                    keyboardActions = KeyboardActions(onSearch = {
                                        viewModel.newSearch(query)
                                        keyboardController?.hide()
                                    }),
                                    textStyle = TextStyle(
                                        color =
                                        MaterialTheme.colors.onSurface
                                    ),
                                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                                )
                            }
                            LazyRow(content = {
                                items(getAllMovieCategories()) { category ->
                                    MovieCategoryChip(
                                        category = category.value,
                                        onExecuteSearch = {
                                            viewModel.onQueryChanged("")
                                            viewModel.newCategorySearch(99)
                                        }
                                    )
                                }
                            })
                        }
                    }
                    LazyColumn {
                        itemsIndexed(
                            items = movies
                        ) { index, movie ->
                            MovieCard(movie = movie, onClick = {})
                        }
                    }
                }
            }
        }
    }
}