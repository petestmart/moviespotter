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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.petestmart.moviespotter.R
import com.petestmart.moviespotter.presentation.components.util.SnackbarController
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieCategory
import com.petestmart.moviespotter.presentation.ui.movie_list.getAllMovieCategories
import kotlin.reflect.KFunction1
import kotlin.reflect.KSuspendFunction1

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    categoryScrollPosition: Int,
    selectedCategory: MovieCategory?,
    selectedGenreId: Int?,
    onSelectedCategoryChanged: (Int?) -> Unit,
    onChangeCategoryPosition: KFunction1<Int, Unit>,
    newCategorySearch: KFunction1<Int?, Unit>,
    onToggleTheme: () -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,

    ) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .fillMaxWidth(0.88f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = { newValue ->
                        if (newValue.contains("\n")){
                            newValue.replace("\n", "")
                            println("DEBUG-ONQUERYCHANGED: " + newValue)
                            onExecuteSearch()
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
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
                    singleLine = true,
                    maxLines = 1,
                    keyboardActions = KeyboardActions(onSearch = {
                        if (query == ""){
                            snackbarController.showSnackbar(
                                scaffoldState = scaffoldState,
                                message = "Enter a Search Term",
                                actionLabel = "OK"
                            )
                        } else {
                            onExecuteSearch()
                        }
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        focusedIndicatorColor = MaterialTheme.colors.onSurface,
                        cursorColor = MaterialTheme.colors.onSurface,
                        focusedLabelColor = MaterialTheme.colors.onSurface,
                    ),
                )
                ConstraintLayout(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    val menu = createRef()
                    ThemeIconButton(
                        onToggleTheme,
                        modifier = Modifier
                            .constrainAs(menu) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            }
            var lazyListState = rememberLazyListState()
            var categoryScrollPosition: Int

            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .padding(bottom = 8.dp),
                content = {
                    items(getAllMovieCategories()) { category ->
                        MovieCategoryChip(

                            category = category.value,
                            isSelected = selectedCategory == category,
                            onSelectedCategoryChanged = {
                                onQueryChanged("")
                                onSelectedCategoryChanged(category.id)
                                categoryScrollPosition =
                                    lazyListState.firstVisibleItemIndex
                                focusManager.clearFocus()
                            },
                            { newCategorySearch(category.id) }
                        )
                    }
                }
            )
        }
    }
}