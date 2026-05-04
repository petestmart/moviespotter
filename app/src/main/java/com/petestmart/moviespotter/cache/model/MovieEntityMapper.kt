package com.petestmart.moviespotter.cache.model

import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.domain.util.DomainMapper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovieEntityMapper : DomainMapper<MovieEntity, Movie> {

    override fun mapToDomainModel(model: MovieEntity): Movie {
        return Movie(
            id = model.id,
            title = model.title,
            tagline = model.tagline,
            overview = model.overview,
            posterPath = model.posterPath,
            releaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(model.releaseDate)),
            voteAverage = model.voteAverage,
            runtime = model.runtime,
            isSaved = model.isSaved,
            isWatched = model.isWatched,
            savedAt = model.savedAt,
            watchedAt = model.watchedAt,
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieEntity {
        return MovieEntity(
            id = domainModel.id ?: 0,
            title = domainModel.title ?: "",
            tagline = domainModel.tagline ?: "",
            overview = domainModel.overview ?: "",
            posterPath = domainModel.posterPath ?: "",
            genres = "",
            releaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(domainModel.releaseDate ?: "1970-01-01")?.time ?: 0L,
            voteAverage = domainModel.voteAverage ?: 0.0,
            runtime = domainModel.runtime ?: 0,
            dateAdded = System.currentTimeMillis(),
            dateUpdated = System.currentTimeMillis(),
            dateRefreshed = System.currentTimeMillis(),
        )
    }

    fun toDomainList(initial: List<MovieEntity>): List<Movie> {
        return initial.map { mapToDomainModel(it) }
    }
}