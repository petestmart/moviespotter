package com.petestmart.moviespotter.presentation.ui.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.petestmart.moviespotter.presentation.BaseApplication
import com.petestmart.moviespotter.presentation.components.CircularIndeterminateProgressBar
import com.petestmart.moviespotter.presentation.components.MovieCard
import com.petestmart.moviespotter.presentation.components.SearchAppBar
import com.petestmart.moviespotter.presentation.components.ShimmerMovieCardItem
import com.petestmart.moviespotter.presentation.theme.AppTheme
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListViewModel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
                    val selectedGenreId = viewModel.selectedGenreId.value
                    val loading = viewModel.loading.value
                    val page = viewModel.page.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = viewModel::onTriggerEvent,
                                categoryScrollPosition = viewModel.categoryScrollPosition,
                                selectedCategory = selectedCategory,
                                selectedGenreId = selectedGenreId,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onChangeCategoryPosition = viewModel::onChangeCategoryPosition,
                                newCategorySearch = viewModel::newCategorySearch,
                                onToggleTheme = {
                                    application.toggleTheme()
                                }
                            )
                        },
//                        bottomBar = {
//                            BottomNavBar()
//                        },
//                        drawerContent = {
//                            LeftDrawer()
//                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colors.background)
                        ) {

                            if (loading && movies.isEmpty()) {
                                ShimmerMovieCardItem(
                                    imageHeight = 250.dp, padding = 8.dp
                                )
                            } else {
                                LazyColumn {
                                    itemsIndexed(
                                        items = movies
                                    ) { index, movie ->
                                        viewModel
                                            .onChangeMovieScrollPosition(index)
                                        if((index + 1) >= (page * PAGE_SIZE) && !loading){
                                                viewModel.onTriggerEvent(MovieListEvent.NextPageEvent)
                                        }
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