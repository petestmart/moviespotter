package com.petestmart.moviespotter;

import androidx.test.espresso.IdlingRegistry;

import okhttp3.OkHttpClient;

public class IdlingTestResource {
    public static void registerIdlingResource(OkHttpClient client) {
        IdlingRegistry.getInstance().register();
//        final String STRING_TO_BE_TYPED = "Espresso";
//
//        IdlingResource mIdlingResource;
//
//        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);
//        activityScenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
//            @Override
//            public void perform(MainActivity activity) {
//                mIdlingResource = activity.getIdlingResource();
//                // To prove that the test fails, omit this call:
//                IdlingRegistry.getInstance().register(mIdlingResource);
//            }
//        });
    }
}
