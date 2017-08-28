package com.example.gracepark.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test ActivityRecipeDetails
 */

public class ActivityRecipeDetailsTest {

    private final String NAME = "Brownies";
    private final String INGREDIENTS = "[{}]";
    private final String STEPS = "[{\"id\": 0,\"shortDescription\": \"STEPTEXT\",\"description\": \"\",\"videoURL\": \"\",\"thumbnailURL\": \"\"}]";

    @Rule
    public IntentsTestRule<RecipeDetailsActivity> mActivityTestRule = new IntentsTestRule<RecipeDetailsActivity>(RecipeDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent intent = new Intent(targetContext, RecipeDetailsActivity.class);
            intent.putExtra(RecipeListActivity.EXTRA_RECIPE_NAME, NAME);
            intent.putExtra(RecipeListActivity.EXTRA_RECIPE_INGREDIENTS, INGREDIENTS);
            intent.putExtra(RecipeListActivity.EXTRA_RECIPE_STEPS, STEPS);
            return intent;
        }
    };

    @Test
    public void clickStep_opensStepPage() {
        onView(withText("STEPTEXT")).perform(click());

        intended(hasComponent(RecipeStepActivity.class.getName()));

    }
}
