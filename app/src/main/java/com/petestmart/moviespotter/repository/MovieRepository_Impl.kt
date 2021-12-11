package com.petestmart.moviespotter.repository

import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.network.MovieService
import com.petestmart.moviespotter.network.model.MovieDtoMapper

class MovieRepository_Impl(
    private val movieService: MovieService,
    private val mapper: MovieDtoMapper
) : MovieRepository {

    override suspend fun search(
        token: String,
        includeAdult: Boolean,
        includeVideo: Boolean,
        query: String,
        page: Int
    ):
            List<Movie> {
        return mapper.toDomainList(
            movieService.search(
                token,
                includeAdult,
                includeVideo,
                query,
                page
            ).movies
        )
    }

    override suspend fun get(
        movieId: Int,
        token: String,
        language: String,
    ): Movie {
        return mapper.mapToDomainModel(
            movieService.get(
                movieId = movieId,
                token = token,
                language = language,
            )
        )
    }

    override suspend fun category(
        token: String,
        language: String,
        sortBy: String,
        includeAdult: Boolean,
        includeVideo: Boolean,
        page: Int,
        genreId: Int?
    ):
            List<Movie> {
        return mapper.toDomainList(
            movieService.category(
                token,
                language,
                sortBy,
                includeAdult,
                includeVideo,
                page,
                genreId
            ).movies
        )
    }
}