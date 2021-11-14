package com.petestmart.moviespotter.presentation.ui.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.petestmart.moviespotter.presentation.components.MovieCard
import com.petestmart.moviespotter.presentation.components.SearchAppBar
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
//                val keyboardController = LocalSoftwareKeyboardController.current
                val selectedCategory = viewModel.selectedCategory.value

                Column {
                    SearchAppBar(
                        query = query,
                        onQueryChanged = viewModel::onQueryChanged,
                        onExecuteSearch = viewModel::newSearch,
                        categoryScrollPosition = viewModel.categoryScrollPosition,
                        categoryScrollOffsetPosition = viewModel.categoryScrollOffsetPosition,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onChangeCategoryPosition = viewModel::onChangeCategoryPosition,
                        newCategorySearch = viewModel::newCategorySearch,
                    )
//                    Surface(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        color = Color.White,
//                        elevation = 8.dp
//                    ) {
//                        Column {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                            ) {
//                                TextField(
//                                    modifier = Modifier
//                                        .fillMaxWidth(0.9f)
//                                        .padding(8.dp),
//                                    value = query,
//                                    onValueChange = { newValue ->
//                                        viewModel.onQueryChanged(newValue)
//                                    },
//                                    label = {
//                                        Text(text = "Search Movies")
//                                    },
//                                    keyboardOptions = KeyboardOptions(
//                                        keyboardType = KeyboardType.Text,
//                                        imeAction = ImeAction.Search
//                                    ),
//                                    leadingIcon = { Icon(Icons.Filled.Search, "Search") },
//                                    trailingIcon = {
//                                        Icon(
//                                            Icons.Filled.Clear,
//                                            contentDescription = "clear text",
//                                            modifier = Modifier
//                                                .clickable {
//                                                    viewModel.onQueryChanged("")
//                                                }
//                                        )
//                                    },
//                                    keyboardActions = KeyboardActions(onSearch = {
//                                        viewModel.newSearch()
//                                        keyboardController?.hide()
//                                    }),
//                                    textStyle = TextStyle(
//                                        color =
//                                        MaterialTheme.colors.onSurface
//                                    ),
//                                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
//                                )
//                            }
//                            val lazyListState = rememberLazyListState()
//                            val coroutineScope = rememberCoroutineScope()
//
//                            LazyRow(
//                                state = lazyListState,
//                                modifier = Modifier
//                                    .padding(start = 8.dp, bottom = 8.dp),
//                                content = {
//                                    coroutineScope.launch {
//                                        lazyListState.scrollToItem(
//                                            viewModel.categoryScrollPosition,
//                                            viewModel.categoryScrollOffsetPosition
//                                        )
//                                    }
//                                    items(getAllMovieCategories()) { category ->
//                                        MovieCategoryChip(
//                                            category = category.value,
//                                            isSelected = selectedCategory == category,
//                                            onSelectedCategoryChanged = {
//                                                viewModel.onQueryChanged("")
//                                                viewModel.onSelectedCategoryChanged(category.id)
//                                                viewModel.categoryScrollPosition =
//                                                    lazyListState.firstVisibleItemIndex
//                                                viewModel.categoryScrollOffsetPosition =
//                                                    lazyListState.firstVisibleItemScrollOffset
//                                            },
//                                            { viewModel.newCategorySearch(category.id) }
//                                        )
//                                    }
//                                })
//                        }
//                    }
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