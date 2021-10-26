package com.petestmart.moviespotter


import com.petestmart.moviespotter.MoviesAdapterTestObject.moviesAdapterTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviesAdapterTest {

    @Test
    fun moviesAdapterTest() {
        val key = 20
        val value = "movies"
        val result = moviesAdapterTest(key, value)
        Assert.assertEquals(result,true)
    }
}