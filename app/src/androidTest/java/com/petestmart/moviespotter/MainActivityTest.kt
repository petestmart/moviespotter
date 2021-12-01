package com.petestmart.moviespotter


//// DEPRECATED

//import androidx.test.core.app.ActivityScenario
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.IdlingRegistry
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.*
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.jakewharton.espresso.OkHttp3IdlingResource
//import com.petestmart.moviespotter.FileReader.readStringFromFile
//import com.petestmart.moviespotter.presentation.MainActivity
//import okhttp3.mockwebserver.Dispatcher
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import okhttp3.mockwebserver.RecordedRequest
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class MainActivityTest {
//
//    private val mockWebServer = MockWebServer()
//
//    @Before
//    fun setup() {
//        mockWebServer.start(8080)
//        IdlingRegistry.getInstance().register(
//            ServiceBuilder.getClient()?.let {
//                OkHttp3IdlingResource.create(
//                    "okhttp",
//                    it
//                )
//            }
//        )
//    }
//
//    @Test
//    fun testSuccessfulResponse() {
//        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
//        mockWebServer.dispatcher = object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest): MockResponse {
//                return MockResponse()
//                    .setResponseCode(200)
//                    .setBody(readStringFromFile("success_response.json"))
//            }
//        }
//        onView(withId(R.id.view_container))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.progress_bar))
//            .check(matches(withEffectiveVisibility(Visibility.GONE)))
//        onView(withId(R.id.recyclerView))
//            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
//    }
//
//    @After
//    fun teardown() {
//        mockWebServer.shutdown()
//    }
//}

