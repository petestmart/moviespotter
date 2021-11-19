package com.petestmart.moviespotter.presentation.ui.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.petestmart.moviespotter.presentation.BaseApplication
import com.petestmart.moviespotter.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.petestmart.moviespotter.presentation.components.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: MovieListViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(
                    darkTheme = application.isDark.value
                ) {
                    val movies = viewModel.movies.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val loading = viewModel.loading.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = viewModel::newSearch,
                                categoryScrollPosition = viewModel.categoryScrollPosition,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onChangeCategoryPosition = viewModel::onChangeCategoryPosition,
                                newCategorySearch = viewModel::newCategorySearch,
                                onToggleTheme = {
                                    application.toggleTheme()
                                }
                            )
                        },
                        bottomBar = {
//                            BottomNavBar()
                        },
                        drawerContent = {
//                            LeftDrawer()
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.background)
                        ) {

                            if (loading) {
                                ShimmerMovieCardItem(
                                    imageHeight = 250.dp, padding = 8.dp
                                )
                            } else {
                                LazyColumn {
                                    itemsIndexed(
                                        items = movies
                                    ) { index, movie ->
                                        MovieCard(movie = movie, onClick = {})
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }
                    }
                }
            }
        }
    }
}