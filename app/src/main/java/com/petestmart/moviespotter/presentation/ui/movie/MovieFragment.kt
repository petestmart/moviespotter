package com.petestmart.moviespotter.presentation.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.petestmart.moviespotter.presentation.ui.movie.MovieEvent.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()

    private var movieId: MutableState<Int> = mutableStateOf(-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("movieId")?.let { mId ->
            viewModel.onTriggerEvent(GetMovieEvent(mId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val loading = viewModel.loading.value
                val movie = viewModel.movie.value

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = movie?.let {
                            "Selected Movie Title: ${movie.title}"
                        }?: "Loading...",
                    )
                }
            }
        }
    }
}