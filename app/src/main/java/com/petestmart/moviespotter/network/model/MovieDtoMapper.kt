package com.petestmart.moviespotter.network.model

import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.domain.util.DomainMapper
import com.petestmart.moviespotter.util.DateUtils

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            id = model.id,
            title = model.title,
            tagline = model.tagline,
            overview = model.overview,
            posterPath = model.posterPath,
            releaseDate = DateUtils.stringToDate(model.releaseDate),
            voteAverage = model.voteAverage,
            runtime = model.runtime,
            budget = model.budget,
            status = model.status,
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(
            id = domainModel.id,
            title = domainModel.title,
            tagline = domainModel.tagline,
            overview = domainModel.overview,
            posterPath = domainModel.posterPath,
            releaseDate = DateUtils.dateToString(domainModel.releaseDate),
            voteAverage = domainModel.voteAverage,
            runtime = domainModel.runtime,
            budget = domainModel.budget,
            status = domainModel.status,
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