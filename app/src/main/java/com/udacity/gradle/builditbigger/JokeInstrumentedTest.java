package com.udacity.gradle.builditbigger;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import funsolutions.project.lynsychin.myjokes.JokeActivity;

@RunWith(AndroidJUnit4.class)
public class JokeInstrumentedTest {

    @Rule
    public ActivityTestRule<JokeActivity> mActivityTestRule =
            new ActivityTestRule<>(JokeActivity.class);

    @Test
    public void jokeIsNotEmpty() throws InterruptedException {
        Espresso.onView(ViewMatchers.withId(R.id.joke)).perform(click());
        Thread.sleep(2000);
        //check that output is not empty
        onView(withId(R.id.textView)).check(matches(not(withText(""))));
    }


}
