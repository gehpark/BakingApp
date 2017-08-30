package com.example.gracepark.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test ActivityRecipeStep
 */

public class ActivityRecipeStepTest {
    @Rule
    public IntentsTestRule<RecipeStepActivity> mActivityTestRule = new IntentsTestRule<RecipeStepActivity>(RecipeStepActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent intent = new Intent(targetContext, RecipeStepActivity.class);
            ArrayList<String> testList = new ArrayList<String>();
            testList.add("step1");
            testList.add("step2");
            ArrayList<String> emptyList = new ArrayList<String>();
            emptyList.add("");
            emptyList.add("");
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(RecipeDetailsActivity.EXTRA_KEY_SHORT, testList);
            bundle.putStringArrayList(RecipeDetailsActivity.EXTRA_KEY_TEXT, testList);
            bundle.putStringArrayList(RecipeDetailsActivity.EXTRA_KEY_MEDIA, emptyList);
            bundle.putInt(RecipeDetailsActivity.EXTRA_KEY_POSITION, 0);
            intent.putExtras(bundle);
            return intent;
        }
    };

    @Test
    public void clickNext_opensNextStep() {
        onView(withId(R.id.step_text)).check(matches(withText("step1")));

        onView(withId(R.id.next_step_button)).perform(click());

        onView(withId(R.id.step_text)).check(matches(withText("step2")));
    }
}
