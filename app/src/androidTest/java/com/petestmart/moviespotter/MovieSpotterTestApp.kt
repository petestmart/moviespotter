package com.petestmart.moviespotter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

open class MovieSpotterTestApp : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var url = "http://127.0.0.1:8080"

        fun getBaseUrl(): String {
            return url
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(ServiceBuilder.getClient())
            .build()

        fun<T> buildService(service: Class<T>): T{
            return retrofit.create(service)
        }

        val request = buildService(TmdbEndpoints::class.java)
        val call = request.getMovies(getString(R.string.api_key))

        call.enqueue(object : Callback<MoviesData> {
            override fun onResponse(call: Call<MoviesData>, response: Response<MoviesData>) {
                if (response.isSuccessful) {
                    progress_bar.visibility = View.GONE
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MovieSpotterTestApp)
                        adapter = MoviesAdapter(response.body()!!.results)
                    }
                }
            }

            override fun onFailure(call: Call<MoviesData>, t: Throwable) {
                Toast.makeText(this@MovieSpotterTestApp, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        fun showToast(str: String) {
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
    }
}
