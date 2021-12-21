package com.petestmart.moviespotter.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "genres")
    var genres: String,

    @ColumnInfo(name = "tagline")
    var tagline: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "poster_path")
    var posterPath: String,

    @ColumnInfo(name = "release_date")
    var releaseDate: Date,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,

    @ColumnInfo(name = "runtime")
    var runtime: Int,

    @ColumnInfo(name = "budget")
    var budget: Int,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "date_added")
    var dateAdded: Long,

    @ColumnInfo(name = "date_updated")
    var dateUpdated: Long,

    @ColumnInfo(name = "date_refreshed")
    var dateRefreshed: Long,

//    Example of list
//    SQL doesn't use list. Convert to comma separated strings
//    @ColumnInfo(name = "ex_list_of_things")
//    var list: String = "",
)