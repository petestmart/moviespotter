package com.petestmart.moviespotter.cache.model

import com.petestmart.moviespotter.domain.model.Movie
import com.petestmart.moviespotter.domain.util.DomainMapper
import com.petestmart.moviespotter.util.DateUtils

class MovieEntityMapper : DomainMapper<MovieEntity, Movie> {

    override fun mapToDomainModel(model: MovieEntity): Movie {
        return Movie(
            id = model.id,
            title = model.title,
            genres = model.genres,
            tagline = model.tagline,
            overview = model.overview,
            posterPath = model.posterPath,
            releaseDate = model.releaseDate,
            voteAverage = model.voteAverage,
            runtime = model.runtime,
            budget = model.budget,
            status = model.status,
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated,
            dateRefreshed = model.dateRefreshed,
//            listExample = convertListItemsToList(model.listItems), // parse "listItem 1, listItem 2, ..." to ["listItem 1", "listItem 2", ...]
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieEntity {
        return MovieEntity(
            id = domainModel.id,
            title = domainModel.title,
            genres = domainModel.genres,
            tagline = domainModel.tagline,
            overview = domainModel.overview,
            posterPath = domainModel.posterPath,
            releaseDate = domainModel.releaseDate,
            voteAverage = domainModel.voteAverage,
            runtime = domainModel.runtime,
            budget = domainModel.budget,
            status = domainModel.status,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated,
            dateRefreshed = domainModel.dateRefreshed,
            //            listExample = convertListToString(domainModel.listItems), // parse "listItem 1, listItem 2, ..." to ["listItem 1", "listItem 2", ...]
        )
    }

    private fun convertListToString(listItems: List<String>): String {
        val listItemsString = StringBuilder()
        for(listItem in listItems){
            listItemsString.append("$listItem,")
        }
        return listItemsString.toString()
    }

    private fun convertListItemsToList(listItemsString: String?): List<String>{
        val list: ArrayList<String> = ArrayList()
        listItemsString?.let {
            for(listItem in it.split(",")){
                list.add(listItem)
            }
        }
        return list
    }
}