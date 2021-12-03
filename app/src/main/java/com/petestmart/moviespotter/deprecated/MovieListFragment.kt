package com.petestmart.moviespotter.deprecated

//import android.os.Build
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.annotation.RequiresApi
//import androidx.compose.material.Scaffold
//import androidx.compose.material.rememberScaffoldState
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.platform.ComposeView
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.findNavController
//import com.petestmart.moviespotter.presentation.BaseApplication
//import com.petestmart.moviespotter.presentation.components.*
//import com.petestmart.moviespotter.presentation.components.util.SnackbarController
//import com.petestmart.moviespotter.presentation.theme.AppTheme
//import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListEvent.*
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//
//@AndroidEntryPoint
//class MovieListFragment : Fragment() {
//
//    @Inject
//    lateinit var application: BaseApplication
//
//    private val viewModel: MovieListViewModel by viewModels()
//
//    private val snackbarController = SnackbarController(lifecycleScope)
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    @ExperimentalComposeUiApi
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent {
//                val movies = viewModel.movies.value
//                val query = viewModel.query.value
//                val selectedCategory = viewModel.selectedCategory.value
//                val selectedGenreId = viewModel.selectedGenreId
//                val loading = viewModel.loading.value
//                val page = viewModel.page.value
//                val scaffoldState = rememberScaffoldState()
//
//                AppTheme(
//                    darkTheme = application.isDark.value,
//                    displayProgressBar = loading,
//                    scaffoldState = scaffoldState,
//                ) {
//                    Scaffold(
//                        topBar = {
//                            SearchAppBar(
//                                query = query,
//                                onQueryChanged = viewModel::onQueryChanged,
//                                onExecuteSearch = {
//                                    viewModel.onTriggerEvent(NewSearchEvent)
//                                },
//                                categoryScrollPosition = viewModel.categoryScrollPosition,
//                                selectedCategory = selectedCategory,
//                                selectedGenreId = selectedGenreId,
//                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
//                                onChangeCategoryPosition = viewModel::onChangeCategoryPosition,
//                                newCategorySearch = viewModel::newCategorySearch,
//                                onToggleTheme = {
//                                    application.toggleTheme()
//                                },
//                                snackbarController = snackbarController,
//                                scaffoldState = scaffoldState,
//                            )
//
//                        },
////                        bottomBar = {
////                            BottomNavBar()
////                        },
//                        drawerContent = {
//                            LeftDrawer()
//                        },
//                        scaffoldState = scaffoldState,
//                        snackbarHost = {
//                            scaffoldState.snackbarHostState
//                        }
//                    ) {
//                        MovieList(
//                            loading = loading,
//                            movies = movies,
//                            onChangeMovieScrollPosition = viewModel::onChangeMovieScrollPosition,
//                            page = page,
//                            onNextPage = {
//                                viewModel.onTriggerEvent(NextPageEvent(selectedGenreId))
//                            },
//                            selectedGenreId = selectedGenreId,
//                            scaffoldState = scaffoldState,
//                            snackbarController = snackbarController,
//                            navController = findNavController()
//                        )
//                    }
//                }
//            }
//        }
//    }
//}