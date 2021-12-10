package com.petestmart.moviespotter.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.petestmart.moviespotter.cache.MovieDao
import com.petestmart.moviespotter.cache.model.MovieEntity

@Database(entities = [MovieEntity::class], version =1)
abstract class MovieDatabase: RoomDatabase(){

    abstract fun recipeDao(): MovieDao

    companion object{

        val DATABASE_NAME = "movie_db"
    }
}