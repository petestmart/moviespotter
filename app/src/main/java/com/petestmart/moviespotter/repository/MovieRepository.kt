package com.petestmart.moviespotter.repository

import com.petestmart.moviespotter.domain.model.Movie

interface MovieRepository {

    suspend fun search(token: String, query: String, page: Int): List<Movie>

    suspend fun category(token: String, page: Int, genreId: Int?): List<Movie>
}