package com.petestmart.moviespotter

object MoviesAdapterTestObject {

    fun moviesAdapterTest(key: Int, value: String): Boolean {
        return !(key <= 0 || value.isEmpty())
    }
}