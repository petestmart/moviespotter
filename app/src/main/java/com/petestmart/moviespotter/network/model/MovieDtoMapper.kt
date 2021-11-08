package com.petestmart.moviespotter.network.model

import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.domain.util.DomainMapper

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            id = model.id,
            title = model.title,
            overview = model.overview,
            posterPath = model.posterPath,
            releaseDate = model.releaseDate,
            voteAverage = model.voteAverage
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(
            id = domainModel.id,
            title = domainModel.title,
            overview = domainModel.overview,
            posterPath = domainModel.posterPath,
            releaseDate = domainModel.releaseDate,
            voteAverage = domainModel.voteAverage
        )
    }

    // For future use
    fun toDomainList(initial: List<MovieDto>): List<Movie>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Movie>): List<MovieDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}