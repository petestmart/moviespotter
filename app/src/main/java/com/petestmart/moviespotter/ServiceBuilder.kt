package com.petestmart.moviespotter

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    private fun getBaseUrl() = "https://api.themoviedb.org/"

    private val REQUEST_TIMEOUT = 3L

    private var client: OkHttpClient? = null

    fun getClient(): OkHttpClient {
        return if (client == null) {
            val client = OkHttpClient.Builder()
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .build()
            this.client = client
            client
        } else {
            client!!
        }
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}