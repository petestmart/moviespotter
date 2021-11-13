package com.petestmart.moviespotter.repository

import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.network.MovieService
import com.petestmart.moviespotter.network.model.MovieDtoMapper

class MovieRepository_Impl(
    private val movieService: MovieService,
    private val mapper: MovieDtoMapper
): MovieRepository {

    override suspend fun search(token: String, query: String, page: Int):
            List<Movie> {
        return mapper.toDomainList(movieService.search(token, query, page).movies)
    }

    override suspend fun category(token: String, page: Int, genreId: Int?):
            List<Movie> {
        return mapper.toDomainList(movieService.category(token, page, genreId).movies)
    }
}