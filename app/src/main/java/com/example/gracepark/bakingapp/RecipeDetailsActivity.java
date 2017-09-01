package com.example.gracepark.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_ID;
import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_INGREDIENTS;
import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_NAME;
import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_STEPS;
import static com.example.gracepark.bakingapp.UpdateWidgetService.NO_RECIPE_CHOSEN_ID;

/**
 * Activity to hold details on recipe including short descriptions of steps and ingredients.
 * Holds RecipeDetailsFragment in phone view.
 */

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener, RecipeStepFragment.OnNextClickListener  {

    public final static String EXTRA_KEY_POSITION = "key_step_position";
    public final static String EXTRA_KEY_MEDIA = "key_step_media";
    public final static String EXTRA_KEY_TEXT = "key_step_text";
    public final static String EXTRA_KEY_SHORT = "key_step_short";

    public ArrayList<String> mStepShortDescriptionsList = new ArrayList<>();
    public ArrayList<String> mStepTextList = new ArrayList<>();
    public ArrayList<String> mStepMediaList = new ArrayList<>();
    private boolean mTwoPane;

    private RecipeDetailsFragment mDetailsFragment;
    private RecipeStepFragment mStepFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra(EXTRA_RECIPE_NAME));

        FragmentManager fragmentManager = getSupportFragmentManager();
        mDetailsFragment = new RecipeDetailsFragment();

        // TODO make it such that we only need the recipe id and query from details page instead
        mDetailsFragment.setIngredientsList(
                getRecipeIngredients(intent.getStringExtra(EXTRA_RECIPE_INGREDIENTS)));
        mDetailsFragment.setShortStepsList(
                getRecipeStepInfo(intent.getStringExtra(EXTRA_RECIPE_STEPS)));

        UpdateWidgetService.startActionUpdate(this, intent.getIntExtra(EXTRA_RECIPE_ID, NO_RECIPE_CHOSEN_ID));

        fragmentManager.beginTransaction()
                .replace(R.id.recipe_details, mDetailsFragment)
                .commit();

        if (findViewById(R.id.recipe_step) != null
                && fragmentManager.findFragmentById(R.id.recipe_step) == null) {
            mTwoPane = true;
            mStepFragment = new RecipeStepFragment();
            mStepFragment.hideNextButton();
            setStepFragmentDetails(0);
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step, mStepFragment)
                    .commit();
        } else
            mTwoPane = false;
    }

    public static ArrayList<String> getRecipeIngredients(String recipeIngredients) {
        ArrayList<String> ingredientList = new ArrayList<>();

        try {
            JSONArray ingredientJsonArray = new JSONArray(recipeIngredients);
            for (int i = 0; i < ingredientJsonArray.length(); i++) {
                JSONObject ingredients_data = ingredientJsonArray.getJSONObject(i);

                String measure = ingredients_data.getString("measure").equalsIgnoreCase("UNIT")
                        ? ""
                        : ingredients_data.getString("measure").concat(" ");
                String ingredient = ingredients_data.getDouble("quantity")
                        + " "
                        + measure
                        + ingredients_data.getString("ingredient");
                ingredientList.add(ingredient);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredientList;
    }

    private ArrayList<String> getRecipeStepInfo(String recipeStepInfo) {
        try {
            JSONArray stepsJsonArray = new JSONArray(recipeStepInfo);
            for (int i = 0; i < stepsJsonArray.length(); i++) {
                JSONObject steps_data = stepsJsonArray.getJSONObject(i);
                mStepTextList.add(steps_data.getString("description"));
                mStepMediaList.add(steps_data.getString("videoURL").isEmpty() ?
                        steps_data.getString("thumbnailURL")
                        : steps_data.getString("videoURL"));
                mStepShortDescriptionsList.add(steps_data.getString("shortDescription"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mStepShortDescriptionsList;
    }

    private void setStepFragmentDetails(int position) {
        mStepFragment.setMedia(mStepMediaList.get(position));
        mStepFragment.setText(mStepTextList.get(position));
        if (position + 1 <mStepShortDescriptionsList.size()) {
            mStepFragment.setNextButtonText(mStepShortDescriptionsList.get(position));
        } else {
            mStepFragment.hideNextButton();
        }
        mStepFragment.setViews();
    }

    public void onStepSelected(int position) {
        if (mTwoPane) {
            setStepFragmentDetails(position);
        } else {
            final Intent intent = new Intent(this, RecipeStepActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(EXTRA_KEY_SHORT, mStepShortDescriptionsList);
            bundle.putStringArrayList(EXTRA_KEY_TEXT, mStepTextList);
            bundle.putStringArrayList(EXTRA_KEY_MEDIA, mStepMediaList);
            bundle.putInt(EXTRA_KEY_POSITION, position);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    @Override
    public void onNextClicked() {
        // No-op.
    }
}
