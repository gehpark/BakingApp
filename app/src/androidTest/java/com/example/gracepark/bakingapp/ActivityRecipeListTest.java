package com.example.gracepark.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test ActivityRecipeList
 */

@RunWith(AndroidJUnit4.class)
public class ActivityRecipeListTest {

    @Rule
    public IntentsTestRule<RecipeListActivity> mIntentsTestRule = new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void clickRecipe_opensRecipeDetails() {
        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(RecipeDetailsActivity.class.getName()));
    }


}
