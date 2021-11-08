package com.petestmart.moviespotter.repository

import com.petestmart.moviespotter.domain.model.Movie

interface MovieRepository {

    suspend fun search(token: String, query: String, page: Int): List<Movie>

    suspend fun popular(token: String): List<Movie>
}