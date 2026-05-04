package com.petestmart.moviespotter.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.petestmart.moviespotter.presentation.components.util.SnackbarController
import com.petestmart.moviespotter.presentation.navigation.Screen
import com.petestmart.moviespotter.presentation.ui.auth.LoginScreen
import com.petestmart.moviespotter.presentation.ui.auth.SignUpScreen
import com.petestmart.moviespotter.presentation.ui.movie.MovieDetailScreen
import com.petestmart.moviespotter.presentation.ui.movie.MovieDetailViewModel
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListScreen
import com.petestmart.moviespotter.presentation.ui.movie_list.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class MainActivity : AppCompatActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            // If user is already signed in, go straight to MovieList
            val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
                Screen.MovieList.route
            } else {
                Screen.Login.route
            }

            NavHost(navController = navController, startDestination = startDestination) {

                composable(route = Screen.Login.route) {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(Screen.MovieList.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        onNavigateToSignUp = {
                            navController.navigate(Screen.SignUp.route)
                        }
                    )
                }

                composable(route = Screen.SignUp.route) {
                    SignUpScreen(
                        onSignUpSuccess = {
                            navController.navigate(Screen.MovieList.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            navController.popBackStack()
                        }
                    )
                }

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
                        snackbarController = SnackbarController(lifecycleScope),
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
                    val viewModel: MovieDetailViewModel =
                        viewModel(viewModelStoreOwner, "MovieDetailViewModel", factory)
                    MovieDetailScreen(
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        movieId = navBackStackEntry.arguments?.getInt("movieId"),
                        viewModel = viewModel,
                        snackbarController = SnackbarController(lifecycleScope),
                    )
                }
            }
        }
    }
}