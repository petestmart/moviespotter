package com.petestmart.moviespotter.presentation.ui.movie_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.petestmart.moviespotter.R
import com.petestmart.moviespotter.util.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        return ComposeView(requireContext()).apply {
            setContent {

                val movies = viewModel.movies.value

                for(movie in movies) {
                    Log.d(TAG, "onCreateView: ${movie.title}")
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "MovieList Fragment",
                        style = TextStyle(
                            fontSize = 30.sp)
                        )
                    Spacer(modifier = Modifier.padding(10.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}