package com.petestmart.moviespotter.repository

import com.petestmart.moviespotter.domain.model.Movie

interface MovieRepository {

    suspend fun search(
        token: String,
        includeAdult: Boolean,
        includeVideo: Boolean,
        query: String,
        page: Int
    ): List<Movie>

    suspend fun category(
        token: String,
        language: String,
        sortBy: String,
        includeAdult: Boolean,
        includeVideo: Boolean,
        page: Int,
        genreId: Int?
    ): List<Movie>
}