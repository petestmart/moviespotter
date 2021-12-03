package com.petestmart.moviespotter.deprecated
//
//import android.os.Build
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.*
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.ComposeView
//import androidx.compose.ui.unit.dp
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import com.petestmart.moviespotter.presentation.BaseApplication
//import com.petestmart.moviespotter.presentation.components.*
//import com.petestmart.moviespotter.presentation.components.util.SnackbarController
//import com.petestmart.moviespotter.presentation.theme.AppTheme
//import com.petestmart.moviespotter.presentation.theme.Grey1
//import com.petestmart.moviespotter.presentation.ui.movie.MovieEvent.*
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//
//@AndroidEntryPoint
//class MovieFragment : Fragment() {
//
//    @Inject
//    lateinit var application: BaseApplication
//
//    private val snackbarController = SnackbarController(lifecycleScope)
//
//    private val viewModel: MovieViewModel by viewModels()
//
//    private var movieId: MutableState<Int> = mutableStateOf(-1)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.getInt("movieId")?.let { mId ->
//            viewModel.onTriggerEvent(GetMovieEvent(mId))
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent {

//                val loading = viewModel.loading.value
//                val movie = viewModel.movie.value
//                val scaffoldState = rememberScaffoldState()
//                val darkTheme = application.isDark.value
//
//                AppTheme(
//                    darkTheme = darkTheme,
//                    displayProgressBar = loading,
//                    scaffoldState = scaffoldState,
//                ) {
//                    Scaffold(
//                        scaffoldState = scaffoldState,
//                        snackbarHost = {
//                            scaffoldState.snackbarHostState
//                        }
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(color = if(!darkTheme) Grey1 else Color.Black)
//                        ){
//                            Surface(
//                                shape = MaterialTheme.shapes.medium,
//                                color = MaterialTheme.colors.surface,
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(4.dp)
//                            ){
//                                if(loading && movie == null){
//                                    ShimmerMovieItem(imageHeight = IMAGE_HEIGHT.dp)
//                                } else {
//                                    movie?.let {
//                                        if(it.id == null){
//                                            snackbarController.showSnackbar(
//                                                scaffoldState = scaffoldState,
//                                                message = "An error has occurred with this movie selection",
//                                                actionLabel = "OK"
//                                            )
//                                        } else {
//                                            MovieView(movie = it)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}