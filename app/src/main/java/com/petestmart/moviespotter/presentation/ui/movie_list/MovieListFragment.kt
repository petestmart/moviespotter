package com.petestmart.moviespotter.presentation.ui.movie_list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.petestmart.moviespotter.presentation.BaseApplication
import com.petestmart.moviespotter.presentation.components.*
import com.petestmart.moviespotter.presentation.components.util.SnackbarController
import com.petestmart.moviespotter.presentation.theme.AppTheme
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListEvent.*
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListViewModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: MovieListViewModel by viewModels()

    private val snackbarController = SnackbarController(lifecycleScope)

    @RequiresApi(Build.VERSION_CODES.O)
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
                val selectedCategory = viewModel.selectedCategory.value
                val selectedGenreId = viewModel.selectedGenreId
                val loading = viewModel.loading.value
                val page = viewModel.page.value
                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                ) {
                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    viewModel.onTriggerEvent(NewSearchEvent)
                                },
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
//                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        MovieList(
                            loading = loading,
                            movies = movies,
                            onChangeMovieScrollPosition = viewModel::onChangeMovieScrollPosition,
                            page = page,
                            onNextPage = {
                                viewModel.onTriggerEvent(NextPageEvent(selectedGenreId))
                            },
                            selectedGenreId = selectedGenreId,
                            scaffoldState = scaffoldState,
                            snackbarController = snackbarController,
                            navController = findNavController()
                        )
                    }
                }
            }
        }
    }
}