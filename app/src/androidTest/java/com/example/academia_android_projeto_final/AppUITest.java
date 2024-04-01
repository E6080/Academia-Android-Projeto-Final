package com.example.academia_android_projeto_final;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static java.util.EnumSet.allOf;
import static java.util.function.Predicate.not;

import android.content.SharedPreferences;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.idling.CountingIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppUITest {

    private CountingIdlingResource countingIdlingResource;
    private SharedPreferences sharedPreferences;
    private String PREFERENCES_FILENAME = "com.example.academia_android_projeto_final.Favourites";

    @Before
    public void setUp() {
        sharedPreferences = ApplicationProvider.getApplicationContext().getSharedPreferences(PREFERENCES_FILENAME,0);
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
            Thread.sleep(1500);
            recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
            Thread.sleep(1500);

            Espresso.onView(withId(R.id.pokemonName)).check(matches(isDisplayed()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFavourites()
    {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            ViewInteraction recyclerView = Espresso.onView(withId(R.id.recycler_view));
            Thread.sleep(1500);

            recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
            Thread.sleep(1500);

            Espresso.onView(withId(R.id.ivFav)).perform(click());

            assert(sharedPreferences.contains("Bulbasaur"));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFilterByFavourites()
    {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            ViewInteraction recyclerView = Espresso.onView(withId(R.id.recycler_view));
            Thread.sleep(1500);

            Espresso.onView(withId(R.id.favFilter)).perform(click());
            Thread.sleep(1500);

            recyclerView.check((view, noViewFoundException) -> {
                RecyclerView recyclerView1 = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView1.getAdapter();
                assertNotNull(adapter);
                assertEquals(1, adapter.getItemCount());
            });


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
