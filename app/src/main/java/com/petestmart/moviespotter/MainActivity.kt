package com.petestmart.moviespotter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_main.progress_bar

open class MainActivity : AppCompatActivity() {
    private val viewModel: MovieSearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            MovieSpotterTheme() {
        setContentView(R.layout.activity_main)
//            }
//        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.popular_text_compose, PopularMoviesTextComposeFragment())
            .commit()

        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
        val apiKey = getString(R.string.api_key)
        var searchString = ""
        viewModel.searchTerm.observe(this) {
            searchString = it;
            callMovies(call = request.getSearchMovies(apiKey, searchString))
        }

        var call = request.getPopularMovies(apiKey)

        callMovies(call)
    }

    fun callMovies(call: Call<MoviesData>) {
        call.enqueue(object : Callback<MoviesData> {
            override fun onResponse(call: Call<MoviesData>, response: Response<MoviesData>) {
                if (response.isSuccessful) {
                    progress_bar.visibility = View.GONE
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MoviesAdapter(response.body()!!.results as List<Result>)
                    }
//                    setContent {
//                        MovieSpotterTheme() {
//                            MovieList(response.body()!!.results)
//                        }
//                    }
                }
            }

            override fun onFailure(call: Call<MoviesData>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

//@Composable
//fun MovieList(results: List<Result>) {
//    Column(
//    ) {
//        @Composable
//        fun ShapeableView() {
//            Box(
//                modifier = Modifier
//                    .size(150.dp)
//            )
//        }
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            elevation = 2.dp,
//            shape = RoundedCornerShape(16.dp)
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ){
//                Text(
//                    text = "Popular Movies",
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(all = 4.dp)
//                )
//            }
//        }
//        LazyColumn {
//            items(results) { result ->
//                MovieCard(result)
//            }
//        }
//    }
//}
//
//@Composable
//fun MovieCard(
//    mov: Result
//) {
//    val padding = 16.dp
//    Card(
//        Modifier.padding(padding),
//        shape = RoundedCornerShape(16.dp),
//        elevation = 2.dp,
//    )
//    {
//        var isExpanded by remember { mutableStateOf(false) }
//
//        val surfaceColor: Color by animateColorAsState(
//            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
//        )
//        Column(
//            modifier = Modifier
//                .padding(all = 8.dp)
//                .clickable { isExpanded = !isExpanded },
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${mov.poster_path}"),
//                contentDescription = "Movie Poster",
//                modifier = Modifier
//                    .size(250.dp)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = mov.title)
//
//            Spacer(modifier = Modifier.height(8.dp))
//            Column(
//                modifier = Modifier
//                    .padding(all = 8.dp)
////                    .clickable { isExpanded = !isExpanded }
//            ) {
//                Surface(
//                    shape = MaterialTheme.shapes.medium,
//                    elevation = 1.dp,
//                    color = surfaceColor,
//                    modifier = Modifier
//                        .animateContentSize()
//                        .padding(1.dp)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(all = 4.dp),
//                    ) {
//                        Text(
//                            text = if (isExpanded)
//                                mov.overview + "\n" +
//                                        "Release Date: " + mov.release_date + "\n" +
//                                        "User Score: " + mov.vote_average.toString()
//                            else mov.overview.substring(0, 50) + "...",
//                            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
//                            style = MaterialTheme.typography.body2
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
