package com.example.academia_android_projeto_final;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.idling.CountingIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppUITest {

    private CountingIdlingResource countingIdlingResource;
    private String PREFERENCES_FILENAME = "com.example.academia_android_projeto_final.Favourites";

    @Before
    public void setUp() {

        countingIdlingResource = new CountingIdlingResource("RetrofitIdlingResource");
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }

    @Test
    public void testOpenPokemonDetails()
    {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            ViewInteraction recyclerView = Espresso.onView(withId(R.id.recycler_view));

            recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
            countingIdlingResource.increment();
            countingIdlingResource.decrement();

            Espresso.onView(withId(R.id.pokemonName)).check(matches(isDisplayed()));
        }
    }
    @Test
    public void testFilterByFavourites()
    {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            Espresso.onView(withId(R.id.favFilter)).perform(click());
            countingIdlingResource.increment();
            countingIdlingResource.decrement();

        }
    }


}
