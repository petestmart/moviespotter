package com.petestmart.moviespotter

import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.Toast
import androidx.activity.compose.setContent
import com.example.moviespotter.R
import com.petestmart.moviespotter.ui.theme.MovieSpotterTheme
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.app.Application

open class MovieSpotterTestApp : Application() {

    var url = "http://127.0.0.1:8080"

    fun getBaseUrl(): String {
        return url
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .client(ServiceBuilder.getClient())
        .build()

    private fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

    val request = buildService(TmdbEndpoints::class.java)
    val call = request.getMovies(getString(R.string.api_key))

}
