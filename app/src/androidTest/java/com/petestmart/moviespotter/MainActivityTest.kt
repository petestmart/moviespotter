package com.petestmart.moviespotter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.petestmart.moviespotter.R
import com.petestmart.moviespotter.FileReader.readStringFromFile
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
        IdlingTestResource.registerIdlingResource(ServiceBuilder.getClient())

//        IdlingRegistry.getInstance().register(
//            ServiceBuilder.getClient()?.let {
//                OkHttp3IdlingResource.create(
//                    "okhttp",
//                    it
//                )
//            }
//        )
    }

    @Test
    fun testSuccessfulResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(readStringFromFile("success_response.json"))
            }

        }
        activityRule.launchActivity(null)

        onView(withId(R.id.progress_bar))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.recyclerView))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testFailedResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }

        activityRule.launchActivity(null)

        onView(withId(R.id.progress_bar))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.recyclerView))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}

