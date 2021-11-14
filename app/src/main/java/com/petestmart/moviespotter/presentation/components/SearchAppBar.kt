package com.petestmart.moviespotter.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieCategory
import com.petestmart.moviespotter.presentation.ui.movie_list.getAllMovieCategories
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction2

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    categoryScrollPosition: Int,
    categoryScrollOffsetPosition: Int,
    selectedCategory: MovieCategory?,
    onSelectedCategoryChanged: (Int?) -> Unit,
    onChangeCategoryPosition: KFunction2<Int, Int, Unit>,
    newCategorySearch: (Int?) -> Unit,

    ) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.White,
        elevation = 8.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = { newValue ->
                        onQueryChanged(newValue)
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
                                    onQueryChanged("")
                                }
                        )
                    },

                    keyboardActions = KeyboardActions(onSearch = {
                        onExecuteSearch()
                        keyboardController?.hide()
                    }),
                    textStyle = TextStyle(
                        color =
                        MaterialTheme.colors.onSurface
                    ),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )
            }
            var lazyListState = rememberLazyListState()
            var coroutineScope = rememberCoroutineScope()

            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp),
                content = {
                    coroutineScope.launch {
                        lazyListState.scrollToItem(
                            categoryScrollPosition,
                            categoryScrollOffsetPosition                        )
                    }
                    items(getAllMovieCategories()) { category ->
                        MovieCategoryChip(
                            category = category.value,
                            isSelected = selectedCategory == category,
                            onSelectedCategoryChanged = {
                                onQueryChanged("")
                                onSelectedCategoryChanged(category.id)
//                                categoryScrollPosition =
//                                    lazyListState.firstVisibleItemIndex
//                                categoryScrollOffsetPosition =
//                                    lazyListState.firstVisibleItemScrollOffset
                            },
                            { newCategorySearch(category.id) }
                        )
                    }
                })
        }
    }
}