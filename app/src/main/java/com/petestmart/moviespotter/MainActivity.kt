package com.petestmart.moviespotter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.moviespotter.R
import com.petestmart.moviespotter.ui.theme.MovieSpotterTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieSpotterTheme() {
                setContentView(R.layout.activity_main)
            }
        }

        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
        val call = request.getMovies(getString(R.string.api_key))

        call.enqueue(object : Callback<PopularMovies> {
            override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                if (response.isSuccessful) {
                    progress_bar.visibility = View.GONE
                    setContent {
                        MovieSpotterTheme() {
                            MovieList(response.body()!!.results)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun MovieList(results: List<Result>) {
    Text(text = "Popular Movies")
    LazyColumn {
        items(results) { result ->
            MovieCard(result)
        }
    }
}

@Composable
fun MovieCard(
    mov: Result
) {
    val padding = 16.dp
    Card(
        Modifier.padding(padding),
        shape = RoundedCornerShape(16.dp)
    )
    {
        var isExpanded by remember { mutableStateOf(false) }

        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .clickable { isExpanded = !isExpanded },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${mov.poster_path}"),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .size(250.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(text = mov.title)

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .padding(all = 8.dp)
//                    .clickable { isExpanded = !isExpanded }
            ) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(all = 4.dp),
                    ) {
                        Text(text = if (isExpanded)
                                mov.overview + "\n" +
                                "Release Date: " + mov.release_date + "\n" +
                                "User Score: " + mov.vote_average.toString()
                                else mov.overview.substring(0,50) + "...",
                            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                            style = MaterialTheme.typography.body2)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

        }
    }
}
