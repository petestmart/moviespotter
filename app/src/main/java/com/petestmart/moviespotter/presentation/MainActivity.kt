package com.petestmart.moviespotter.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.petestmart.moviespotter.presentation.navigation.Screen
import com.petestmart.moviespotter.presentation.ui.movie.MovieDetailScreen
import com.petestmart.moviespotter.presentation.ui.movie.MovieViewModel
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListScreen
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
open class MainActivity : AppCompatActivity() {

    @ExperimentalComposeUiApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.MovieList.route) {

                composable(route = Screen.MovieList.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
                    val viewModel: MovieListViewModel =
                        viewModel(viewModelStoreOwner, "MovieListViewModel", factory)
                    MovieListScreen(
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        onToggleTheme = (application as BaseApplication)::toggleTheme,
                        viewModel = viewModel,
                        onNavigateToMovieDetailScreen = navController::navigate,
                    )
                }

                composable(
                    route = Screen.MovieDetail.route + "/{movieId}",
                    arguments = listOf(navArgument("movieId") {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
                    val viewModel: MovieViewModel =
                        viewModel(viewModelStoreOwner, "MovieDetailViewModel", factory)
                    MovieDetailScreen(
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        movieId = navBackStackEntry.arguments?.getInt("movieId"),
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}
