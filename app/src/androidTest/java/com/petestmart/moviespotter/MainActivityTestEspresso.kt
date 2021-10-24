package com.petestmart.moviespotter

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ActivityScenario
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTestEspresso{

    @Test
    fun test_isActivity_inView() {
        val activityScenario = ActivityScneario.launch(MainActivity::class.java)
    }
}