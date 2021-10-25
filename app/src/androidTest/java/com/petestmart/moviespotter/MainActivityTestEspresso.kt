package com.petestmart.moviespotter

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTestEspresso{

    @Test
    fun test_isActivity_inView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.view_container)).check(matches(isDisplayed()))
    }
}